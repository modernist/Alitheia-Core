package gr.tracer.platform.controllers;

import java.util.List;

import gr.tracer.common.entities.db.SecurityProfile;
import gr.tracer.common.entities.db.VulnerabilityType;
import gr.tracer.platform.components.SecurityProfileComponent;
import gr.tracer.platform.components.VulnerabilityComponent;

public class SecurityProfileController {
	
	private SecurityProfileComponent spc;
	private VulnerabilityComponent vc;
	public VulnerabilityDetectorController _getAvailableVulnerabilityDetectors;
	public MonitoredProjectListController _getSecurityProfile;

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

	public SecurityProfile createSecurityProfile(String aAName, String aAType) {
		return spc.createSecurityProfile(aAName, aAType);
	}

	public List<SecurityProfile> searchSecurityProfile(String aAName) {
		return spc.searchSecurityProfile(aAName);
	}

	public boolean addSecurityProfileToList(SecurityProfile aASp) {
		return spc.addSecurityProfileToList(aASp);
	}

	public SecurityProfile getSecurityProfile(int aAProfile_index) {
		return spc.getSecurityProfile(aAProfile_index);
	}

	public boolean removeVulnerabilityFromSecurityProfile(String vtName, String spName) {
		VulnerabilityType vt = (VulnerabilityType) vc.searchVulnerability(vtName);
		SecurityProfile sp = (SecurityProfile) spc.searchSecurityProfile(spName);
		if ((vt != null) && (sp != null))
			return spc.removeVulnerabilityFromSecurityProfile(vt.getId(), sp.getId());
		return false;
	}
}
