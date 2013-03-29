package gr.tracer.platform.components;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import eu.sqooss.service.db.DBService;
import eu.sqooss.service.logging.Logger;
import gr.tracer.common.entities.db.SecurityLibrary;
import gr.tracer.platform.TracerPlatform;

public class SecurityLibraryComponentImpl implements SecurityLibraryComponent {

	private TracerPlatform platform;
	private Logger logger;
	private DBService dbs;
	private Map<String, Object> seLibProps;
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
		seLibProps = new Hashtable<String, Object>(1);
		return true;
	}

	@Override
	public boolean shutDown() {
		// TODO Auto-generated method stub
		return true;
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
					seLibProps.clear();
					seLibProps.put("name", slName);
					secLibs = dbs.findObjectsByProperties(SecurityLibrary.class, seLibProps);
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
}
