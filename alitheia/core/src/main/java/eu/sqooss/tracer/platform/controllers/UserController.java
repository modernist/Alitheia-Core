package eu.sqooss.tracer.platform.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import eu.sqooss.service.db.User;
import eu.sqooss.tracer.platform.TracerPlatform;
import eu.sqooss.tracer.platform.components.UserComponent;

@Path("/tracerapi")
public class UserController {
	
	UserComponent ucl = null;
	
	/*
	 * Parameterless constructor of the class.
	 * Retrieving an instance of UserComponent.
	 */
	public UserController() {
		ucl = TracerPlatform.getInstance().getComponent(UserComponent.class);
	}

	/**
     * @see gr.tracer.platform.components.UserComponent#createNewUser(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
	@PUT
    @Produces({"application/xml", "application/json"})
	@Path("/createUser/{userName: [a-z0-9_-]{3,15}}/{userPassword: ((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})}/{userType: [a-z,A-Z]*}/{userEmail: [a-zA-Z_.0-9]+[@][a-zA-Z_0-9]+([.][a-z]+)+}")
	public User createTracerUser(@PathParam("userName") String userName, @PathParam("userPassword") String userPassword, @PathParam("userType") String userType, @PathParam("userEmail") String userEmail) {
		
		return ucl.createNewUser(userName, userPassword, userType, userEmail);
	}

	/**
     * @see gr.tracer.platform.components.UserComponent#userLoginAttempt(java.lang.String, java.lang.String)
     */
	@GET
    @Produces({"application/xml", "application/json"})
	@Path("/loginAttempt/{userName: [a-zA-Z][a-zA-Z_0-9]*}/{userPassword: [a-zA-Z][a-zA-Z_0-9]*}")
	public User loginAttempt(@PathParam("userName") String userName, @PathParam("userPassword") String userPassword) {
		
		return ucl.userLoginAttempt(userName, userPassword);
	}
	
	/**
     * @see gr.tracer.platform.components.UserComponent#userExists(java.lang.String, java.lang.String)
     */
	@GET
    @Produces({"application/xml", "application/json"})
	@Path("/userExists/{userName: [a-zA-Z][a-zA-Z_0-9]*}/{userPassword: [a-zA-Z][a-zA-Z_0-9]*}")
	public boolean userExists(@PathParam("userName") String userName, @PathParam("userPassword") String userPassword)  {
		return ucl.userExists(userName, userPassword);
	}
}