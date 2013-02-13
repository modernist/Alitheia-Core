package gr.tracer.platform.components;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import eu.sqooss.service.db.DBService;
import eu.sqooss.service.logging.Logger;
import gr.tracer.common.entities.db.SecurityLibrary;
import gr.tracer.common.entities.db.VulnerabilityType;
import gr.tracer.platform.TracerPlatform;

public class SecurityLibraryComponentImpl implements SecurityLibraryComponent {

	private TracerPlatform platform;
	private Logger logger;
	private DBService dbs;
	private Map<String, Object> userProps;
    private Object lockObject = new Object();
	
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
		userProps = new Hashtable<String, Object>(1);
		return true;
	}

	@Override
	public boolean shutDown() {
		// TODO Auto-generated method stub
		return false;
	}

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
	public boolean createSecurityLibrary(String slName, String slDescription) {
		// TODO Auto-generated method stub
		SecurityLibrary sl = new SecurityLibrary();
		sl.setName(slName);
		sl.setDescription(slDescription);
		if(dbs != null && dbs.startDBSession())
    	{
    		if(dbs.addRecord(sl)) 
    			return dbs.commitDBSession();
    	}
    	return false;
	}

	@Override
	public List<SecurityLibrary> searchSecurityLibrary(String slName) {
		// TODO Auto-generated method stub
		try {
			if (dbs.startDBSession()) {
				synchronized (lockObject) {
					userProps.clear();
					userProps.put("security_library_name", slName);
					return dbs.findObjectsByProperties(SecurityLibrary.class, userProps);
				}
			} else {
				logger.info("Failed to start DBSession");
			}
		} finally {
			if (dbs.isDBSessionActive())
				dbs.commitDBSession();
		}
		return Collections.emptyList();
	}
}
