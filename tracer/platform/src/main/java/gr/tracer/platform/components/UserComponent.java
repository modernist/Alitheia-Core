package gr.tracer.platform.components;

import eu.sqooss.service.db.User;
import gr.tracer.platform.TracerComponent;

public interface UserComponent extends TracerComponent {
	
	public User createNewUser(String aAUsername, String aAPassword, String aAType, String aAName, String aAEmail);
	
	public User userLoginAttempt(String aAUsername, String aAPassword);
}
