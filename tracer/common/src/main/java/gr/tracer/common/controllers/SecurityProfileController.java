package gr.tracer.common.controllers;

import gr.tracer.common.entities.SecurityProfile;
import gr.tracer.common.entities.Vulnerability;
import gr.tracer.common.entities.VulnerabilityDetector;
import gr.tracer.common.entities.VulnerabilityDetectorList;

public class SecurityProfileController {
	public VulnerabilityDetectorController _getAvailableVulnerabilityDetectors;
	public ObservedProjectListController _getSecurityProfile;

	public SecurityProfile getSecurityProfiles() {
		throw new UnsupportedOperationException();
	}

	public boolean addVulnerabilityToSecurityProfile(Vulnerability aAVd) {
		throw new UnsupportedOperationException();
	}

	public VulnerabilityDetectorList createSecurityProfile(String aAName, String aAType) {
		throw new UnsupportedOperationException();
	}

	public boolean searchSecurityProfile(String aAName) {
		throw new UnsupportedOperationException();
	}

	public boolean addSecurityProfileToList(SecurityProfile aASp) {
		throw new UnsupportedOperationException();
	}

	public SecurityProfile getSecurityProfile(int aAProfile_index) {
		throw new UnsupportedOperationException();
	}

	public boolean removeVulnerabilityFromSecurityProfile(Vulnerability aAVd) {
		throw new UnsupportedOperationException();
	}

	public SecurityProfile getSecurityProfile() {
		throw new UnsupportedOperationException();
	}

	public boolean addVulnerabilityDetectorToSecurityProfile(int aAVul_id) {
		throw new UnsupportedOperationException();
	}
}
