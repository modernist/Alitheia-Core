package gr.tracer.platform.controllers;

import java.util.List;

import gr.tracer.common.entities.db.MonitoredProjectList;
import gr.tracer.common.entities.db.SecurityLibrary;
import gr.tracer.common.entities.db.SecurityProfile;
import gr.tracer.common.entities.db.VulnerabilityType;
import gr.tracer.platform.TracerPlatform;
import gr.tracer.platform.components.SecurityProfileComponent;
import gr.tracer.platform.components.SecurityProfileComponentImpl;

public class SecurityProfileController {
	
	SecurityProfileController (){
		
	}
	
	
	/*
	 * The methods for handling Security Profile's operations
	 */	
	public List<SecurityProfile> getSecurityProfiles() {
		return ((SecurityProfileComponentImpl) TracerPlatform.getInstance().getComponent(SecurityProfileComponent.class)).getSecurityProfiles();	
	}

	public boolean addVulnerabilityTypeToSecurityProfile(String vtName, String spName) {
		return ((SecurityProfileComponentImpl) TracerPlatform.getInstance().getComponent(SecurityProfileComponent.class)).addVulnerabilityTypeToSecurityProfile(vtName, spName);
	}

	public SecurityProfile createSecurityProfile(String spName, String spType) {
		return ((SecurityProfileComponentImpl) TracerPlatform.getInstance().getComponent(SecurityProfileComponent.class)).createSecurityProfile(spName, spType);
	}

	public SecurityProfile searchSecurityProfile(String spName) {
		return ((SecurityProfileComponentImpl) TracerPlatform.getInstance().getComponent(SecurityProfileComponent.class)).searchSecurityProfile(spName);
	}

	public boolean removeVulnerabilityFromSecurityProfile(String vtName, String spName) {
		return ((SecurityProfileComponentImpl) TracerPlatform.getInstance().getComponent(SecurityProfileComponent.class)).removeVulnerabilityTypeFromSecurityProfile(vtName, spName);
		
	}
	

	/*
	 * The methods for handling Monitored Project List's operations
	 */
	public MonitoredProjectList createMonitoredProjectList(String mplName, String mplDescription, String userName, String secProfName) {
		return ((SecurityProfileComponentImpl) TracerPlatform.getInstance().getComponent(SecurityProfileComponent.class)).createMonitoredProjectList(mplName, mplDescription, userName, secProfName);
	}

	public boolean setSecurityProfileToList(String spName, String mplName) {
		return ((SecurityProfileComponentImpl) TracerPlatform.getInstance().getComponent(SecurityProfileComponent.class)).setSecurityProfileToList(spName, mplName);
	}

	public MonitoredProjectList searchMonitoredProjectList(String mplName) {
		return ((SecurityProfileComponentImpl) TracerPlatform.getInstance().getComponent(SecurityProfileComponent.class)).searchMonitoredProjectList(mplName);
	}

	public boolean addProjectFromMonitoredProjectList(String monProjList, String projName) {
		return ((SecurityProfileComponentImpl) TracerPlatform.getInstance().getComponent(SecurityProfileComponent.class)).addProjectToMonitoredProjectList(monProjList, projName);
	}
	
	public boolean removeProjectFromMonitoredProjectList(String monProjList, String projName) {
		return ((SecurityProfileComponentImpl) TracerPlatform.getInstance().getComponent(SecurityProfileComponent.class)).removeProjectFromMonitoredProjectList(monProjList, projName);
	}
	
	
	/*
	 * The methods for handling Vulnerability Type's operations
	 */
	
	public List<VulnerabilityType> getVulnerabilityTypeList() {
		return ((SecurityProfileComponentImpl) TracerPlatform.getInstance().getComponent(SecurityProfileComponent.class)).getVulnerabilityTypes();
	}

	public boolean createVulnerabilityType(String vtName, String vtDescription, String slName, String slDescription) {
		SecurityLibrary sl = ((SecurityProfileComponentImpl) TracerPlatform.getInstance().getComponent(SecurityProfileComponent.class)).createSecurityLibrary(slName, slDescription);
		VulnerabilityType vt = ((SecurityProfileComponentImpl) TracerPlatform.getInstance().getComponent(SecurityProfileComponent.class)).createVulnerabilityType(vtName, vtDescription);
		return ((SecurityProfileComponentImpl) TracerPlatform.getInstance().getComponent(SecurityProfileComponent.class)).addSecurityLibraryToVulnerabilityType(slName, vtDescription);
	}

	public VulnerabilityType searchVulnerabilityType(String vtName) {
		return ((SecurityProfileComponentImpl) TracerPlatform.getInstance().getComponent(SecurityProfileComponent.class)).searchVulnerabilityType(vtName);
	}	
	
}
