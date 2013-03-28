package gr.tracer.platform.components;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import eu.sqooss.core.AlitheiaCore;
import eu.sqooss.service.db.DBService;
import eu.sqooss.service.db.User;
import eu.sqooss.service.logging.Logger;
import eu.sqooss.service.security.SecurityManager;
import eu.sqooss.service.security.UserManager;
import gr.tracer.common.entities.db.MonitoredProjectList;
import gr.tracer.common.entities.db.SecurityProfile;
import gr.tracer.platform.TracerPlatform;

public class MonitoredProjectListComponentImpl implements
		MonitoredProjectListComponent {

	private TracerPlatform platform;
	private Logger logger;
	private DBService dbs;
	private Map<String, Object> monProjLProps;
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
		else
			return null;
		
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
		List<MonitoredProjectList> secLibs = null;
		try {
			if (dbs.startDBSession()) {
				synchronized (lockObject) {
					monProjLProps.clear();
					monProjLProps.put("name", mplName);
					secLibs = dbs.findObjectsByProperties(MonitoredProjectList.class, monProjLProps);
				}
			} else {
				logger.info("Failed to start DBSession");
			}
			 if (secLibs.size() != 0) {
				 return secLibs.get(0);
				 }
		} finally {
			if (dbs.isDBSessionActive())
				dbs.commitDBSession();
		}
		return null;
	}

	@Override
	public boolean setSecurityProfileToList(int spId, int mplId) {
		// TODO Auto-generated method stub
		SecurityProfileComponent spm = new SecurityProfileComponentImpl();
		SecurityProfile sp = spm.getSecurityProfile(spId);
		MonitoredProjectList mpl = getMonitoredProjectList(mplId);
		if (dbs.startDBSession() && (sp != null) && (mpl != null)) {
			mpl.setSecurityProfile(sp);
			return dbs.commitDBSession();
		}
		return false;	
	}
}
