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
import gr.tracer.common.entities.db.SecurityProfile;
import gr.tracer.platform.TracerPlatform;

public class MonitoredProjectListComponentImpl implements
		MonitoredProjectListComponent {

	private TracerPlatform platform;
	private Logger logger;
	private DBService dbs;
	private Map<String, Object> monProjLProps;
	private Map<String, Object> monProjListProjProps;
    private Object lockObject = new Object();
    SecurityManager sm;
	
	@Override
	public void initComponent(TracerPlatform platform, Logger logger) {
		// TODO Auto-generated method stub
		this.platform = platform;
		this.logger = logger;
	}

	@Override
	public boolean startUp() {
		// TODO Auto-generated method stub
		sm = AlitheiaCore.getInstance().getSecurityManager();
		this.dbs = platform.getDB();
		monProjLProps = new Hashtable<String, Object>(1);
		monProjListProjProps = new Hashtable<String, Object>(1);
		return true;
	}

	@Override
	public boolean shutDown() {
		// TODO Auto-generated method stub
		return true;
	}

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
					monProjLProps.clear();
					monProjLProps.put("name", mplName);
					mpls = dbs.findObjectsByProperties(MonitoredProjectList.class, monProjLProps);
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
						monProjListProjProps.clear();
						monProjListProjProps.put("monitoredProjectList", mpl.getId());
						monProjListProjProps.put("project", project.getId());
						mplps = dbs.findObjectsByProperties(MonitoredProjectListProject.class, monProjListProjProps);
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
}
