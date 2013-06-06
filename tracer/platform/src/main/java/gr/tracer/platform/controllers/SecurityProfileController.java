package gr.tracer.platform.controllers;

import java.util.List;

import gr.tracer.common.entities.db.MonitoredProjectList;
import gr.tracer.common.entities.db.SecurityLibrary;
import gr.tracer.common.entities.db.SecurityProfile;
import gr.tracer.common.entities.db.VulnerabilityType;
import gr.tracer.platform.TracerPlatform;
import gr.tracer.platform.components.SecurityProfileComponent;
import gr.tracer.platform.components.impl.SecurityProfileComponentImpl;

public class SecurityProfileController {
	
	SecurityProfileComponent spc = null;
	
	/*
	 * Parameterless constructor of the class.
	 * Retrieving an instance of SecurityProfileComponent.
	 */
	public SecurityProfileController (){
		spc = TracerPlatform.getInstance().getComponent(SecurityProfileComponent.class);
	}
	
	/*
	 * The methods for handling Security Profile's operations
	 */
	
	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#getSecurityProfiles()
     */
	public List<SecurityProfile> getSecurityProfiles() {
		return spc.getSecurityProfiles();	
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#addVulnerabilityTypeToSecurityProfile(java.lang.String, java.lang.String)
     */
	public boolean addVulnerabilityTypeToSecurityProfile(String vtName, String spName) {
		return spc.addVulnerabilityTypeToSecurityProfile(vtName, spName);
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#createSecurityProfile(java.lang.String, java.lang.String)
     */
	public SecurityProfile createSecurityProfile(String spName, String spType) {
		return spc.createSecurityProfile(spName, spType);
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#searchSecurityProfile(java.lang.String)
     */
	public SecurityProfile searchSecurityProfile(String spName) {
		return spc.searchSecurityProfile(spName);
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#removeVulnerabilityTypeFromSecurityProfile(java.lang.String, java.lang.String)
     */
	public boolean removeVulnerabilityTypeFromSecurityProfile(String vtName, String spName) {
		return spc.removeVulnerabilityTypeFromSecurityProfile(vtName, spName);
		
	}

	/*
	 * The methods for handling Monitored Project List's operations
	 */
	
	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#createMonitoredProjectList(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
	public MonitoredProjectList createMonitoredProjectList(String mplName, String mplDescription, String userName, String secProfName) {
		return spc.createMonitoredProjectList(mplName, mplDescription, userName, secProfName);
	}
	
	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#createMonitoredProjectList(java.lang.String, java.lang.String, java.lang.String)
     */
	public MonitoredProjectList createMonitoredProjectList(String mplName, String mplDescription, String userName) {
		return spc.createMonitoredProjectList(mplName, mplDescription, userName);
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#setSecurityProfileToList(java.lang.String, java.lang.String)
     */
	public boolean setSecurityProfileToList(String spName, String mplName) {
		return spc.setSecurityProfileToList(spName, mplName);
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#searchMonitoredProjectList(java.lang.String)
     */
	public MonitoredProjectList searchMonitoredProjectList(String mplName) {
		return spc.searchMonitoredProjectList(mplName);
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#addProjectToMonitoredProjectList(java.lang.String, java.lang.String)
     */
	public boolean addProjectToMonitoredProjectList(String monProjList, String projName) {
		return spc.addProjectToMonitoredProjectList(monProjList, projName);
	}
	
	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#removeProjectFromMonitoredProjectList(java.lang.String, java.lang.String)
     */
	public boolean removeProjectFromMonitoredProjectList(String monProjList, String projName) {
		return spc.removeProjectFromMonitoredProjectList(monProjList, projName);
	}
	
	
	/*
	 * The methods for handling Vulnerability Type's operations
	 */
	
	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#getVulnerabilityTypes()
     */
	public List<VulnerabilityType> getVulnerabilityTypes() {
		return spc.getVulnerabilityTypes();
	}

	/**
	 * Create a Vulnerability type and a Security library, that counter it, and associate them
	 * @param vtName The Vulnerability type's name
	 * @param vtDescription The Vulnerability type' description
	 * @param slName The Security library's name
	 * @param slDescription The Security library's description
	 * @return true or false whether the method executed successful or not
	 */
	public boolean createVulnerabilityType(String vtName, String vtDescription, String slName, String slDescription) {
		SecurityLibrary sl = spc.createSecurityLibrary(slName, slDescription);
		VulnerabilityType vt = spc.createVulnerabilityType(vtName, vtDescription);
		if ((sl != null) && (vt != null))
			return spc.addSecurityLibraryToVulnerabilityType(slName, vtName);
		else
			return false;
	}
	
	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#createVulnerabilityType(java.lang.String, java.lang.String)
     */
	public VulnerabilityType createVulnerabilityType(String vtName, String vtDescription) {
		return spc.createVulnerabilityType(vtName, vtDescription);
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#addSecurityLibraryToVulnerabilityType(java.lang.String, java.lang.String)
     */
	public boolean addSecurityLibraryToVulnerabilityType(String slName, String vtName) {
		return spc.addSecurityLibraryToVulnerabilityType(slName, vtName);
	}
	
	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#searchVulnerabilityType(java.lang.String)
     */
	public VulnerabilityType searchVulnerabilityType(String vtName) {
		return spc.searchVulnerabilityType(vtName);
	}	
	
}
