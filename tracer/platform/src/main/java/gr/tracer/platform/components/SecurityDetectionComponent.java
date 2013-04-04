package gr.tracer.platform.components;

import gr.tracer.common.entities.db.ProjectFileVulnerability;
import gr.tracer.platform.TracerComponent;

import java.util.List;

public interface SecurityDetectionComponent extends TracerComponent {
	
	/**
	 * Record detected Vulnerability types to the database
	 * @param sProjVul A {@link java.util.List} of Project file vulnerabilities
	 * @return true or false whether the method executed successful or not
	 */
	public boolean addToDetectedVulnerabilities(List<ProjectFileVulnerability> sProjVul);

}
