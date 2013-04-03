package gr.tracer.platform.controllers;

import java.util.List;

import gr.tracer.common.entities.db.MonitoredProjectList;
import gr.tracer.common.entities.db.SecurityLibrary;
import gr.tracer.common.entities.db.SecurityProfile;
import gr.tracer.common.entities.db.VulnerabilityType;
import gr.tracer.platform.components.SecurityProfileComponent;
import gr.tracer.platform.components.SecurityProfileComponentImpl;

public class SecurityProfileController {
	
	private SecurityProfileComponent spc;
	
	SecurityProfileController (){
		spc = new SecurityProfileComponentImpl();
	}
	
	
	/*
	 * The methods for handling Security Profile's operations
	 */	
	public List<SecurityProfile> getSecurityProfiles() {
		return spc.getSecurityProfiles();	
	}

	public boolean addVulnerabilityTypeToSecurityProfile(String vtName, String spName) {
		return spc.addVulnerabilityTypeToSecurityProfile(vtName, spName);
	}

	public SecurityProfile createSecurityProfile(String spName, String spType) {
		return spc.createSecurityProfile(spName, spType);
	}

	public SecurityProfile searchSecurityProfile(String spName) {
		return spc.searchSecurityProfile(spName);
	}

	public SecurityProfile getSecurityProfile(int spId) {
		return spc.getSecurityProfile(spId);
	}

	public boolean removeVulnerabilityFromSecurityProfile(String vtName, String spName) {
		return spc.removeVulnerabilityTypeFromSecurityProfile(vtName, spName);
		
	}
	

	/*
	 * The methods for handling Monitored Project List's operations
	 */
	public MonitoredProjectList getMonitoredProjectList(int mplId) {
		return spc.getMonitoredProjectList(mplId);
	}

	public MonitoredProjectList createMonitoredProjectList(String mplName, String mplDescription, String userName) {
		return spc.createMonitoredProjectList(mplName, mplDescription, userName);
	}

	public boolean setSecurityProfileToList(String spName, String mplName) {
		return spc.setSecurityProfileToList(spName, mplName);
	}

	public MonitoredProjectList searchMonitoredProjectList(String mplName) {
		return spc.searchMonitoredProjectList(mplName);
	}

	public boolean addProjectFromMonitoredProjectList(String monProjList, String projName) {
		return spc.addProjectFromMonitoredProjectList(monProjList, projName);
	}
	
	public boolean removeProjectFromMonitoredProjectList(String monProjList, String projName) {
		return spc.removeProjectFromMonitoredProjectList(monProjList, projName);
	}
	
	
	/*
	 * The methods for handling Vulnerability Type's operations
	 */
	
	public List<VulnerabilityType> getVulnerabilityTypeList() {
		return spc.getVulnerabilityTypeList();
	}

	public VulnerabilityType getVulnerabilityType(int vtId) {
		return spc.getVulnerabilityType(vtId);
	}
	
	
	public boolean createVulnerabilityType(String vtName, String vtDescription, String slName, String slDescription) {
		SecurityLibrary sl = spc.createSecurityLibrary(slName, slDescription);
		VulnerabilityType vt = spc.createVulnerabilityType(vtName, vtDescription);
		return spc.addSecurityLibraryToVulnerabilityType(slName, vtDescription);
	}

	public VulnerabilityType searchVulnerabilityType(String vtName) {
		return spc.searchVulnerabilityType(vtName);
	}	
	
}
