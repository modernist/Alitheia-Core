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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import eu.sqooss.service.db.DAObject;

@Entity
@Table(name="PENDING_USER")
public class PendingUser extends DAObject {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="USER_ID")
	@XmlElement
	private long id;
	
	@Column(name="NAME", nullable=false)
	@XmlElement
    private String name;
	
	@Column(name="PASSWORD", nullable=false)
	@XmlElement
    private String password;
	
	@Column(name="EMAIL", nullable=false)
	@XmlElement
    private String email;
	
	@Column(name="HASH", nullable=false)
    private String hash;
	
	@Column(name="CREATED")
    private Date created;

    public PendingUser() {};

    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String value) {
        password = value;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String value) {
        email = value;
    }

    /**
     * Retrieves the hash string that will be used to identify the correct
     * pending user's record during user's confirmation.
     * 
     * @return the hash string value
     */
    public String getHash() {
        return hash;
    }

    /**
     * The hash value returned by this function will be primarily used during
     * the construction of user's confirmation email.
     * 
     * @param hash the hash string value
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * Returns the exact time when this pending user record was created.
     * 
     * @return the creation time
     */
    public Date getCreated() {
        return created;
    }

    /**
     * Sets the time that will be later on used as a creation time for this
     * record
     * 
     * @param time the time stamp to set as a record's creation time
     */
    public void setCreated(Date time) {
        this.created = time;
    }

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

}

//vi: ai nosi sw=4 ts=4 expandtab

