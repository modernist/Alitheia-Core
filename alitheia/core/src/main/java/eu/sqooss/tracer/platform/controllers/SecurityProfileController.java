package eu.sqooss.tracer.platform.controllers;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import eu.sqooss.tracer.common.entities.db.MonitoredProjectList;
import eu.sqooss.tracer.common.entities.db.SecurityLibrary;
import eu.sqooss.tracer.common.entities.db.SecurityProfile;
import eu.sqooss.tracer.common.entities.db.VulnerabilityType;
import eu.sqooss.tracer.platform.TracerPlatform;
import eu.sqooss.tracer.platform.components.SecurityProfileComponent;

@Produces(MediaType.APPLICATION_JSON)
@Path("/tracerapi")
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
	@GET
	//@Produces({"application/xml", "application/json"})
	@Path("/SecurityProfile/")
	public List<SecurityProfile> getSecurityProfiles() {
		return spc.getSecurityProfiles();	
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#addVulnerabilityTypeToSecurityProfile(java.lang.String, java.lang.String)
     */
	@PUT
	//@Produces({"application/xml", "application/json"})
	@Path("/SecurityProfile/addVulnerabilityType/{vtName}/{spName}")
	public boolean addVulnerabilityTypeToSecurityProfile(@PathParam("vtName") String vtName, @PathParam("spName") String spName) {
		return spc.addVulnerabilityTypeToSecurityProfile(vtName, spName);
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#createSecurityProfile(java.lang.String, java.lang.String)
     */
	@PUT
	//@Produces({"application/xml", "application/json"})
	@Path("/SecurityProfile/createSecurityProfile/{vtName}/{spName}")
	public SecurityProfile createSecurityProfile(@PathParam("spName") String spName, @PathParam("spType") String spType) {
		return spc.createSecurityProfile(spName, spType);
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#searchSecurityProfile(java.lang.String)
     */
	@GET
	//@Produces({"application/xml", "application/json"})
	@Path("/SecurityProfile/get-by-name/{spName}")
	public SecurityProfile searchSecurityProfile(@PathParam("spName") String spName) {
		return spc.searchSecurityProfile(spName);
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#removeVulnerabilityTypeFromSecurityProfile(java.lang.String, java.lang.String)
     */
	@PUT
	//@Produces({"application/xml", "application/json"})
	@Path("/SecurityProfile/removeVulnerabilityType/{vtName}/{spName}")
	public boolean removeVulnerabilityTypeFromSecurityProfile(@PathParam("vtName") String vtName, @PathParam("spName") String spName) {
		return spc.removeVulnerabilityTypeFromSecurityProfile(vtName, spName);
		
	}

	/*
	 * The methods for handling Monitored Project List's operations
	 */
	
	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#createMonitoredProjectList(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
	@PUT
	//@Produces({"application/xml", "application/json"})
	@Path("/MonitoredProjectList/createMonitoredProjectList/{mplName}/{mplDescription}/{userName}/SecurityProfile/{secProfName}")
	public MonitoredProjectList createMonitoredProjectList(@PathParam("mplName") String mplName, @PathParam("mplDescription") String mplDescription, @PathParam("userName") String userName, @PathParam("secProfName") String secProfName) {
		return spc.createMonitoredProjectList(mplName, mplDescription, userName, secProfName);
	}
	
	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#createMonitoredProjectList(java.lang.String, java.lang.String, java.lang.String)
     */
	@PUT
	//@Produces({"application/xml", "application/json"})
	@Path("/MonitoredProjectList/createMonitoredProjectList/{mplName}/{mplDescription}/{userName}")
	public MonitoredProjectList createMonitoredProjectList(@PathParam("mplName") String mplName, @PathParam("mplDescription") String mplDescription, @PathParam("userName") String userName) {
		return spc.createMonitoredProjectList(mplName, mplDescription, userName);
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#setSecurityProfileToList(java.lang.String, java.lang.String)
     */
	@PUT
	//@Produces({"application/xml", "application/json"})
	@Path("/MonitoredProjectList/setSecurityProfile/{spName}/{mplName}")
	public boolean setSecurityProfileToList(@PathParam("spName") String spName, @PathParam("mplName") String mplName) {
		return spc.setSecurityProfileToList(spName, mplName);
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#searchMonitoredProjectList(java.lang.String)
     */
	@GET
	//@Produces({"application/xml", "application/json"})
	@Path("/MonitoredProjectList/get-by-name/{mplName}")
	public MonitoredProjectList searchMonitoredProjectList(@PathParam("mplName") String mplName) {
		return spc.searchMonitoredProjectList(mplName);
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#addProjectToMonitoredProjectList(java.lang.String, java.lang.String)
     */
	@PUT
	//@Produces({"application/xml", "application/json"})
	@Path("/MonitoredProjectList/addProject/{monProjList}/{projName}")
	public boolean addProjectToMonitoredProjectList(@PathParam("monProjList") String monProjList, @PathParam("projName") String projName) {
		return spc.addProjectToMonitoredProjectList(monProjList, projName);
	}
	
	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#removeProjectFromMonitoredProjectList(java.lang.String, java.lang.String)
     */
	@PUT
	//@Produces({"application/xml", "application/json"})
	@Path("/MonitoredProjectList/removeProject/{monProjList}/{projName}")
	public boolean removeProjectFromMonitoredProjectList(@PathParam("monProjList") String monProjList, @PathParam("projName") String projName) {
		return spc.removeProjectFromMonitoredProjectList(monProjList, projName);
	}
	
	
	/*
	 * The methods for handling Vulnerability Type's operations
	 */
	
	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#getVulnerabilityTypes()
     */
	@GET
	//@Produces({"application/xml", "application/json"})
	@Path("/VulnerabilityType/")
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
	@PUT
	//@Produces({"application/xml", "application/json"})
	@Path("/VulnerabilityType/createVulnerabilityType/{vtName}/{vtDescription}/SecurityLibrary/{slName}/{slDescription}")
	public boolean createVulnerabilityType(@PathParam("vtName") String vtName, @PathParam("vtDescription") String vtDescription, @PathParam("slName") String slName, @PathParam("slDescription") String slDescription) {
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
	@PUT
	//@Produces({"application/xml", "application/json"})
	@Path("/VulnerabilityType/createVulnerabilityType/{vtName}/{vtDescription}")
	public VulnerabilityType createVulnerabilityType(@PathParam("vtName") String vtName, @PathParam("vtDescription") String vtDescription) {
		return spc.createVulnerabilityType(vtName, vtDescription);
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#addSecurityLibraryToVulnerabilityType(java.lang.String, java.lang.String)
     */
	@PUT
	//@Produces({"application/xml", "application/json"})
	@Path("/MonitoredProjectList/addSecurityLibrary/{slName}/{vtName}")
	public boolean addSecurityLibraryToVulnerabilityType(@PathParam("slName") String slName, @PathParam("vtName") String vtName) {
		return spc.addSecurityLibraryToVulnerabilityType(slName, vtName);
	}
	
	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#searchVulnerabilityType(java.lang.String)
     */
	@GET
	//@Produces({"application/xml", "application/json"})
	@Path("/MonitoredProjectList/get-by-name/{vtName}")
	public VulnerabilityType searchVulnerabilityType(String vtName) {
		return spc.searchVulnerabilityType(vtName);
	}
}
