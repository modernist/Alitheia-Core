package gr.tracer.platform.controllers;

import eu.sqooss.service.db.User;
import gr.tracer.platform.TracerPlatform;
import gr.tracer.platform.components.UserComponent;
import gr.tracer.platform.components.UserComponentImpl;

public class UserController {
	
	UserComponentImpl ucl = null;
	
	/*
	 * Parameterless constructor of the class.
	 * Retrieving an instance of UserComponent.
	 */
	public UserController() {
		ucl = ((UserComponentImpl) TracerPlatform.getInstance().getComponent(UserComponent.class));
	}

	/**
     * @see gr.tracer.platform.components.UserComponent#createNewUser(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
	public User createTracerUser(String userName, String userPassword, String userType, String userEmail) {
		
		return ucl.createNewUser(userName, userPassword, userType, userEmail);
	}

	/**
     * @see gr.tracer.platform.components.UserComponent#userLoginAttempt(java.lang.String, java.lang.String)
     */
	public User loginAttempt(String userName, String userPassword) {
		
		return ucl.userLoginAttempt(userName, userPassword);
	}
}