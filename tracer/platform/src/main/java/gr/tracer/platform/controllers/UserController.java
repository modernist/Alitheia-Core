package gr.tracer.platform.controllers;

import eu.sqooss.service.security.GroupManager;
import eu.sqooss.service.security.UserManager;
import eu.sqooss.service.db.Group;
import eu.sqooss.service.db.User;
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

	public User createTracerUser(String userName, String userPassword, String userType, String userEmail) {
		
		return uc.createNewUser(userName, userPassword, userType, userEmail);
	}

	public User loginAttempt(String userName, String userPassword) {
		
		return uc.userLoginAttempt(userName, userPassword);
	}
}