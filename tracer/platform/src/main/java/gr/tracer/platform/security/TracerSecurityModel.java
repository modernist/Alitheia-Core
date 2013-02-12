package gr.tracer.platform.security;

import gr.tracer.platform.TracerComponent;

public interface TracerSecurityModel extends TracerComponent{
	
	public void initSecurityModel();
	
	public boolean createNewUser(String aAUsername, String aAPassword, String aAType, String aAName, String aAEmail);
	
	public boolean userLoginAttempt(String aAUsername, String aAPassword);

}
