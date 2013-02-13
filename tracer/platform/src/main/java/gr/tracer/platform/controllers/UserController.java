package gr.tracer.platform.controllers;

import eu.sqooss.service.security.GroupManager;
import eu.sqooss.service.security.UserManager;
import eu.sqooss.service.db.Group;
import gr.tracer.platform.components.UserComponent;

public class UserController {
	private UserComponent uc;
	UserManager userManager;
	GroupManager groupManager;
	
	eu.sqooss.service.db.User user;
	Group group;
	
	public UserController(UserComponent uc) {
		this.uc = uc;
	}

	public boolean createTracerUser(String aAUsername, String aAPassword, String aAType, String aAName, String aAEmail) {
		
		return uc.createNewUser(aAUsername, aAPassword, aAType, aAName, aAEmail);
	}

	public boolean loginAttempt(String aAUsername, String aAPassword) {
		
		return uc.userLoginAttempt(aAUsername, aAPassword);
	}
}