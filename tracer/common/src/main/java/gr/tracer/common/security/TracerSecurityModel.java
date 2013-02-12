package gr.tracer.common.security;

public interface TracerSecurityModel {
	
	public void initSecurityModel();
	
	public boolean createNewUser(String aAUsername, String aAPassword, String aAType, String aAName, String aAEmail);
	
	public boolean userLoginAttempt(String aAUsername, String aAPassword);

}
