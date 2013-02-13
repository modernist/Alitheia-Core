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

package eu.sqooss.impl.service.security.utils;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import eu.sqooss.service.db.DAObject;
import eu.sqooss.service.db.DBService;
import eu.sqooss.service.db.Group;
import eu.sqooss.service.db.GroupPrivilege;
import eu.sqooss.service.db.PrivilegeValue;
import eu.sqooss.service.db.ServiceUrl;
import eu.sqooss.service.db.User;

public class GroupManagerDatabase implements GroupManagerDBQueries {

    private static final String ATTRIBUTE_GROUP_DESCRIPTION = "description";
    private static final String ATTRIBUTE_GROUP_PRIVILEGE_URL = "url";
    private static final String ATTRIBUTE_GROUP_PRIVILEGE_GROUP = "group";
    private static final String ATTRIBUTE_GROUP_PRIVILEGE_PRIVILEGEVALUE = "privilegeValue";
    
    private DBService db;
    private Map<String, Object> groupProps;
    private Map<String, Object> privilegeProps;
    private Object lockObject = new Object();
    
    public GroupManagerDatabase(DBService db) {
        super();
        this.db = db;
        groupProps = new Hashtable<String, Object>(1);
        privilegeProps = new Hashtable<String, Object>(3);
    }

    public List<?> getGroups() {
    	try {
	    	if(db.startDBSession()) {
	    		return db.doHQL(GET_GROUPS);
	    	}
    	}
    	finally {
    		db.commitDBSession();
    	}
    	return Collections.emptyList();
    }
    
    public Set<?> getGroups(long userId) {
    	try {
	    	if(db.startDBSession()) {
		        User user = db.findObjectById(User.class, userId);
		        if (user != null) {
		            return user.getGroups();
		        } else {
		            return null;
		        }
	    	}
	    	else
	    		return null;
    	}
    	finally {
    		db.commitDBSession();
    	}
    }
    
    public Group getGroup(long groupId) {
    	try {
	    	if(db.startDBSession()) {
	    		return db.findObjectById(Group.class, groupId);
	    	}
	    	else
	    		return null;
    	}
    	finally {
    		db.commitDBSession();
    	}
    }
    
    public List<Group> getGroup(String description) {
    	try {
	    	if(db.startDBSession()) {
	    		synchronized(lockObject) {
	                groupProps.clear();
	                groupProps.put(ATTRIBUTE_GROUP_DESCRIPTION, description);
	                return db.findObjectsByProperties(Group.class, groupProps);
	            }
	    	}
	    	else
	    		return Collections.emptyList();
    	}
    	finally {
    		db.commitDBSession();
    	}    	
    }
    
    public List<?> getGroupPrivileges() {
        try {
	    	if(db.startDBSession()) {
	    		return db.doHQL(GET_GROUP_PRIVILEGES);
	    	}
	    	else
	    		return Collections.emptyList();
    	}
    	finally {
    		db.commitDBSession();
    	}
    }
    
    public boolean create(DAObject dao) {
    	if(db != null && db.startDBSession()) {
    		if(db.addRecord(dao))
    			return db.commitDBSession();
    	}
    	return false;
    }
    
    public boolean delete(DAObject dao) {
    	if(db != null && db.startDBSession()) {
    		if(db.deleteRecord(dao)) 
    			return db.commitDBSession();
    	}
    	return false;
    }
    
    public boolean addPrivilegeToGroup(long groupId, long urlId,
            long privilegeValueId) {
    	db.startDBSession();
        Session session = db.getActiveDBSession();
        try {
            Group group = db.findObjectById(Group.class, groupId);
            PrivilegeValue privilegeValue = db.findObjectById(PrivilegeValue.class,
                    privilegeValueId);
            ServiceUrl serviceUrl = db.findObjectById(ServiceUrl.class, urlId);
            if ((group != null) && (privilegeValue != null) && (serviceUrl != null)) {
                GroupPrivilege newGroupPrivilege = new GroupPrivilege();
                newGroupPrivilege.setGroup(group);
                newGroupPrivilege.setPrivilegeValue(privilegeValue);
                newGroupPrivilege.setUrl(serviceUrl);
                return db.addRecord(newGroupPrivilege);
                /*Transaction transaction = null;
                try {
                    transaction = session.beginTransaction();
                    session.persist(newGroupPrivilege);
                    session.flush();
                    session.refresh(privilegeValue);
                    session.refresh(serviceUrl);
                    session.refresh(group);
                    transaction.commit();
                } catch (HibernateException he) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    return false;
                }
                return true;
                */
            } else {
                return false;
            }
        } finally {
            db.commitDBSession();
        }
    }
    
    public boolean deletePrivilegeFromGroup(long groupId, long urlId, long privilegeValueId) {
        db.startDBSession();
    	Session session = db.getActiveDBSession();
        try {
            Group group = db.findObjectById(Group.class, groupId);
            PrivilegeValue privilegeValue = db.findObjectById(PrivilegeValue.class,
                    privilegeValueId);
            ServiceUrl serviceUrl = db.findObjectById(ServiceUrl.class, urlId);
            if ((group != null) && (privilegeValue != null) && (serviceUrl != null)) {
            	
            	List<GroupPrivilege> items = null;
            	
            	synchronized(lockObject) {
	                privilegeProps.clear();
	                privilegeProps.put(ATTRIBUTE_GROUP_PRIVILEGE_GROUP, groupId);
	                privilegeProps.put(ATTRIBUTE_GROUP_PRIVILEGE_URL, urlId);
	                privilegeProps.put(ATTRIBUTE_GROUP_PRIVILEGE_PRIVILEGEVALUE, privilegeValueId);
	                items = db.findObjectsByProperties(GroupPrivilege.class, privilegeProps);
            	}
            	
            	if(items != null && items.size() > 0) {
            		return db.deleteRecords(items);
            	}
            	/*    GroupPrivilege groupPrivilege = new GroupPrivilege();
                groupPrivilege.setGroup(group);
                groupPrivilege.setPrivilegeValue(privilegeValue);
                groupPrivilege.setUrl(serviceUrl);
                Object storedGroupPrivilege =
                    session.get(GroupPrivilege.class, groupPrivilege);
                if (storedGroupPrivilege != null) {
                    Transaction transaction = null;
                    try {
                        transaction = session.beginTransaction();
                        session.delete(storedGroupPrivilege);
                        session.refresh(group);
                        session.refresh(privilegeValue);
                        session.refresh(serviceUrl);
                        transaction.commit();
                    } catch (HibernateException he) {
                        if (transaction != null) {
                            transaction.rollback();
                        }
                        return false;
                    }
                    return true;
                }*/
            }
            return false;
        } finally {
        	db.commitDBSession();
        }
    }
    
    @SuppressWarnings("unchecked")
    public boolean addUserToGroup(long groupId, long userId) {
        db.startDBSession();
        try {
            Group group = db.findObjectById(Group.class, groupId);
            User user = db.findObjectById(User.class, userId);
            if ((group!=null) && (user != null)) {
                return ((group.getUsers().add(user)) &&
                        (user.getGroups().add(group)));
            } else {
                return false;
            }
        } finally {
            db.commitDBSession();
        }
    }
    
    public boolean deleteUserFromGroup(long groupId, long userId) {
        db.startDBSession();
        try {
            Group group = db.findObjectById(Group.class, groupId);
            User user = db.findObjectById(User.class, userId);
            if ((group!=null) && (user != null)) {
                return ((group.getUsers().remove(user)) &&
                        (user.getGroups().remove(group)));
            } else {
                return false;
            }
        } finally {
            db.commitDBSession();
        }
    }
    
}

//vi: ai nosi sw=4 ts=4 expandtab
