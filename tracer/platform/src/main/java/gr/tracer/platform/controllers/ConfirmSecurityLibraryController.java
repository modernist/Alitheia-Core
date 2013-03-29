package gr.tracer.platform.controllers;

import java.util.List;

import gr.tracer.common.entities.db.ProjectFileVulnerability;
import gr.tracer.platform.components.ConfirmSecurityLibraryComponent;

public class ConfirmSecurityLibraryController {

	private ConfirmSecurityLibraryComponent cslc;
	
	public ConfirmSecurityLibraryController(ConfirmSecurityLibraryComponent cslc){
		this.cslc = cslc;
	}
	
	public boolean addToDetectedVulnerabilities(List<ProjectFileVulnerability> sProjVul){
		
		return cslc.addToDetectedVulnerabilities(sProjVul);		
	}
}