package gr.tracer.common.entities;

import gr.tracer.common.entities.db.SecurityLibrary;

import java.util.ArrayList;

public class Project {
	private String _name;
	private String _programmingLanguage;
	private String _svnURL;
	private java.util.ArrayList<Vulnerability> _detected = new ArrayList<Vulnerability>();

	public boolean addToDetectedVulnerabilitiesList(Vulnerability aAATreatVulnerabilities) {
		throw new UnsupportedOperationException();
	}

	public boolean addSecurityLibraryToProject(SecurityLibrary aAASecLibrary) {
		throw new UnsupportedOperationException();
	}
}