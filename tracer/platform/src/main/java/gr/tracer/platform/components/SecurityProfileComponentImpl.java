package gr.tracer.platform.components;

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
	private Map<String, Object> secProfProps;
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
		secProfProps = new Hashtable<String, Object>(1);
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

	@SuppressWarnings("finally")
	@Override
	public boolean addVulnerabilityToSecurityProfile(long vtId, long spId) {
		dbs.startDBSession();
        try {
        	VulnerabilityType vt = dbs.findObjectById(VulnerabilityType.class, vtId);
            SecurityProfile sp = dbs.findObjectById(SecurityProfile.class, spId);
            if ((vt!=null) && (sp != null)) {
                sp.getDetectedVulnerabilityTypes().add(vt);
            }
        } finally {
            return dbs.commitDBSession();
        }
	}

	@Override
	public SecurityProfile createSecurityProfile(String aAName,
			String aAType) {
		SecurityProfile sp = new SecurityProfile();
		sp.setName(aAName);
		sp.setDescription(aAType);
		if(dbs != null && dbs.startDBSession())
    	{
    		if(dbs.addRecord(sp)) 
    			return dbs.commitDBSession() ? sp : null;
    	}
    	return null;
	}

	@Override
	public SecurityProfile searchSecurityProfile(String aAName) {
		List<SecurityProfile> secProfs;
		try {
			if (dbs.startDBSession()) {
				synchronized (lockObject) {
					secProfProps.clear();
					secProfProps.put("name", aAName);
					secProfs = dbs.findObjectsByProperties(SecurityProfile.class, secProfProps);
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
		try {
			if (dbs.startDBSession()) {
				VulnerabilityType vt = dbs.findObjectById(VulnerabilityType.class, vtId);
				SecurityProfile sp = dbs.findObjectById(SecurityProfile.class, spId);
				if ((vt!=null) && (sp != null)) {
					sp.getDetectedVulnerabilityTypes().remove(vt);
					return true;
				} else
					return false;
			} else
				return false;
        } finally {
            dbs.commitDBSession();
        }
	}
}
