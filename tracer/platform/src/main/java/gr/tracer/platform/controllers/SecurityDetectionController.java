package gr.tracer.platform.controllers;

import java.util.List;

import gr.tracer.common.entities.db.ProjectFileVulnerability;
import gr.tracer.platform.TracerPlatform;
import gr.tracer.platform.components.VulnerabilityDetectionComponent;
import gr.tracer.platform.components.VulnerabilityDetectionComponentImpl;

public class SecurityDetectionController {

	public SecurityDetectionController(){
	}
	
	public boolean addToDetectedVulnerabilities(List<ProjectFileVulnerability> sProjVul){
		
		return ((VulnerabilityDetectionComponentImpl) TracerPlatform.getInstance().getComponent(VulnerabilityDetectionComponent.class)).addToDetectedVulnerabilities(sProjVul);		
	}
}