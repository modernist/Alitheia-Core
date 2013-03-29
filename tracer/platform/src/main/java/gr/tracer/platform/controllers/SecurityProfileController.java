package gr.tracer.platform.controllers;

import java.util.List;

import gr.tracer.common.entities.db.SecurityProfile;
import gr.tracer.common.entities.db.VulnerabilityType;
import gr.tracer.platform.components.SecurityProfileComponent;
import gr.tracer.platform.components.VulnerabilityComponent;

public class SecurityProfileController {
	
	private SecurityProfileComponent spc;
	private VulnerabilityComponent vc;
	
	public List<SecurityProfile> getSecurityProfiles() {
		return spc.getSecurityProfiles();	
	}

	public boolean addVulnerabilityToSecurityProfile(String vtName, String spName) {
		VulnerabilityType vt = (VulnerabilityType) vc.searchVulnerability(vtName);
		SecurityProfile sp = (SecurityProfile) spc.searchSecurityProfile(spName);
		if ((vt != null) && (sp != null))
			return spc.addVulnerabilityToSecurityProfile(vt.getId(), sp.getId());
		return false;
	}

	public SecurityProfile createSecurityProfile(String spName, String spType) {
		return spc.createSecurityProfile(spName, spType);
	}

	public SecurityProfile searchSecurityProfile(String spName) {
		return spc.searchSecurityProfile(spName);
	}

	public boolean addSecurityProfileToList(SecurityProfile sp) {
		return spc.addSecurityProfileToList(sp);
	}

	public SecurityProfile getSecurityProfile(int spId) {
		return spc.getSecurityProfile(spId);
	}

	public boolean removeVulnerabilityFromSecurityProfile(String vtName, String spName) {
		VulnerabilityType vt = (VulnerabilityType) vc.searchVulnerability(vtName);
		SecurityProfile sp = (SecurityProfile) spc.searchSecurityProfile(spName);
		if ((vt != null) && (sp != null))
			return spc.removeVulnerabilityFromSecurityProfile(vt.getId(), sp.getId());
		return false;
	}
}
