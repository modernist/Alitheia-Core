package gr.tracer.platform.controllers;

import java.util.List;

import gr.tracer.common.entities.db.ProjectFileVulnerability;
import gr.tracer.platform.TracerPlatform;
import gr.tracer.platform.components.SecurityDetectionComponent;
import gr.tracer.platform.components.SecurityDetectionComponentImpl;

public class SecurityDetectionController {

	public SecurityDetectionController(){
	}
	
	public boolean addToDetectedVulnerabilities(List<ProjectFileVulnerability> sProjVul){
		
		return ((SecurityDetectionComponentImpl) TracerPlatform.getInstance().getComponent(SecurityDetectionComponent.class)).addToDetectedVulnerabilities(sProjVul);		
	}
}