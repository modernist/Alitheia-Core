package gr.tracer.platform.components;

import eu.sqooss.core.AlitheiaCore;
import eu.sqooss.service.db.Group;
import eu.sqooss.service.db.User;
import eu.sqooss.service.logging.Logger;
import eu.sqooss.service.security.GroupManager;
import eu.sqooss.service.security.SecurityManager;
import eu.sqooss.service.security.UserManager;
import gr.tracer.platform.TracerPlatform;
import gr.tracer.platform.security.TracerSecurityConstants;

public class UserComponentImpl implements UserComponent {
	
	private TracerPlatform platform;
	private Logger logger;
	private SecurityManager sm;

	/**
     * @see gr.tracer.platform.components.UserComponent#createNewUser(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
	public User createNewUser(String userName, String userPassword, String userType, String userEmail) {
		
		User user = null;
        Group group = null;
        
		GroupManager groupManager = sm.getGroupManager();
		UserManager userManager = sm.getUserManager();
		
		userManager = sm.getUserManager();
		groupManager = sm.getGroupManager();
		
		user = userManager.getUser(userName);
		if ( user == null) {
			user = userManager.createUser(userName, userPassword, userEmail);
		} else {
			logger.info("User already exists"); 
			return null;
		}
		
		if (userType.toLowerCase().equals(TracerSecurityConstants.GroupName.ADMINISTRATOR.toString()))
			group = groupManager.getGroup(TracerSecurityConstants.GroupName.ADMINISTRATOR.toString());
		else if (userType.toLowerCase().equals(TracerSecurityConstants.GroupName.PROGRAMMER.toString()))
			group = groupManager.getGroup(TracerSecurityConstants.GroupName.PROGRAMMER.toString());
		else if (userType.toLowerCase().equals(TracerSecurityConstants.GroupName.VULNERABILITY_MANAGER.toString()))
			group = groupManager.getGroup(TracerSecurityConstants.GroupName.VULNERABILITY_MANAGER.toString());
		group = groupManager.getGroup(TracerSecurityConstants.GroupName.PROGRAMMER.toString());
		if (group != null) {
			groupManager.addUserToGroup(group.getId(), user.getId());
		} else {
			logger.info("Group does not exist"); 
			return null;
		}
		
		logger.info("Created user with the name " + userName + " as " + userType);
		return user;
	}
	
	/**
     * @see gr.tracer.platform.components.UserComponent#userLoginAttempt(java.lang.String, java.lang.String)
     */
	public User userLoginAttempt(String userName, String userPassword) {
		
		User user = null;       
		UserManager userManager;

		userManager = sm.getUserManager();		
		user = userManager.getUser(userName);
		
		if (user != null) {
			if (user.getPassword().equals(userManager.getHash(userPassword)))
				logger.info("Authenticated user with username " + userName);
			else {
				logger.info("Wrong password for user with username " + userName);
				return null;
			}
		} else {
			logger.info("There is no user with username " + userName);
			return null;
		}
		
		return user;
	}

	@Override
	public void initComponent(TracerPlatform platform, Logger logger) {
		// TODO Auto-generated method stub
		this.platform = platform;
		this.logger = logger;
	}

	@Override
	public boolean startUp() {
		// TODO Auto-generated method stub
		sm = AlitheiaCore.getInstance().getSecurityManager();
		userLoginAttempt("admin","admin");
		createNewUser("fotis","fotis","programmer","kostas@tracer.gr");
		return true;
	}

	@Override
	public boolean shutDown() {
		// TODO Auto-generated method stub
		return false;
	}
}
