package gr.tracer.platform.components;

import java.util.List;

import gr.tracer.common.entities.db.MonitoredProjectList;
import gr.tracer.common.entities.db.SecurityLibrary;
import gr.tracer.common.entities.db.SecurityProfile;
import gr.tracer.common.entities.db.VulnerabilityType;
import gr.tracer.platform.TracerComponent;

public interface SecurityProfileComponent extends TracerComponent {
	
	/*
	 * The methods for handling Security Profile's operations
	 */
	
	public List<SecurityProfile> getSecurityProfiles();

	public boolean addVulnerabilityTypeToSecurityProfile(String vtName, String spName);

	public SecurityProfile createSecurityProfile(String spName, String spDescription);

	public SecurityProfile searchSecurityProfile(String spName);

	public SecurityProfile getSecurityProfile(int spId);

	public boolean removeVulnerabilityTypeFromSecurityProfile(String vtName, String spName);
	
	
	/*
	 * The methods for handling Monitored Project List's operations
	 */
	
	public List<MonitoredProjectList> getMonitoredProjectList();

	public MonitoredProjectList getMonitoredProjectList(int mplId);
		
	public MonitoredProjectList createMonitoredProjectList(String mplName, String mplDescription, String userName);

	public MonitoredProjectList searchMonitoredProjectList(String mplName);
	
	public boolean setSecurityProfileToList(String spName, String mplName);
	
	public boolean addProjectFromMonitoredProjectList(String monProjList, String projName);
	
	public boolean removeProjectFromMonitoredProjectList(String monProjList, String projName);
	
	
	/*
	 * The methods for handling Security Library's operations
	 */
	
	public List<SecurityLibrary> getSecurityLibrary();

	public SecurityLibrary getSecurityLibrary(int slId);
		
	public SecurityLibrary createSecurityLibrary(String slName, String slDescription);

	public SecurityLibrary searchSecurityLibrary(String slName);
	
	
	/*
	 * The methods for handling Vulnerability Type's operations
	 */
	
	public List<VulnerabilityType> getVulnerabilityTypeList();

	public VulnerabilityType getVulnerabilityType(int vtId);
		
	public VulnerabilityType createVulnerabilityType(String vtName, String vtDescription);

	public VulnerabilityType searchVulnerabilityType(String vtName);
	
	public boolean addSecurityLibraryToVulnerabilityType(String slName, String vtName);
}
