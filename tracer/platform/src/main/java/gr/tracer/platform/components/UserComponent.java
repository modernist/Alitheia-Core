package gr.tracer.platform.components;

import eu.sqooss.service.db.User;
import gr.tracer.platform.TracerComponent;

public interface UserComponent extends TracerComponent {
	
	public User createNewUser(String userName, String userPassword, String userType, String userEmail);
	
	public User userLoginAttempt(String userName, String userPassword);
}
