package gr.tracer.platform.components;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import eu.sqooss.service.db.DBService;
import eu.sqooss.service.logging.Logger;
import gr.tracer.common.entities.db.SecurityProfile;
import gr.tracer.common.entities.db.VulnerabilityType;
import gr.tracer.platform.TracerPlatform;

public class SecurityProfileComponentImpl implements SecurityProfileComponent {

	private TracerPlatform platform;
	private Logger logger;
	private DBService dbs;
	private Map<String, Object> userProps;
    private Object lockObject = new Object();
	
	public SecurityProfileComponentImpl() {
		
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
		userProps = new Hashtable<String, Object>(1);
		return true;
	}

	@Override
	public boolean shutDown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<SecurityProfile> getSecurityProfiles() {
        
        StringBuilder q = new StringBuilder("from SecurityProfile sp");
        
        return (List<SecurityProfile>) dbs.doHQL(q.toString());
	}

	@Override
	public boolean addVulnerabilityToSecurityProfile(long vtId, long spId) {
		dbs.startDBSession();
        try {
        	VulnerabilityType vt = dbs.findObjectById(VulnerabilityType.class, vtId);
            SecurityProfile sp = dbs.findObjectById(SecurityProfile.class, spId);
            if ((vt!=null) && (sp != null)) {
                return sp.getDetectedVulnerabilityTypes().add(vt);
            } else {
                return false;
            }
        } finally {
            dbs.commitDBSession();
        }
	}

	@Override
	public boolean createSecurityProfile(String aAName,
			String aAType) {
		SecurityProfile sp = new SecurityProfile();
		sp.setName(aAName);
		sp.setDescription(aAType);
		if(dbs != null && dbs.startDBSession())
    	{
    		if(dbs.addRecord(sp)) 
    			return dbs.commitDBSession();
    	}
    	return false;
	}

	@Override
	public List<SecurityProfile> searchSecurityProfile(String aAName) {
		try {
			if (dbs.startDBSession()) {
				synchronized (lockObject) {
					userProps.clear();
					userProps.put("security_profile_name", aAName);
					return dbs.findObjectsByProperties(SecurityProfile.class, userProps);
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

	@Override
	public boolean addSecurityProfileToList(SecurityProfile aASp) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SecurityProfile getSecurityProfile(int aAProfile_index) {
		try {
	    	if(dbs.startDBSession()) {
	    		return dbs.findObjectById(SecurityProfile.class, aAProfile_index);
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
	public boolean removeVulnerabilityFromSecurityProfile(long vtId, long spId) {
		dbs.startDBSession();
        try {
        	VulnerabilityType vt = dbs.findObjectById(VulnerabilityType.class, vtId);
            SecurityProfile sp = dbs.findObjectById(SecurityProfile.class, spId);
            if ((vt!=null) && (sp != null)) {
                return sp.getDetectedVulnerabilityTypes().remove(vt);
            } else {
                return false;
            }
        } finally {
            dbs.commitDBSession();
        }
	}
}
