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
 

package eu.sqooss.service.db;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import eu.sqooss.service.db.DAObject;

@Entity
@Table(name="USERS")
@XmlRootElement(name="user")
public class User extends DAObject {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="USER_ID")
	@XmlElement
	private long id;
	
	@Column(name="NAME", nullable=false, unique=true)
	@XmlElement
    private String name;
	
	@Column(name="REGISTERED")
	@XmlElement
    private Date registered;
	
	@Column(name="LAST_ACTIVITY")
	@XmlElement
    private Date lastActivity;
	
	@Column(name="PASSWORD", nullable=false)
	@XmlElement
    private String password;
	
	@Column(name="EMAIL", nullable=false)
	@XmlElement
    private String email;
	
	/*@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(
		name="GROUP_USER",
		joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="USER_ID")},
		inverseJoinColumns={@JoinColumn(name="GROUP_ID", referencedColumnName="GROUP_ID")})*/
	@ManyToMany(mappedBy="users",targetEntity=Group.class, cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<Group> groups = new HashSet<Group>();
    
	@OneToMany(fetch=FetchType.LAZY, mappedBy="user", cascade=CascadeType.ALL)
    private Set<MonitoredProjectList> monitoredProjectLists;
	
    public User() {};

    public String getName() {
        return name;
    }

    public void setName( String value ) {
        name = value;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered( Date value ) {
        registered = value;
    }

    public Date getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity( Date value ) {
        lastActivity = value;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String value ) {
        password = value;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String value ) {
        email = value;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public Set<MonitoredProjectList> getMonitoredProjectLists() {
		return monitoredProjectLists;
	}

	public void setMonitoredProjectLists(Set<MonitoredProjectList> monitoredProjectLists) {
		this.monitoredProjectLists = monitoredProjectLists;
	}
}

// vi: ai nosi sw=4 ts=4 expandtab
