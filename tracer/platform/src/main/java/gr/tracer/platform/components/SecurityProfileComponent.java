package gr.tracer.platform.components;

import java.util.List;

import gr.tracer.common.entities.db.SecurityProfile;
import gr.tracer.platform.TracerComponent;

public interface SecurityProfileComponent extends TracerComponent {
	
	public List<SecurityProfile> getSecurityProfiles();

	public boolean addVulnerabilityToSecurityProfile(long vtId, long spId);

	public SecurityProfile createSecurityProfile(String aAName, String aAType);

	public List<SecurityProfile> searchSecurityProfile(String aAName);

	public boolean addSecurityProfileToList(SecurityProfile aASp);

	public SecurityProfile getSecurityProfile(int aAProfile_index);

	public boolean removeVulnerabilityFromSecurityProfile(long vtId, long spId);
}
