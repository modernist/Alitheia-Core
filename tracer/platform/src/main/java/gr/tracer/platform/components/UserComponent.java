package gr.tracer.platform.components;

import gr.tracer.platform.TracerComponent;

public interface UserComponent extends TracerComponent {
	
	public boolean createNewUser(String aAUsername, String aAPassword, String aAType, String aAName, String aAEmail);
	
	public boolean userLoginAttempt(String aAUsername, String aAPassword);
}
