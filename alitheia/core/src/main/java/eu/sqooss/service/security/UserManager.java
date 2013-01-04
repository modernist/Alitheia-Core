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

package eu.sqooss.service.security;

import eu.sqooss.service.db.User;

/**
 * <code>UserManager</code> gives an access to the user's management. 
 */
public interface UserManager {

    /**
     * This method returns a User object encapsulating the user record that
     * corresponds to the specified user ID.
     * 
     * @param userId user's identifier
     * @return the corresponding User object, or
     * <code>null</code> when such user record doesn't exist
     */
    public User getUser(long userId);

    /**
     * This method returns an User object encapsulating the user record that
     * corresponds to the specified user name.
     * 
     * @param userName user's name
     * @return the corresponding User object, or
     * <code>null</code> when such user record doesn't exist
     */
    public User getUser(String userName);

    /**
     * This method returns a list of all users currently registered in the
     * SQO-OSS system.
     * 
     * @return all users in the system
     */
    public User[] getUsers();

    /**
     * This method returns a list of users belonging to the specified 
     * users group ID.
     * 
     * @param groupId group's identifier
     * @return
     */
    public User[] getUsers(long groupId);

    /**
     * This method creates a new user record from the specified user account
     * parameters
     * 
     * @param userName user's name
     * @param password user's password
     * @param email user's email
     * @return an User object corresponding to the created user record, or
     * <code>null</code> if the record can't be created.
     */
    public User createUser(String userName, String password, String email);

    /**
     * This method creates a pending user record upon user registration
     * request, and sends a confirmation request notification too the user's
     * email address.
     * 
     * @param userName
     * @param password
     * @param email
     * @return <code>true</code> if the user name is available,
     * <code>false</code> otherwise
     */
    public boolean createPendingUser(String userName, String password, String email);

    /**
     * This method modifies the user record corresponding to the given user
     * name.
     * 
     * @param userName
     * @param newPassword
     * @param newEmail
     * @return <code>true</code> if the user record is successfully modified,
     * <code>false</code> otherwise
     * 
     */
    public boolean modifyUser(String userName, String newPassword, String newEmail);

    /**
     * This method deletes the user record matching the specified user's
     * ID parameter.
     * 
     * @param userId user's ID 
     * @return <code>true</code> if the user is deleted successfully,
     * <code>false</code> otherwise
     */
    public boolean deleteUser(long userId);

    /**
     * This method deletes the user record that corresponds to the specified
     * user's name.
     * 
     * @param userName user's name
     * @return <code>true</code> if the user is deleted successfully,
     * <code>false</code> in case of failure
     */
    public boolean deleteUser(String userName);

    /**
     * This method generates a SHA-256 based hash from the specified password
     * string.
     * 
     * @param password
     * @return the password's hash
     */
    public String getHash(String password);

    /**
     * Checks if there is a pending user record that contains the same hash
     * value.
     * 
     * @param hashValue the hash value
     * 
     * @return <code>true</code>, if a corresponding pending user record is
     * found
     */
    public boolean hasPendingUserHash(String hashValue);

    /**
     * Activates the pending user record with the same hash value.
     * 
     * @param hashValue the hash value
     * 
     * @return <code>true</code>, if the corresponding pending user record is
     * activated
     */
    public boolean activatePendingUser (String hashValue);

    /**
     * Checks if there is a pending user record that contains the same user
     * name
     * 
     * @param userName the user's name
     * 
     * @return <code>true</code>, if a corresponding pending user record is
     * found
     */
    public boolean hasPendingUserName(String userName);
}

//vi: ai nosi sw=4 ts=4 expandtab
