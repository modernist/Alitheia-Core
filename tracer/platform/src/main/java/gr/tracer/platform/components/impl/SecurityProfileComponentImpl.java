package gr.tracer.platform.components.impl;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import eu.sqooss.core.AlitheiaCore;
import eu.sqooss.service.db.DBService;
import eu.sqooss.service.db.StoredProject;
import eu.sqooss.service.db.User;
import eu.sqooss.service.logging.Logger;
import eu.sqooss.service.security.SecurityManager;
import eu.sqooss.service.security.UserManager;
import gr.tracer.common.entities.db.MonitoredProjectList;
import gr.tracer.common.entities.db.SecurityLibrary;
import gr.tracer.common.entities.db.SecurityProfile;
import gr.tracer.common.entities.db.VulnerabilityType;
import gr.tracer.platform.TracerPlatform;
import gr.tracer.platform.components.SecurityProfileComponent;

public class SecurityProfileComponentImpl implements SecurityProfileComponent {

	private TracerPlatform platform;
	private Logger logger;
	private DBService dbs;
	private Map<String, Object> vulTypeProps;
	private Map<String, Object> secLibProps;
	private Map<String, Object> secProfProps;
	private Map<String, Object> monProjListProps;
	private Map<String, Object> monProjListProjProps;
    private Object lockObject = new Object();
	
    /*
     * Parameterless constructor of the class.
     */
	public SecurityProfileComponentImpl() {
		vulTypeProps = new Hashtable<String, Object>(1);
		secLibProps = new Hashtable<String, Object>(1);
		secProfProps = new Hashtable<String, Object>(1);
		monProjListProps = new Hashtable<String, Object>(1);
		monProjListProjProps = new Hashtable<String, Object>(1);
	}
	
	@Override
	public void initComponent(TracerPlatform platform, Logger logger) {
		this.platform = platform;
		this.logger = logger;
	}

	@Override
	public boolean startUp() {
		this.dbs = AlitheiaCore.getInstance().getDBService();
		return true;
	}

	@Override
	public boolean shutDown() {
		return true;
	}
	
	/*
	 * The methods for handling Security Profile's operations
	 */
	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#getSecurityProfiles()
     */
	@Override
	public List<SecurityProfile> getSecurityProfiles() {
        
        StringBuilder q = new StringBuilder("from SecurityProfile sp");
        
        return (List<SecurityProfile>) dbs.doHQL(q.toString());
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#createSecurityProfile(java.lang.String, java.lang.String)
     */
	@Override
	public SecurityProfile createSecurityProfile(String spName,
			String spDescription) {
		SecurityProfile sp = new SecurityProfile();
		sp.setName(spName);
		sp.setDescription(spDescription);
		if(dbs != null && dbs.startDBSession())
    	{
    		if(dbs.addRecord(sp)) 
    			return dbs.commitDBSession() ? sp : null;
    	}
    	return null;
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#searchSecurityProfile(java.lang.String)
     */
	@Override
	public SecurityProfile searchSecurityProfile(String spName) {
		List<SecurityProfile> secProfs = null;
		try {
			if (dbs != null &&  dbs.startDBSession()) {
				synchronized (lockObject) {
					secProfProps.clear();
					secProfProps.put("name", spName);
					secProfs = dbs.findObjectsByProperties(SecurityProfile.class, secProfProps);
					return (secProfs.size() > 0 && secProfs != null) ? secProfs.get(0) : null;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.warn("Failed to retrieve security profile by name", e);
		}
		return null;
	}

	/**
	 * Retrieve a Security profile by its Id
	 * @param spId The Security profile's Id
	 * @return The Security profile or null if the Security profile is not found 
	 */
	private SecurityProfile getSecurityProfile(int spId) {
		try {
	    	if(dbs.startDBSession()) {
	    		return dbs.findObjectById(SecurityProfile.class, spId);
	    	}
	    	else
	    		return null;
    	}
    	finally {
    		if(dbs.isDBSessionActive())
    			dbs.commitDBSession();
    	}
	}
	
	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#addVulnerabilityTypeToSecurityProfile(java.lang.String, java.lang.String)
     */
	@Override
	public boolean addVulnerabilityTypeToSecurityProfile(String vtName, String spName) {
		try {
			VulnerabilityType vt = searchVulnerabilityType(vtName);
	        SecurityProfile sp = searchSecurityProfile(spName);
			dbs.startDBSession();
	        
        	if ((vt!=null) && (sp != null)) {
        		if ((vt.getDetectingSecurityProfiles().add(sp)) && (sp.getDetectedVulnerabilityTypes().add(vt))) {
        			vt = dbs.attachObjectToDBSession(vt);
        			sp = dbs.attachObjectToDBSession(sp);
        			//Session s = dbs.getActiveDBSession();
        			//s.saveOrUpdate(sp);
        			//s.saveOrUpdate(vt);
        			return true;
        		}
            } else {
            	logger.error("Vulnerability type and/or Security profile do not exist");
            }
        } catch(Exception e) {
        	logger.error("failed to associate vulnerability type to security profile", e);
        	return false;
        }
		finally {
        	dbs.commitDBSession();
        }
		return false;
	}
	
	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#removeVulnerabilityTypeFromSecurityProfile(java.lang.String, java.lang.String)
     */
	@Override
	public boolean removeVulnerabilityTypeFromSecurityProfile(String vtName, String spName) {
		try {
			VulnerabilityType vt = searchVulnerabilityType(vtName);
	        SecurityProfile sp = searchSecurityProfile(spName);
			dbs.startDBSession();
        	if ((vt!=null) && (sp != null)) {
        		if (sp.getDetectedVulnerabilityTypes().remove(vt) && vt.getDetectingSecurityProfiles().remove(sp)) {
        			vt = dbs.attachObjectToDBSession(vt);
        			sp = dbs.attachObjectToDBSession(sp);
        			return true;
        		} else {
                	logger.error("Vulnerability type do not remove from Security profile");
                	return false;
                }
            } else {
            	logger.error("Vulnerability type and/or Security profile do not exist");
            	return false;
            }
        } finally {
        	dbs.commitDBSession();
        }
	}
	
	/*
	 * The methods for handling Monitored Project List's operations
	 */
	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#getMonitoredProjectLists(java.lang.String, java.lang.String)
     */
	@Override
	public List<MonitoredProjectList> getMonitoredProjectLists() {
		StringBuilder q = new StringBuilder("from SecurityLibrary sl");
		return (List<MonitoredProjectList>) dbs.doHQL(q.toString());
	}

	/**
	 * Retrieve a Monitored project list by Id
	 * @param mplId The Monitored project list's Id
	 * @return The Monitored project list or null if the Monitored project list is not found
	 */
	private MonitoredProjectList getMonitoredProjectList(int mplId) {
		try {
	    	if(dbs.startDBSession()) {
	    		return dbs.findObjectById(MonitoredProjectList.class, mplId);
	    	}
	    	else
	    		return null;
    	}
    	finally {
    		if(dbs.isDBSessionActive())
    			dbs.commitDBSession();
    	}
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#createMonitoredProjectList(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
	@Override
	public MonitoredProjectList createMonitoredProjectList(String mplName,
			String mplDescription, String userName, String secProfName) {
		SecurityManager sm = AlitheiaCore.getInstance().getSecurityManager();
		UserManager userManager = sm.getUserManager();		
		User user = userManager.getUser(userName);
		SecurityProfile sp = searchSecurityProfile(secProfName);
		MonitoredProjectList mpl = new MonitoredProjectList();
		mpl.setName(mplName);
		mpl.setDescription(mplDescription);
		
		
		if ((user != null) && (sp != null)) {
			mpl.setUser(user);
			mpl.setSecurityProfile(sp);
		}
		else{
			logger.info("User and/or Security profile do not exist with these names");
			return null;
		}
		
		if(dbs != null && dbs.startDBSession())
    	{
    		if(dbs.addRecord(mpl)) 
    			return dbs.commitDBSession() ? mpl : null;
    	}
		return null;
	}
	
	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#createMonitoredProjectList(java.lang.String, java.lang.String, java.lang.String)
     */
	@Override
	public MonitoredProjectList createMonitoredProjectList(String mplName,
			String mplDescription, String userName) {
		SecurityManager sm = AlitheiaCore.getInstance().getSecurityManager();
		UserManager userManager = sm.getUserManager();		
		User user = userManager.getUser(userName);
		MonitoredProjectList mpl = new MonitoredProjectList();
		mpl.setName(mplName);
		mpl.setDescription(mplDescription);
		
		if (user == null) {
			logger.info("User does not exist with this name");
			return null;
		}
		
		if(dbs != null && dbs.startDBSession())
    	{
			user = dbs.attachObjectToDBSession(user);
			mpl.setUser(user);
    		if(dbs.addRecord(mpl)) 
    			return dbs.commitDBSession() ? mpl : null;
    	}
		return null;
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#searchMonitoredProjectList(java.lang.String, java.lang.String)
     */
	@Override
	public MonitoredProjectList searchMonitoredProjectList(String mplName) {
		List<MonitoredProjectList> mpls = null;
		try {
			if (dbs.startDBSession()) {
				synchronized (lockObject) {
					monProjListProps.clear();
					monProjListProps.put("name", mplName);
					mpls = dbs.findObjectsByProperties(MonitoredProjectList.class, monProjListProps);
					return (mpls.size() > 0 && mpls != null) ? mpls.get(0) : null;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.warn("Failed to retrieve monitored project list by name", e);
		}
		return null;
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#setSecurityProfileToList(java.lang.String, java.lang.String)
     */
	@Override
	public boolean setSecurityProfileToList(String spName, String mplName) {
		try {
			SecurityProfile sp = searchSecurityProfile(spName);
			MonitoredProjectList mpl = searchMonitoredProjectList(mplName);
			dbs.startDBSession();
			
			if ((sp != null) && (mpl != null)) {
				mpl = dbs.attachObjectToDBSession(mpl);
				sp = dbs.attachObjectToDBSession(sp);
				mpl.setSecurityProfile(sp);
				return true;
			} else {
				logger.error("Security profile and/or Monitored project list do not exist");
				return false;
			}
		} finally {
			dbs.commitDBSession();
		}
	}
	
	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#addProjectToMonitoredProjectList(java.lang.String, java.lang.String)
     */
	@Override
	public boolean addProjectToMonitoredProjectList(String storProjName,
			String monProjList) {
		try {
			MonitoredProjectList mpl = searchMonitoredProjectList(monProjList);
			dbs.startDBSession();
			StoredProject project = StoredProject.getProjectByName(storProjName);
			
			boolean result = false;
			
			if ((mpl != null) && (project != null)) {
				
				if(mpl.getProjects().contains(project)) {
					logger.info("StoredProject is already associated with MonitoredProjectList");
					return true;
				}
				
				project = dbs.attachObjectToDBSession(project);
				mpl = dbs.attachObjectToDBSession(mpl);
				result = mpl.getProjects().add(project);
				
				return result & dbs.commitDBSession();
			}
			else{
				logger.info("MonitoredProjectList or StoredProject do not exist.");
				return false;
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.warn("Failed to associate project to monitored project list", e);
		}
		return false;
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#removeProjectFromMonitoredProjectList(java.lang.String, java.lang.String)
     */
	@Override
	public boolean removeProjectFromMonitoredProjectList(String storProjName,
			String monProjList) {
		try {
			MonitoredProjectList mpl = searchMonitoredProjectList(monProjList);
			dbs.startDBSession();
			StoredProject project = StoredProject.getProjectByName(storProjName);
			
			if ((mpl != null) && (project != null)){
					return mpl.getProjects().remove(project) & dbs.commitDBSession();
			} else {
				logger.info("MonitoredProjectList or StoredProject do not exist.");
				return false;
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.warn("Failed to remove project from monitored project list", e);
		}
		return false;
	}
		
	/*
	 * The methods for handling Monitored Project List Project's operations
	 */
	/**
	 * Retrieve an association between a Monitored project list and a Stored project by name
	 * @param monProjList The Monitored project list's name
	 * @param projFileName The Stored project's name
	 * @return The retrieved project or null if the association is not found
	 */
	public StoredProject getMonitoredProjectListProject(String monProjList, String projFileName) {
		try {
			MonitoredProjectList mpl = searchMonitoredProjectList(monProjList);
			StoredProject project = StoredProject.getProjectByName(projFileName);
			if ((mpl != null) && (project != null)) {
				if(mpl.getProjects().contains(project))
					return project;
				else
					return null;
			}
			else {
				logger.error("MonitoredProjectList or StoredProject do not exist.");
				return null;
			}
		}
    	finally {
    		if(dbs.isDBSessionActive())
    			dbs.commitDBSession();
    	}
	}
	
	/*
	 * The methods for handling Security Libraries operations
	 */
	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#getSecurityLibraries()
     */
	@Override
	public List<SecurityLibrary> getSecurityLibraries() {
		StringBuilder q = new StringBuilder("from SecurityLibrary sl");
		return (List<SecurityLibrary>) dbs.doHQL(q.toString());
	}

	/**
	 * Retrieve a Security library by Id 
	 * @param slId The Security library's Id
	 * @return A Security library or null if the Security library is not found
	 */
	private SecurityLibrary getSecurityLibrary(int slId) {
		try {
	    	if(dbs.startDBSession()) {
	    		return dbs.findObjectById(SecurityLibrary.class, slId);
	    	}
	    	else
	    		return null;
    	}
    	finally {
    		if(dbs.isDBSessionActive())
    			dbs.commitDBSession();
    	}
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#createSecurityLibrary(java.lang.String, java.lang.String)
     */
	@Override
	public SecurityLibrary createSecurityLibrary(String slName, String slDescription) {
		SecurityLibrary sl = new SecurityLibrary();
		sl.setName(slName);
		sl.setDescription(slDescription);
		if(dbs != null && dbs.startDBSession())
    	{
    		if(dbs.addRecord(sl)) 
    			return dbs.commitDBSession() ? sl : null;
    	}
    	return null;
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#searchSecurityLibrary(java.lang.String)
     */
	@Override
	public SecurityLibrary searchSecurityLibrary(String slName) {
		List<SecurityLibrary> secLibs = null;
		try {
			if (dbs.startDBSession()) {
				synchronized (lockObject) {
					secLibProps.clear();
					secLibProps.put("name", slName);
					secLibs = dbs.findObjectsByProperties(SecurityLibrary.class, secLibProps);
					return (secLibs.size() > 0 && secLibs != null) ? secLibs.get(0) : null;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.warn("Failed to retrieve security library by name", e);
		}
		return null;
	}
	
	/*
	 * The methods for handling Vulnerability Type's operations
	 */
	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#getVulnerabilityTypes()
     */
	@Override
	public List<VulnerabilityType> getVulnerabilityTypes() {
		
		return VulnerabilityType.getVulnerabilityTypes();
	}

	/**
	 * Retrieve a Vulnerability type by Id
	 * @param vtId The Vulnerability type's Id
	 * @return A Vulnerability type or null if the Vulnerability type is not found
	 */
	private VulnerabilityType getVulnerabilityType(int vtId) {
		try {
	    	if(dbs.startDBSession()) {
	    		return dbs.findObjectById(VulnerabilityType.class, vtId);
	    	}
	    	else
	    		return null;
    	}
    	finally {
    		if(dbs.isDBSessionActive())
    			dbs.commitDBSession();
    	}
	}
	
	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#createVulnerabilityType(java.lang.String, java.lang.String)
     */
	@Override
	public VulnerabilityType createVulnerabilityType(String vtName, String vtDescription) {
		VulnerabilityType vt = new VulnerabilityType();
		vt.setName(vtName);
		vt.setDescription(vtDescription);
		if(dbs != null && dbs.startDBSession())
    	{
    		if(dbs.addRecord(vt))
    			return dbs.commitDBSession() ? vt : null;
    	}
    	return null;
	}

	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#searchVulnerabilityType(java.lang.String)
     */
	@Override
	public VulnerabilityType searchVulnerabilityType(String vtName) {
		List<VulnerabilityType> vulTypes = null;
		try {
			if (dbs.startDBSession()) {
				synchronized (lockObject) {
					vulTypeProps.clear();
					vulTypeProps.put("name", vtName);
					vulTypes = dbs.findObjectsByProperties(
							VulnerabilityType.class, vulTypeProps);
					return (vulTypes.size() > 0 && vulTypes != null) ? vulTypes.get(0) : null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("Failed to retrieve monitored project list by name", e);
		}
		return null;
	}
	
	/**
     * @see gr.tracer.platform.components.SecurityProfileComponent#addSecurityLibraryToVulnerabilityType(java.lang.String, java.lang.String)
     */
	@Override
	public boolean addSecurityLibraryToVulnerabilityType(String slName, String vtName) {
		try {
			SecurityLibrary sl = searchSecurityLibrary(slName);
	    	VulnerabilityType vt = searchVulnerabilityType(vtName);
	    	dbs.startDBSession();
	    	
			if ((sl!=null) && (vt != null)) {
        		if( ((sl.getTreatedVulnerabilityTypes().add(vt)) && vt.getTreatingSecurityLibraries().add(sl))) {
        			vt = dbs.attachObjectToDBSession(vt);
        			sl = dbs.attachObjectToDBSession(sl);
        			//Session s = dbs.getActiveDBSession();
        			//s.saveOrUpdate(sl);
        			//s.saveOrUpdate(vt);
        			
        			return dbs.commitDBSession();
        		}
        	} else {
        		logger.error("SecurityLibrary or VulnerabilityType do not exist");
        	}
        } catch(Exception e) {
        	logger.error("Failed to associate security library to vulnerability type", e);
        }
		return false;
    }
}
