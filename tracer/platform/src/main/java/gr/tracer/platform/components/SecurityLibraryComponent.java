package gr.tracer.platform.components;

import java.util.List;

import gr.tracer.common.entities.db.SecurityLibrary;
import gr.tracer.platform.TracerComponent;

public interface SecurityLibraryComponent extends TracerComponent {
	
	public List<SecurityLibrary> getSecurityLibrary();

	public SecurityLibrary getSecurityLibrary(int slId);
		
	public boolean createSecurityLibrary(String slName, String slDescription);

	public SecurityLibrary searchSecurityLibrary(String slName);
}
