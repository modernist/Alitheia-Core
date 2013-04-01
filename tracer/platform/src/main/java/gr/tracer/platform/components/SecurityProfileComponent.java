package gr.tracer.platform.components;

import java.util.List;

import gr.tracer.common.entities.db.SecurityProfile;
import gr.tracer.platform.TracerComponent;

public interface SecurityProfileComponent extends TracerComponent {
	
	public List<SecurityProfile> getSecurityProfiles();

	public boolean addVulnerabilityTypeToSecurityProfile(String vtName, String spName);

	public SecurityProfile createSecurityProfile(String spName, String spDescription);

	public SecurityProfile searchSecurityProfile(String spName);

	public SecurityProfile getSecurityProfile(int spId);

	public boolean removeVulnerabilityTypeFromSecurityProfile(String vtName, String spName);
}
