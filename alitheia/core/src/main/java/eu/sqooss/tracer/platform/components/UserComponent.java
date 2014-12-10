package eu.sqooss.tracer.platform.components;

import eu.sqooss.service.db.User;
import eu.sqooss.tracer.platform.TracerComponent;

public interface UserComponent extends TracerComponent {
	
	/** Create a new user
	 * 
	 * @param userName The User's username 
	 * @param userPassword The User's  password
	 * @param userType The User's  type
	 * @param userEmail The User's  email 
	 * @return The new User or null if the User is not created.  
	 */	
	public User createNewUser(String userName, String userPassword, String userType, String userEmail);
	
	
	/** Authenticate a User
	 * 
	 * @param userName The User's username
	 * @param userPassword The User's  password
	 * @return The authenticated User, null if the User is not authenticated.
	 */
	public User userLoginAttempt(String userName, String userPassword);
	
	/** Search if a User exists
	 * 
	 * @param userName The User's username
	 * @param userPassword The User's  password
	 * @return True, if the User exists,otherwise false
	 */
	public boolean userExists(String userName, String userPassword);
}