package gr.tracer.common.entities;

import java.util.ArrayList;

public class SecurityProfile {
	private String _name;
	private boolean isInstant;
	private java.util.ArrayList<Vulnerability> _ableToDetect = new ArrayList<Vulnerability>();

	public boolean addVulnerabilityToSecurityProfile(Vulnerability aAAVd) {
		throw new UnsupportedOperationException();
	}

	public boolean runOnProject() {
		throw new UnsupportedOperationException();
	}

	public Vulnerability detectVulnerabilities(Project aAAPr) {
		throw new UnsupportedOperationException();
	}

	public boolean removeVulnerabilityToSecurityProfile(Vulnerability aAAVd) {
		throw new UnsupportedOperationException();
	}

}