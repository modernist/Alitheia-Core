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
	
	/**
	 * Retrieve all the Security profiles
	 * @return A {@link java.util.List} of the Security profiles
	 */
	public List<SecurityProfile> getSecurityProfiles();

	/**
	 * Create a new Security profile
	 * @param spName The Security profile's name
	 * @param spDescription The Security profile's description
	 * @return The new Security profile or null if the Security profile is not created
	 */
	public SecurityProfile createSecurityProfile(String spName, String spDescription);

	/**
	 * Search a Security profile by name
	 * @param spName The Security profile's name
	 * @return The Security profile or null if the Security profile is not found
	 */
	public SecurityProfile searchSecurityProfile(String spName);

	/**
	 * Add a Vulnerability type to a Security profile
	 * @param vtName The Vulnerability type's name
	 * @param spName The Security profile's name
	 * @return true or false whether the method executed successful or not
	 */
	public boolean addVulnerabilityTypeToSecurityProfile(String vtName, String spName);

	/**
	 * Remove a Vulnerability type to a Security profile
	 * @param vtName The Vulnerability type's name
	 * @param spName The Security profile's name
	 * @return true or false whether the method executed successful or not
	 */
	public boolean removeVulnerabilityTypeFromSecurityProfile(String vtName, String spName);
	
	
	/*
	 * The methods for handling Monitored Project List's operations
	 */
	
	/**
	 * Retrieve all Monitored project lists
	 * @return A {@link java.util.List} with the Monitored project lists
	 */
	public List<MonitoredProjectList> getMonitoredProjectLists();
		
	/**
	 * Create a new Monitored project list
	 * @param mplName The Monitored project list's name
	 * @param mplDescription The Monitored project list's description
	 * @param userName The User's name, that creates the Monitored project list
	 * @param secProfName The Security profile's name, that is associated with the Monitored project list
	 * @return The Monitored project list or null if the Monitored project list is not created
	 */
	public MonitoredProjectList createMonitoredProjectList(String mplName, String mplDescription, String userName, String secProfName);
	
	/**
	 * Create a new Monitored project list
	 * @param mplName The Monitored project list's name
	 * @param mplDescription The Monitored project list's description
	 * @param userName The User's name, that creates the Monitored project list
	 * @return The Monitored project list or null if the Monitored project list is not created
	 */
	public MonitoredProjectList createMonitoredProjectList(String mplName, String mplDescription, String userName);

	/**
	 * Search a Monitored project list by name
	 * @param mplName The Monitored project list's name
	 * @return The Monitored project list or not if the Monitored project list is not found
	 */
	public MonitoredProjectList searchMonitoredProjectList(String mplName);
	
	/**
	 * Associate a Security profile with a Monitored project list
	 * @param spName The Security profile's name
	 * @param mplName The Monitored project list's name
	 * @return true or false whether the method executed successful or not
	 */
	public boolean setSecurityProfileToList(String spName, String mplName);
	
	/**
	 * Add a Stored project to a Monitored project list
	 * @param monProjList The Stored project's name
	 * @param projFileName The Monitored project list's name
	 * @return true or false whether the method executed successful or not
	 */
	public boolean addProjectToMonitoredProjectList(String monProjList, String projFileName);
	
	/**
	 * Remove a Stored project to a Monitored project list
	 * @param monProjList The Stored project's name
	 * @param projFileName The Monitored project list's name
	 * @return true or false whether the method executed successful or not
	 */
	public boolean removeProjectFromMonitoredProjectList(String monProjList, String projFileName);
	
	
	/*
	 * The methods for handling Security Library's operations
	 */
	/**
	 * Retrieve all Security libraries
	 * @return A {@link java.util.List} of Security libraries
	 */
	public List<SecurityLibrary> getSecurityLibraries();
	
	/**
	 * Create a Security library
	 * @param slName The Security library's name
	 * @param slDescription The Security library's description
	 * @return The new Security library or null if the Security library is not created
	 */
	public SecurityLibrary createSecurityLibrary(String slName, String slDescription);

	/**
	 * Search a Security library by name
	 * @param slName The Security library's name
	 * @return A Security library or null if the Security library is not found
	 */
	public SecurityLibrary searchSecurityLibrary(String slName);
	
	
	/*
	 * The methods for handling Vulnerability Type's operations
	 */
	/**
	 * Retrieve all Vulnerability types
	 * @return A {@link java.util.List} of Vulnerability types
	 */
	public List<VulnerabilityType> getVulnerabilityTypes();
	
	/**
	 * Create a new Vulnerability type
	 * @param vtName The Vulnerability type's name
	 * @param vtDescription The Vulnerability type's description
	 * @return The new Vulnerability type or null if the Vulnerability type is not created
	 */
	public VulnerabilityType createVulnerabilityType(String vtName, String vtDescription);

	/**
	 * Search a Vulnerability type by name
	 * @param vtName The Vulnerability type's name
	 * @return A Vulnerability type or null if the Vulnerability type is not found
	 */
	public VulnerabilityType searchVulnerabilityType(String vtName);
	
	/**
	 * Associate a Vulnerability type with a Security library
	 * @param slName The Security library's name
	 * @param vtName The Vulnerability type's name
	 * @return true or false whether the method executed successful or not
	 */
	public boolean addSecurityLibraryToVulnerabilityType(String slName, String vtName);
}
