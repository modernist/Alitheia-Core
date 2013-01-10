/*
 * This file is part of the Alitheia system, developed by the SQO-OSS
 * consortium as part of the IST FP6 SQO-OSS project, number 033331.
 *
 * Copyright 2007-2008 by the SQO-OSS consortium members <info@sqo-oss.eu>
 * Copyright 2008 by Paul J. Adams <paul.adams@siriusit.co.uk>
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

package eu.sqooss.service.db;

import eu.sqooss.service.db.DAObject;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Index;

@Entity
@Table(name="PRIVILEGE_VALUE")
@XmlRootElement(name="privilegevalue")
public class PrivilegeValue extends DAObject {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="PRIVILEGE_VALUE_ID")
	@XmlElement
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PRIVILEGE_ID")
	@Index(name="IDX_PRIVILEGE_ID")
	private Privilege privilege;
	
	@Column(name="VALUE")
	@XmlElement
    private String value;
	
	@OneToMany(mappedBy="groupPrivilege.privilegeValue", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<GroupPrivilege> groupPrivileges = new HashSet<GroupPrivilege>();
    
    public PrivilegeValue() {}

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public String getValue() {
        return value;
    }

    public Set<GroupPrivilege> getGroupPrivileges() {
        return groupPrivileges;
    }

    public void setGroupPrivileges(Set<GroupPrivilege> groupPrivileges) {
        this.groupPrivileges = groupPrivileges;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
