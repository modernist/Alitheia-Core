package gr.tracer.common.controllers;

import eu.sqooss.service.logging.Logger;
import eu.sqooss.service.security.GroupManager;
import eu.sqooss.service.security.SecurityManager;
import eu.sqooss.service.security.UserManager;
import eu.sqooss.service.db.Group;
import gr.tracer.common.security.TracerSecurityModel;
import gr.tracer.common.security.TracerSecurityModelImpl;

public class UserController {
	private TracerSecurityModelImpl tsm;
	UserManager userManager;
	GroupManager groupManager;
	
	eu.sqooss.service.db.User user;
	Group group;
	
	public UserController(TracerSecurityModelImpl tsm) {
		this.tsm = tsm;
	}

	public boolean createTracerUser(String aAUsername, String aAPassword, String aAType, String aAName, String aAEmail) {
		
		return tsm.createNewUser(aAUsername, aAPassword, aAType, aAName, aAEmail);
	}

	public boolean loginAttempt(String aAUsername, String aAPassword) {
		
		return tsm.userLoginAttempt(aAUsername, aAPassword);
	}
}