package gr.tracer.common.entities;

import gr.tracer.common.entities.db.SecurityLibrary;
import gr.tracer.common.entities.db.SecurityProfile;

import java.util.ArrayList;

public class ObservedProjectList {
	private java.util.ArrayList<SecurityProfile> _checkedFor = new ArrayList<SecurityProfile>();
	private java.util.ArrayList<Project> _projects = new ArrayList<Project>();

	public boolean addProjectToObservedProjectList(Project aAAP) {
		throw new UnsupportedOperationException();
	}

	public boolean setSecurityProfileToList(SecurityProfile aAAP) {
		throw new UnsupportedOperationException();
	}

	public void detectVulnerabilities() {
		throw new UnsupportedOperationException();
	}

	public boolean addToDetectedVulnerabilitiesList(Vulnerability aAADetVulnerabilities) {
		throw new UnsupportedOperationException();
	}

	public ArrayList<Project> getProjects() {
		throw new UnsupportedOperationException();
	}

	public boolean removeProjectFromObservedProjectList(int aAAProj_index) {
		throw new UnsupportedOperationException();
	}

	public boolean addSecurityLibraryToProject(SecurityLibrary aAASecLibrary) {
		throw new UnsupportedOperationException();
	}
}