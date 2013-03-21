package gr.tracer.platform.components;

import gr.tracer.common.entities.db.ProjectFileVulnerability;
import gr.tracer.platform.TracerComponent;

import java.util.List;

public interface ConfirmSecurityLibraryComponent extends TracerComponent {
	
	public boolean setLibraryApplication(boolean aATreatVulnerability);
	
	public boolean addToDetectedVulnerabilities(List<ProjectFileVulnerability> sProjVul);

}
