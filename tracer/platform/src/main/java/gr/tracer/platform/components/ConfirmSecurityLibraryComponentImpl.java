package gr.tracer.platform.components;

import eu.sqooss.core.AlitheiaCoreService;
import eu.sqooss.service.db.DBService;
import eu.sqooss.service.db.ProjectFile;
import eu.sqooss.service.logging.Logger;
import gr.tracer.common.entities.db.ProjectFileVulnerability;
import gr.tracer.common.entities.db.VulnerabilityType;
import gr.tracer.platform.TracerPlatform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConfirmSecurityLibraryComponentImpl implements
		ConfirmSecurityLibraryComponent {
	
	private TracerPlatform platform;
	private Logger logger;
	private DBService dbs;
    private VulnerabilityTypeComponent vtc;

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
				VulnerabilityType vt = vtc.searchVulnerabilityType(pvf.getVulnerabilityType().getName());
				if (vt == null) {
					vt = vtc.createVulnerabilityType(pvf.getVulnerabilityType().getName(), pvf.getVulnerabilityType().getName());
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
		this.vtc = new VulnerabilityTypeComponentImpl();
	}

	@Override
	public boolean startUp() {
		// TODO Auto-generated method stub
		if (platform != null)
			platform.registerComponent(ConfirmSecurityLibraryComponent.class, ConfirmSecurityLibraryComponentImpl.class);
		this.dbs = platform.getDB();
		ProjectFile pf = new ProjectFile();
		VulnerabilityType vt = new VulnerabilityType();
		ProjectFileVulnerability pvf = new ProjectFileVulnerability();
		List<ProjectFileVulnerability> sProjVul = new ArrayList<ProjectFileVulnerability>();
		pf.setName("Project1");
		dbs.startDBSession();
		dbs.addRecord(pf);
		dbs.commitDBSession();
		vt.setName("Sql name");
		vt.setDescription("Sql description");
		pvf.setDescription("Sql attack desc");
		pvf.setLocation("Sql attack loc");
		pvf.setProjectFile(pf);
		pvf.setVulnerabilityType(vt);
		sProjVul.add(pvf);
		addToDetectedVulnerabilities(sProjVul);
		return true;
	}

	@Override
	public boolean shutDown() {
		// TODO Auto-generated method stub
		return true;
	}

}
