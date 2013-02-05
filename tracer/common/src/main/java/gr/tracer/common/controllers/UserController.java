package gr.tracer.common.controllers;

import eu.sqooss.service.logging.Logger;
import eu.sqooss.service.security.GroupManager;
import eu.sqooss.service.security.SecurityManager;
import eu.sqooss.service.security.UserManager;
import eu.sqooss.service.db.Group;

public class UserController {
	private Logger logger;
	private SecurityManager sm;
	UserManager userManager;
	GroupManager groupManager;
	
	eu.sqooss.service.db.User user;
	Group group;
	
	public UserController(SecurityManager sm, Logger logger) {
		this.sm = sm;
		this.logger = logger;
	}

	public boolean createUser(String aAUsername, String aAPassword, String aAType, String aAName, String aAEmail) {
		userManager = sm.getUserManager();
		groupManager = sm.getGroupManager();
		
		user = userManager.getUser(aAUsername);
		if ( user == null) {
			user = userManager.createUser(aAUsername, aAPassword, aAEmail);
		} else {
			logger.info("User already exists"); 
			return false;
		}
		
		group = groupManager.getGroup(aAType);
		
		if (group != null) {
			groupManager.addUserToGroup(group.getId(), user.getId());
		} else {
			logger.info("Group does not exist"); 
			return false;
		}
		
		logger.info("Created user with the name " + aAUsername + " as " + aAType);
		return true;
	}

	public boolean loginAttempt(String aAUsername, String aAPassword) {
		userManager = sm.getUserManager();
		groupManager = sm.getGroupManager();
		
		user = userManager.getUser(aAUsername);
		if (user != null) {
			if (user.getPassword().equals(aAPassword))
				logger.info("Authenticated user with username " + aAUsername);
			else {
				logger.info("Wrong password for user with username " + aAUsername);
				return false;
			}
		} else {
			logger.info("There is no user with username " + aAUsername);
			return false;
		}
		
		return true;
	}
}