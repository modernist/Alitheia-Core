package gr.tracer.platform.components;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import eu.sqooss.core.AlitheiaCore;
import eu.sqooss.service.db.DBService;
import eu.sqooss.service.db.StoredProject;
import eu.sqooss.service.db.User;
import eu.sqooss.service.logging.Logger;
import eu.sqooss.service.security.SecurityManager;
import eu.sqooss.service.security.UserManager;
import gr.tracer.common.entities.db.MonitoredProjectList;
import gr.tracer.common.entities.db.MonitoredProjectListProject;
import gr.tracer.common.entities.db.SecurityLibrary;
import gr.tracer.common.entities.db.SecurityProfile;
import gr.tracer.common.entities.db.VulnerabilityType;
import gr.tracer.platform.TracerPlatform;

public class SecurityProfileComponentImpl implements SecurityProfileComponent {

	private TracerPlatform platform;
	private Logger logger;
	private DBService dbs;
	private Map<String, Object> mapProps;
    private Object lockObject = new Object();
	
	public SecurityProfileComponentImpl() {
		mapProps = new Hashtable<String, Object>(1);		
	}
	
	@Override
	public void initComponent(TracerPlatform platform, Logger logger) {
		// TODO Auto-generated method stub
		this.platform = platform;
		this.logger = logger;
	}

	@Override
	public boolean startUp() {
		// TODO Auto-generated method stub
		this.dbs = platform.getDB();
		return true;
	}

	@Override
	public boolean shutDown() {
		// TODO Auto-generated method stub
		return true;
	}

	
	
	
	/*
	 * The methods for handling Security Profile's operations
	 */
	@Override
	public List<SecurityProfile> getSecurityProfiles() {
        
        StringBuilder q = new StringBuilder("from SecurityProfile sp");
        
        return (List<SecurityProfile>) dbs.doHQL(q.toString());
	}

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

	@Override
	public SecurityProfile searchSecurityProfile(String spName) {
		List<SecurityProfile> secProfs;
		try {
			if (dbs.startDBSession()) {
				synchronized (lockObject) {
					mapProps.clear();
					mapProps.put("name", spName);
					secProfs = dbs.findObjectsByProperties(SecurityProfile.class, mapProps);
					if (secProfs.size() != 0)
						return secProfs.get(0);
					else {
						logger.info("SecurityProfile with this name does not exist");
						return null;
					}
				}
			} else {
				return null;
			}
		} finally {
			if (dbs.isDBSessionActive())
				dbs.commitDBSession();
		}
	}

	@Override
	public SecurityProfile getSecurityProfile(int spId) {
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
	
	@Override
	public boolean addVulnerabilityTypeToSecurityProfile(String vtName, String spName) {
        try {
        	VulnerabilityType vt = searchVulnerabilityType(vtName);
            SecurityProfile sp = searchSecurityProfile(spName);
            if ((dbs.startDBSession()) && (vt!=null) && (sp != null)) {
                sp.getDetectedVulnerabilityTypes().add(vt);
                return true;
            } else
            	return false;
        } finally {
        	if (dbs.isDBSessionActive())
				dbs.commitDBSession();
        }
	}
	

	@Override
	public boolean removeVulnerabilityTypeFromSecurityProfile(String vtName, String spName) {
		try {
        	VulnerabilityType vt = searchVulnerabilityType(vtName);
            SecurityProfile sp = searchSecurityProfile(spName);
            if ((dbs.startDBSession()) && (vt!=null) && (sp != null)) {
                sp.getDetectedVulnerabilityTypes().remove(vt);
                return true;
            } else
            	return false;
        } finally {
        	if (dbs.isDBSessionActive())
				dbs.commitDBSession();
        }
	}
	
	
	
	
	/*
	 * The methods for handling Monitored Project List's operations
	 */
	@Override
	public List<MonitoredProjectList> getMonitoredProjectList() {
		// TODO Auto-generated method stub
		StringBuilder q = new StringBuilder("from SecurityLibrary sl");
		return (List<MonitoredProjectList>) dbs.doHQL(q.toString());
	}

	@Override
	public MonitoredProjectList getMonitoredProjectList(int mplId) {
		// TODO Auto-generated method stub
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

	@Override
	public MonitoredProjectList createMonitoredProjectList(String mplName,
			String mplDescription, String userName) {
		// TODO Auto-generated method stub
		SecurityManager sm = AlitheiaCore.getInstance().getSecurityManager();
		UserManager userManager = sm.getUserManager();		
		User user = userManager.getUser(userName);
		MonitoredProjectList mpl = new MonitoredProjectList();
		mpl.setName(mplName);
		mpl.setDescription(mplDescription);
		
		if (user != null)
			mpl.setUser(user);
		else{
			logger.info("User does not with this name");
			return null;
		}
		
		if(dbs != null && dbs.startDBSession())
    	{
    		if(dbs.addRecord(mpl)) 
    			return dbs.commitDBSession() ? mpl : null;
    	}
    	return null;
	}

	@Override
	public MonitoredProjectList searchMonitoredProjectList(String mplName) {
		// TODO Auto-generated method stub
		List<MonitoredProjectList> mpls = null;
		try {
			if (dbs.startDBSession()) {
				synchronized (lockObject) {
					mapProps.clear();
					mapProps.put("name", mplName);
					mpls = dbs.findObjectsByProperties(MonitoredProjectList.class, mapProps);
					return (mpls.size() != 0) ? mpls.get(0) : null;
				}
			} else {
				return null;
			}
		} finally {
			if (dbs.isDBSessionActive())
				dbs.commitDBSession();
		}
	}

	@Override
	public boolean setSecurityProfileToList(String spName, String mplName) {
		// TODO Auto-generated method stub
		try {
			SecurityProfileComponent spm = new SecurityProfileComponentImpl();
			SecurityProfile sp = spm.searchSecurityProfile(spName);
			MonitoredProjectList mpl = searchMonitoredProjectList(mplName);
			if (dbs.startDBSession() && (sp != null) && (mpl != null)) {
				mpl.setSecurityProfile(sp);
				return true;
			} else {
				return false;
			}
		} finally {
			if (dbs.isDBSessionActive())
				dbs.commitDBSession();
		}
	}
	
	@Override
	public boolean addProjectFromMonitoredProjectList(String monProjList,
			String projName) {		
		// TODO Auto-generated method stub
		try {
			MonitoredProjectListProject mplp = null;
			MonitoredProjectList mpl = searchMonitoredProjectList(monProjList);
			StoredProject project = StoredProject.getProjectByName(projName);
			if ((mpl != null) && (project != null))
				mplp =  createMonitoredProjectListProject(mpl, project);
			else{
				logger.info("MonitoredProjectList or StoredProject do not exist.");
				return false;
			}
			
			if (mplp != null) {
				if (dbs.startDBSession()) {
					mpl.getIncludedStoredProject().add(mplp);
					return true;
				} else
					return false;
			} else{
				logger.info("MonitoredProjectListProject could not be created.");
				return false;
			}
		} finally {
			if(dbs.isDBSessionActive())
    			dbs.commitDBSession();
		}
	}

	@Override
	public boolean removeProjectFromMonitoredProjectList(String monProjList,
			String projName) {
		// TODO Auto-generated method stub
		
		List<MonitoredProjectListProject> mplps = null;
		try {
			MonitoredProjectList mpl = searchMonitoredProjectList(monProjList);
			StoredProject project = StoredProject.getProjectByName(projName);
			if ((mpl != null) && (project != null)){
				if (dbs.startDBSession()) {
					synchronized (lockObject) {
						mapProps.clear();
						mapProps.put("monitoredProjectList", mpl.getId());
						mapProps.put("project", project.getId());
						mplps = dbs.findObjectsByProperties(MonitoredProjectListProject.class, mapProps);
						return ((mplps.size() > 0) && (mplps != null)) ? dbs.deleteRecords(mplps) : false;
					}
				} else {
					return false;
				}
			} else {
				logger.info("MonitoredProjectList or StoredProject do not exist.");
				return false;
			}
		} finally {
			if (dbs.isDBSessionActive())
				dbs.commitDBSession();
		}
	}
	
	
	
		
	/*
	 * The methods for handling Monitored Project List Project's operations
	 */
	public MonitoredProjectListProject getMonitoredProjectListProject(int mplpId) {
		// TODO Auto-generated method stub
		try {
	    	if(dbs.startDBSession()) {
	    		return dbs.findObjectById(MonitoredProjectListProject.class, mplpId);
	    	}
	    	else
	    		return null;
    	}
    	finally {
    		if(dbs.isDBSessionActive())
    			dbs.commitDBSession();
    	}
	}

	public MonitoredProjectListProject createMonitoredProjectListProject(MonitoredProjectList mpl,
			StoredProject project) {
		// TODO Auto-generated method stub
		
		try {
			MonitoredProjectListProject mplp = new MonitoredProjectListProject();
			mplp.setMonitoredProjectList(mpl);
			mplp.setProject(project);
			
			if(dbs != null && dbs.startDBSession())
	    	{
	    		if(dbs.addRecord(mpl)) 
	    			return dbs.commitDBSession() ? mplp : null;
	    		else
	    			return null;
	    	} else
	    		return null;
    	}
    	finally {
    		if(dbs.isDBSessionActive())
    			dbs.commitDBSession();
    	}	
	}

	
		
	
	/*
	 * The methods for handling Security Libraries operations
	 */
	@Override
	public List<SecurityLibrary> getSecurityLibrary() {
		// TODO Auto-generated method stub
		StringBuilder q = new StringBuilder("from SecurityLibrary sl");
		return (List<SecurityLibrary>) dbs.doHQL(q.toString());
	}

	@Override
	public SecurityLibrary getSecurityLibrary(int slId) {
		// TODO Auto-generated method stub
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

	@Override
	public SecurityLibrary createSecurityLibrary(String slName, String slDescription) {
		// TODO Auto-generated method stub
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

	@Override
	public SecurityLibrary searchSecurityLibrary(String slName) {
		// TODO Auto-generated method stub
		List<SecurityLibrary> secLibs;
		try {
			if (dbs.startDBSession()) {
				synchronized (lockObject) {
					mapProps.clear();
					mapProps.put("name", slName);
					secLibs = dbs.findObjectsByProperties(SecurityLibrary.class, mapProps);
					if (secLibs.size() != 0) 
						return secLibs.get(0);
					else {
						logger.info("SecurityLibrary with this name does not exist");
						return null;
					}
				}
			} else {
				return null;
			}
		} finally {
			if (dbs.isDBSessionActive())
				dbs.commitDBSession();
		}
	}
	
	
	
	
	/*
	 * The methods for handling Vulnerability Type's operations
	 */
	@Override
	public List<VulnerabilityType> getVulnerabilityTypeList() {
		
		StringBuilder q = new StringBuilder("from VulnerabilityType vt");
		return (List<VulnerabilityType>) dbs.doHQL(q.toString());
	}

	@Override
	public VulnerabilityType getVulnerabilityType(int vtId) {
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

	@Override
	public VulnerabilityType searchVulnerabilityType(String vtName) {
		List<VulnerabilityType> vulTypes;
		try {
			if (dbs.startDBSession()) {
				synchronized (lockObject) {
					mapProps.clear();
					mapProps.put("name", vtName);
					vulTypes = dbs.findObjectsByProperties(VulnerabilityType.class, mapProps);
					if (vulTypes.size() != 0)
						return vulTypes.get(0);
					else {
						logger.info("VulnerabilityType with this does not exist");
						return null;
					}
				}
			} else {
				return null;
			}
		} finally {
			if (dbs.isDBSessionActive())
				dbs.commitDBSession();
		}
	}
	
	@Override
	public boolean addSecurityLibraryToVulnerabilityType(String slName, String vtName) {
        try {
        	if (dbs.startDBSession()) {
        		SecurityLibrary sl = searchSecurityLibrary(slName);
        		VulnerabilityType vt = searchVulnerabilityType(vtName);
        		if ((sl!=null) && (vt != null)) {
        			return ((sl.getTreatedVulnerabilityTypes().add(vt)) &&
        					(vt.getTreatingSecurityLibraries().add(sl)));
        		} else {
        			logger.error("SecurityLibrary or VulnerabilityType do not exist");
        			return false;
        		}
        	} else {
    			return false;
        	}
        } finally {
        	if (dbs.isDBSessionActive())
				dbs.commitDBSession();
        }
    }
}
