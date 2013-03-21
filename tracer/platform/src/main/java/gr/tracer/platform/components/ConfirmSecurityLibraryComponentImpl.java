package gr.tracer.platform.components;

import eu.sqooss.service.db.DBService;
import eu.sqooss.service.logging.Logger;
import gr.tracer.common.entities.db.ProjectFileVulnerability;
import gr.tracer.common.entities.db.VulnerabilityType;
import gr.tracer.platform.TracerPlatform;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ConfirmSecurityLibraryComponentImpl implements
		ConfirmSecurityLibraryComponent {
	
	private TracerPlatform platform;
	private Logger logger;
	private DBService dbs;
	private Map<String, Object> vulProps;
    private Object lockObject = new Object();
    VulnerabilityComponent vc;

	@Override
	public boolean setLibraryApplication(boolean aATreatVulnerability) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addToDetectedVulnerabilities(
			List<ProjectFileVulnerability> sProjVul) {
		
		Iterator<ProjectFileVulnerability> it = sProjVul.iterator();
		ProjectFileVulnerability pvf;
		if (dbs.startDBSession()) {
			while(it.hasNext()){
				pvf = it.next();
				VulnerabilityType vt = vc.searchVulnerability(pvf.getVulnerabilityType().getName());
				if (vt == null) {
					vt = vc.createVulnerability(pvf.getVulnerabilityType().getName(), pvf.getVulnerabilityType().getName());
					pvf.setVulnerabilityType(vt);
					} else
						pvf.setVulnerabilityType(vt);
				dbs.addRecord(pvf);
			}
			return dbs.commitDBSession();
		}
		return false;
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
		vulProps = new Hashtable<String, Object>(1);
		return true;
	}

	@Override
	public boolean shutDown() {
		// TODO Auto-generated method stub
		return false;
	}

}
