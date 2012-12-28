package gr.tracer.common.entities;

import java.util.ArrayList;

public class Project {
	private String _name;
	private String _programmingLanguage;
	private String _svnURL;
	private java.util.ArrayList<Files> _files = new ArrayList<Files>();
	private java.util.ArrayList<Vulnerability> _detected = new ArrayList<Vulnerability>();

	public Files getFiles() {
		throw new UnsupportedOperationException();
	}

	public void addFileToProject() {
		throw new UnsupportedOperationException();
	}
	
	public boolean addToDetectedVulnerabilitiesList(Vulnerability aAATreatVulnerabilities) {
		throw new UnsupportedOperationException();
	}

	public boolean addSecurityLibraryToProject(SecurityLibrary aAASecLibrary) {
		throw new UnsupportedOperationException();
	}
}