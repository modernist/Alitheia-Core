package gr.tracer.platform.controllers;

import eu.sqooss.service.db.User;
import gr.tracer.platform.TracerPlatform;
import gr.tracer.platform.components.UserComponent;
import gr.tracer.platform.components.UserComponentImpl;

public class UserController {
	
	UserComponentImpl ucl = null;
	
	public UserController() {
		ucl = ((UserComponentImpl) TracerPlatform.getInstance().getComponent(UserComponent.class));
	}

	public User createTracerUser(String userName, String userPassword, String userType, String userEmail) {
		
		return ucl.createNewUser(userName, userPassword, userType, userEmail);
	}

	public User loginAttempt(String userName, String userPassword) {
		
		return ucl.userLoginAttempt(userName, userPassword);
	}
}