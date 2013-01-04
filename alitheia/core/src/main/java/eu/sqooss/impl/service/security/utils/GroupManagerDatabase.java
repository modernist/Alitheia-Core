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
    
    private DBService db;
    private Map<String, Object> groupProps;
    private Object lockObject = new Object();
    
    public GroupManagerDatabase(DBService db) {
        super();
        this.db = db;
        groupProps = new Hashtable<String, Object>(1);
    }

    public List<?> getGroups() {
        return db.doHQL(GET_GROUPS);
    }
    
    public Set<?> getGroups(long userId) {
        User user = db.findObjectById(User.class, userId);
        if (user != null) {
            return user.getGroups();
        } else {
            return null;
        }
    }
    
    public Group getGroup(long groupId) {
        return db.findObjectById(Group.class, groupId);
    }
    
    public List<Group> getGroup(String description) {
        synchronized(lockObject) {
            groupProps.clear();
            groupProps.put(ATTRIBUTE_GROUP_DESCRIPTION, description);
            return db.findObjectsByProperties(Group.class, groupProps);
        }
    }
    
    public List<?> getGroupPrivileges() {
        return db.doHQL(GET_GROUP_PRIVILEGES);
    }
    
    public boolean create(DAObject dao) {
        return db.addRecord(dao);
    }
    
    public boolean delete(DAObject dao) {
        return db.deleteRecord(dao);
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
                newGroupPrivilege.setPv(privilegeValue);
                newGroupPrivilege.setUrl(serviceUrl);
                Transaction transaction = null;
                try {
                    transaction = session.beginTransaction();
                    session.persist(newGroupPrivilege);
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
            } else {
                return false;
            }
        } finally {
            if(db.flushDBSession())
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
                GroupPrivilege groupPrivilege = new GroupPrivilege();
                groupPrivilege.setGroup(group);
                groupPrivilege.setPv(privilegeValue);
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
                }
            }
            return false;
        } finally {
           if(db.flushDBSession())
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
            if(db.flushDBSession())
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
            if(db.flushDBSession())
            	db.commitDBSession();
        }
    }
    
}

//vi: ai nosi sw=4 ts=4 expandtab
