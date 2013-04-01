package gr.tracer.platform.controllers;

import java.util.List;

import gr.tracer.common.entities.db.SecurityProfile;
import gr.tracer.platform.components.SecurityProfileComponent;

public class SecurityProfileController {
	
	private SecurityProfileComponent spc;
	
	public List<SecurityProfile> getSecurityProfiles() {
		return spc.getSecurityProfiles();	
	}

	public boolean addVulnerabilityTypeToSecurityProfile(String vtName, String spName) {
		return spc.addVulnerabilityTypeToSecurityProfile(vtName, spName);
	}

	public SecurityProfile createSecurityProfile(String spName, String spType) {
		return spc.createSecurityProfile(spName, spType);
	}

	public SecurityProfile searchSecurityProfile(String spName) {
		return spc.searchSecurityProfile(spName);
	}

	public SecurityProfile getSecurityProfile(int spId) {
		return spc.getSecurityProfile(spId);
	}

	public boolean removeVulnerabilityFromSecurityProfile(String vtName, String spName) {
		return spc.removeVulnerabilityTypeFromSecurityProfile(vtName, spName);
		
	}
}
