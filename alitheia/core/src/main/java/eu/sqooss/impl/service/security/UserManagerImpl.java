/*
 * This file is part of the Alitheia system, developed by the SQO-OSS
 * consortium as part of the IST FP6 SQO-OSS project, number 033331.
 *
 * Copyright 2007-2008 by the SQO-OSS consortium members <info@sqo-oss.eu>
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials provided
 *       with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package eu.sqooss.impl.service.security;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.Vector;

import eu.sqooss.impl.service.security.utils.UserManagerDatabase;
import eu.sqooss.service.db.DBService;
import eu.sqooss.service.db.Group;
import eu.sqooss.service.db.PendingUser;
import eu.sqooss.service.db.Privilege;
import eu.sqooss.service.db.PrivilegeValue;
import eu.sqooss.service.db.ServiceUrl;
import eu.sqooss.service.db.User;
import eu.sqooss.service.logging.Logger;
import eu.sqooss.service.security.GroupManager;
import eu.sqooss.service.security.PrivilegeManager;
import eu.sqooss.service.security.ServiceUrlManager;
import eu.sqooss.service.security.UserManager;
import eu.sqooss.service.security.SecurityConstants;

public class UserManagerImpl implements UserManager {

    private static final String NEW_USERS_GROUP_DESCRIPTION = "new users";
    
    private static final String PROPERTY_SERVER_URL = "eu.sqooss.security.server.url";
    private static final String PROPERTY_HTTP_PORT  = "org.osgi.service.http.port";
    
	private static final String CHARSET_NAME_UTF8 = "UTF-8";
	
	private static final long EXPIRATION_PERIOD = 24*3600*1000;
	
	private Group newUserGroup;
	private ServiceUrl newUserServiceUrl;
	private Privilege newUserPrivilege;
	private GroupManager groupManager;
	private ServiceUrlManager serviceUrlManager;
	private PrivilegeManager privilegeManager;
    private UserManagerDatabase dbWrapper;
    private Logger logger;
    private MessageDigest messageDigest;
    private Timer pendingTimer;
    
    public UserManagerImpl(DBService db, Logger logger,
            PrivilegeManager privilegeManager, GroupManager groupManager,
            ServiceUrlManager serviceUrlManager) {
        this.privilegeManager = privilegeManager;
        this.groupManager = groupManager;
        this.serviceUrlManager = serviceUrlManager;
        this.dbWrapper = new UserManagerDatabase(db);
        this.logger = logger;
        
        try {
        	messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
        	messageDigest = null;
        }
        
        try {
        	initNewUserPermission();
        } catch(Exception e) {
        	logger.warn("Failed to initialize new user permissions", e);
        }
    }

    /**
     * @see eu.sqooss.service.security.UserManager#createUser(java.lang.String, java.lang.String, java.lang.String)
     */
    public User createUser(String userName, String password, String email) {
        logger.debug("Create user! username: " + userName + "; e-mail: " + email);
        String passwordHash = getHash(password);
        if (passwordHash == null) {
        	return null;
        }
        User newUser = new User();
        newUser.setName(userName);
        newUser.setPassword(passwordHash);
        newUser.setEmail(email);
        newUser.setRegistered(new Date());
        newUser.setLastActivity(newUser.getRegistered());
        if (dbWrapper.createUser(newUser)) {
            if (addDefaultPermission(newUser)) {
                return newUser;
            } else {
                deleteUser(newUser.getId());
            }
        }
        return null;
    }

    private boolean addDefaultPermission(User user) {
        if (!groupManager.addUserToGroup(newUserGroup.getId(), user.getId())) {
            return false;
        }
        PrivilegeValue newPrivilegeValue = privilegeManager.createPrivilegeValue(
                newUserPrivilege.getId(), Long.toString(user.getId()));
        if (newPrivilegeValue != null) {
            if (groupManager.addPrivilegeToGroup(newUserGroup.getId(),
                    newUserServiceUrl.getId(), newPrivilegeValue.getId())) {
                return true;
            }
            privilegeManager.deletePrivilegeValue(newPrivilegeValue.getId());
        }
        groupManager.deleteUserFromGroup(newUserGroup.getId(), user.getId());
        return false;
    }
    
    private boolean removeDefaultPermission(User user) {
        if (!groupManager.deleteUserFromGroup(newUserGroup.getId(), user.getId())) {
            return false;
        }
        PrivilegeValue[] privilegeValues = privilegeManager.getPrivilegeValues(
                newUserPrivilege.getId());
        PrivilegeValue privilegeValue = null;
        for (PrivilegeValue pValue : privilegeValues) {
            if (Long.toString(user.getId()).equals(pValue.getValue())) {
                privilegeValue = pValue;
                break;
            }
        }
        if (privilegeValue != null) {
            if (groupManager.deletePrivilegeFromGroup(newUserGroup.getId(),
                    newUserServiceUrl.getId(), privilegeValue.getId())) {
                if (privilegeManager.deletePrivilegeValue(privilegeValue.getId())) {
                    return true;
                }
                groupManager.addPrivilegeToGroup(newUserGroup.getId(),
                        newUserServiceUrl.getId(), privilegeValue.getId());
            }
        }
        groupManager.addUserToGroup(newUserGroup.getId(), user.getId());
        return false;
    }
    
    /**
     * @see eu.sqooss.service.security.UserManager#createPendingUser(java.lang.String, java.lang.String, java.lang.String)
     */
    public boolean createPendingUser(String userName, String password, String email) {
        logger.debug("Create pending user! user name: " + userName +
                "; e-mail: " + email);
        
        // Check if there is an existing user (or pending) with the same name
        if ((getUser(userName) != null) || (hasPendingUserName(userName))) {
            return false;
        }
        
        String pendingHash = getHash(userName + password + email);
        String passwordHash = getHash(password);
        
        if ((passwordHash == null) ||
                (dbWrapper.hasPendingUserHash(pendingHash))) {
            return false;
        }
        
        PendingUser newPendingUser = new PendingUser();
        newPendingUser.setName(userName);
        newPendingUser.setPassword(passwordHash);
        newPendingUser.setEmail(email);
        newPendingUser.setHash(pendingHash);
        newPendingUser.setCreated(new Date(System.currentTimeMillis()));
        
        if (!dbWrapper.createPendingUser(newPendingUser)) {
            return false;
        }
        
        updateTimer(newPendingUser);
        
        return true;
    }

    /**
     * @see eu.sqooss.service.security.UserManager#modifyUser(java.lang.String, java.lang.String, java.lang.String)
     */
    public boolean modifyUser(String userName, String newPassword,
            String newEmail) {
        logger.debug("Modify user! userName: " + userName + "; e-mail: " + newEmail);
        return dbWrapper.modifyUser(userName, getHash(newPassword), newEmail);
    }

    /**
     * @see eu.sqooss.service.security.UserManager#deleteUser(long)
     */
    public boolean deleteUser(long userId) {
        logger.debug("Delete user! user's id: " + userId);
        User user = getUser(userId);
        return deleteUser(user);
    }

    /**
     * @see eu.sqooss.service.security.UserManager#deleteUser(java.lang.String)
     */
    public boolean deleteUser(String userName) {
        logger.debug("Delete user! username: " + userName);
        User user = getUser(userName);
        return deleteUser(user);
    }

    private boolean deleteUser(User user) {
        if (user != null) {
            if (removeDefaultPermission(user)) {
                if (dbWrapper.deleteUser(user)) {
                    return true;
                }
                addDefaultPermission(user);
            }
        }
        return false;
    }
    
    /**
     * @see eu.sqooss.service.security.UserManager#getUser(long)
     */
    public User getUser(long userId) {
        logger.debug("Get user! user's id: " + userId);
        return dbWrapper.getUser(userId);
    }

    /**
     * @see eu.sqooss.service.security.UserManager#getUser(java.lang.String)
     */
    public User getUser(String userName) {
        logger.debug("Get user! username: " + userName);
        List<User> users = dbWrapper.getUser(userName);
        if (users.size() != 0) { //the user name is unique
            return users.get(0);
        } else {
            return null;
        }
    }

    /**
     * @see eu.sqooss.service.security.UserManager#getUsers()
     */
    public User[] getUsers() {
        return convertUsers(dbWrapper.getUsers());
    }

    /**
     * @see eu.sqooss.service.security.UserManager#getUsers(long)
     */
    public User[] getUsers(long groupId) {
        return convertUsers(dbWrapper.getUsers(groupId));
    }
    
    /**
     * @see eu.sqooss.service.security.UserManager#getHash(java.lang.String)
     */
    public String getHash(String password) {
    	if ((messageDigest == null) || (password == null)) {
    		return null;
    	}
    	byte[] passwordBytes = null;
    	try {
    		passwordBytes = password.getBytes(CHARSET_NAME_UTF8);
    	} catch (UnsupportedEncodingException uee) {
    		return null;
    	}
    	byte[] passwordHashBytes;
    	synchronized (messageDigest) {
    		messageDigest.reset();
    		passwordHashBytes = messageDigest.digest(passwordBytes);
    	}
    	StringBuilder passwordHash = new StringBuilder();
    	String currentSymbol;
    	for (byte currentByte : passwordHashBytes) {
    		currentSymbol = Integer.toHexString(currentByte & 0xff);
    		if (currentSymbol.length() == 1) {
    			passwordHash.append("0");
    		}
    		passwordHash.append(currentSymbol);
    	}
    	return passwordHash.toString();
    }
    
    private void initNewUserPermission() {
        Group[] groups = groupManager.getGroups();
        for (Group group : groups) {
            if (NEW_USERS_GROUP_DESCRIPTION.equals(group.getDescription())) {
                newUserGroup = group;
                break;
            }
        }
        if (newUserGroup == null) {
            newUserGroup = groupManager.createGroup(NEW_USERS_GROUP_DESCRIPTION);
        }
        ServiceUrl[] serviceUrls = serviceUrlManager.getServiceUrls();
        for (ServiceUrl serviceUrl : serviceUrls) {
            if (SecurityConstants.URL_SQOOSS_SECURITY.equals(serviceUrl.getUrl())) {
                newUserServiceUrl = serviceUrl;
                break;
            }
        }
        if (newUserServiceUrl == null) {
            newUserServiceUrl = serviceUrlManager.createServiceUrl(SecurityConstants.URL_SQOOSS_SECURITY);
        }
        Privilege[] privileges = privilegeManager.getPrivileges();
        for (Privilege privilege : privileges) {
            if (SecurityConstants.Privilege.USER_ID.toString().equals(
                    privilege.getDescription())) {
                newUserPrivilege = privilege;
                break;
            }
        }
        if (newUserPrivilege == null) {
            newUserPrivilege = privilegeManager.createPrivilege(
                    SecurityConstants.Privilege.USER_ID);
        }
    }
    
    private static String getHashUrl(String hash) {
        String addressProp = System.getProperty(PROPERTY_SERVER_URL);
        StringBuilder serverAddress = new StringBuilder();
        if (addressProp == null) {
            serverAddress.append("http://");
            try {
                serverAddress.append(InetAddress.getLocalHost().getHostAddress());
            } catch (UnknownHostException e) {
                return null;
            }
            serverAddress.append(":" + System.getProperty(PROPERTY_HTTP_PORT, "80"));
        } else {
            serverAddress.append(addressProp);
        }
        serverAddress.append("/confirmRegistration?confid="+hash);
        return serverAddress.toString();
    }
    
    /**
     * This method updates the timer and returns the expiration date.
     */
    private void updateTimer(PendingUser pendingUser) {
        if (pendingTimer == null) {
            pendingTimer = new Timer("Security timer");
        }
        Date expirationDate = new Date(pendingUser.getCreated().getTime() + EXPIRATION_PERIOD);
        pendingTimer.schedule(new PendingUserCleaner(dbWrapper, EXPIRATION_PERIOD), expirationDate);
    }
    
    private static User[] convertUsers(Collection<?> users) {
        if (users != null) {
            User[] result = new User[users.size()];
            users.toArray(result);
            return result;
        } else {
            return null;
        }
    }

    /**
     * @see eu.sqooss.service.security.UserManager#hasPendingUserHash(java.lang.String)
     */
    public boolean hasPendingUserHash (String hashValue) {
        return dbWrapper.hasPendingUserHash(hashValue);
    }

    /**
     * @see eu.sqooss.service.security.UserManager#hasPendingUserName(java.lang.String)
     */
    public boolean hasPendingUserName(String userName) {
        return dbWrapper.hasPendingUserName(userName);
    }

    /**
     * @see eu.sqooss.service.security.UserManager#activatePendingUser(java.lang.String)
     */
    public boolean activatePendingUser (String hashValue) {
        PendingUser p = dbWrapper.getPendingUser("hash", hashValue);
        if (createUser(p.getName(), p.getPassword(), p.getEmail()) != null) {
            return dbWrapper.deletePendingUser(p);
        }
        return false;
    }

}

//vi: ai nosi sw=4 ts=4 expandtab
