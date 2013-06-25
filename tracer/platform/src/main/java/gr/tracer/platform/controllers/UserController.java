package gr.tracer.platform.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import eu.sqooss.service.db.User;
import gr.tracer.platform.TracerPlatform;
import gr.tracer.platform.components.UserComponent;

@Path("/controllers/user")
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
	@GET
    @Produces({"application/xml", "application/json"})
	@Path("/createUser/{userName}-{userPassword}-{userType}-{userEmail}")
	public User createTracerUser(@PathParam("userName") String userName, @PathParam("userPassword") String userPassword, @PathParam("userType") String userType, @PathParam("userEmail") String userEmail) {
		
		return ucl.createNewUser(userName, userPassword, userType, userEmail);
	}

	/**
     * @see gr.tracer.platform.components.UserComponent#userLoginAttempt(java.lang.String, java.lang.String)
     */
	@GET
    @Produces({"application/xml", "application/json"})
	@Path("/loginAttempt/{userName}-{userPassword}")
	public User loginAttempt(@PathParam("userName") String userName, @PathParam("userPassword") String userPassword) {
		
		return ucl.userLoginAttempt(userName, userPassword);
	}
	
	/**
     * @see gr.tracer.platform.components.UserComponent#userExists(java.lang.String, java.lang.String)
     */
	@GET
    @Produces({"application/xml", "application/json"})
	@Path("/userExists/{userName}-{userPassword}")
	public boolean userExists(@PathParam("userName") String userName, @PathParam("userPassword") String userPassword)  {
		return ucl.userExists(userName, userPassword);
	}
}