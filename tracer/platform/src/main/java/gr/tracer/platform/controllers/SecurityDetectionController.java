package gr.tracer.platform.controllers;

import java.util.List;

import gr.tracer.common.entities.db.ProjectFileVulnerability;
import gr.tracer.platform.components.SecurityDetectionComponent;

public class SecurityDetectionController {

	private SecurityDetectionComponent sdc;
	
	public SecurityDetectionController(SecurityDetectionComponent sdc){
		this.sdc = sdc;
	}
	
	public boolean addToDetectedVulnerabilities(List<ProjectFileVulnerability> sProjVul){
		
		return sdc.addToDetectedVulnerabilities(sProjVul);		
	}
}