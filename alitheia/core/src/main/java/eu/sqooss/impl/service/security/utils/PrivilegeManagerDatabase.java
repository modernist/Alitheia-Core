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

import eu.sqooss.service.db.DAObject;
import eu.sqooss.service.db.DBService;
import eu.sqooss.service.db.Privilege;
import eu.sqooss.service.db.PrivilegeValue;

public class PrivilegeManagerDatabase implements PrivilegeManagerDBQueries {

    private static final String ATTRIBUTE_PRIVILEGE             = "privilege";
    private static final String ATTRIBUTE_PRIVILEGE_DESCRIPTION = "description";
    private static final String ATTRIBUTE_PRIVILEGE_VALUE       = "value";
    
    private DBService db;
    private Map<String, Object> privilegeProps;
    private Object lockObject = new Object();
    
    public PrivilegeManagerDatabase(DBService db) {
        super();
        this.db = db;
        privilegeProps = new Hashtable<String, Object>(1);
    }

    public List<?> getPrivileges() {
        return db.doHQL(GET_PRIVILEGES);
    }
    
    public List<?> getPrivilegeValues() {
        return db.doHQL(GET_PRIVILEGE_VALUES);
    }
    
    public List<PrivilegeValue> getPrivilegeValues(long privilegeId) {
        Privilege privilege = db.findObjectById(Privilege.class, privilegeId);
        if (privilege != null) {
            synchronized (lockObject) {
                privilegeProps.clear();
                privilegeProps.put(ATTRIBUTE_PRIVILEGE, privilege);
                return db.findObjectsByProperties(PrivilegeValue.class, privilegeProps);
            }
        } else {
            return null;
        }
    }
    
    public PrivilegeValue getPrivilegeValue(long privilegeValueId) {
        return db.findObjectById(PrivilegeValue.class, privilegeValueId);
    }
    
    public List<PrivilegeValue> getPrivilegeValue(long privilegeId,
            String privilegeValue) {
        Privilege privilege = db.findObjectById(Privilege.class, privilegeId);
        if (privilege != null) {
            synchronized (lockObject) {
                privilegeProps.clear();
                privilegeProps.put(ATTRIBUTE_PRIVILEGE, privilege);
                privilegeProps.put(ATTRIBUTE_PRIVILEGE_VALUE, privilegeValue);
                return db.findObjectsByProperties(PrivilegeValue.class,
                        privilegeProps);
            }
        } else {
            return null;
        }
    }
    
    public Privilege getPrivilege(long privilegeId) {
        return db.findObjectById(Privilege.class, privilegeId);
    }
    
    public List<Privilege> getPrivilege(String description) {
        synchronized (lockObject) {
            privilegeProps.clear();
            privilegeProps.put(ATTRIBUTE_PRIVILEGE_DESCRIPTION, description);
            return db.findObjectsByProperties(Privilege.class, privilegeProps);
        }
    }
    
    public boolean delete(DAObject dao) {
        return db.deleteRecord(dao);
    }
    
    public boolean create(DAObject dao) {
        return db.addRecord(dao);
    }
    
}

//vi: ai nosi sw=4 ts=4 expandtab
