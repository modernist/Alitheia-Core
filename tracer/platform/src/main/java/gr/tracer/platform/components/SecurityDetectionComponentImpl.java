package gr.tracer.platform.components;

import eu.sqooss.service.db.DBService;
import eu.sqooss.service.db.ProjectFile;
import eu.sqooss.service.logging.Logger;
import gr.tracer.common.entities.db.MonitoredProjectList;
import gr.tracer.common.entities.db.ProjectFileVulnerability;
import gr.tracer.common.entities.db.SecurityLibrary;
import gr.tracer.common.entities.db.SecurityProfile;
import gr.tracer.common.entities.db.VulnerabilityType;
import gr.tracer.platform.TracerPlatform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SecurityDetectionComponentImpl implements
		SecurityDetectionComponent {
	
	private TracerPlatform platform;
	private Logger logger;
	private DBService dbs;

	/**
     * @see gr.tracer.platform.components.SecurityDetectionComponent#addToDetectedVulnerabilities(java.lang.List)
     */
	@Override
	public boolean addToDetectedVulnerabilities(List<ProjectFileVulnerability> sProjVul) {
		try {
			Iterator<ProjectFileVulnerability> it = sProjVul.iterator();
			ProjectFileVulnerability pvf;
			
			if (dbs.startDBSession()) {
				while(it.hasNext()){
					pvf = it.next();
					VulnerabilityType vt = ((SecurityProfileComponentImpl) platform.getComponent(SecurityProfileComponent.class)).searchVulnerabilityType(pvf.getVulnerabilityType().getName());
					if (vt == null) {
						vt = ((SecurityProfileComponentImpl) platform.getComponent(SecurityProfileComponent.class)).createVulnerabilityType(pvf.getVulnerabilityType().getName(), pvf.getVulnerabilityType().getName());
						pvf.setVulnerabilityType(vt);
					} else
						pvf.setVulnerabilityType(vt);
					dbs.addRecord(pvf);
				}
			return true;
			} else
				return false;
		} finally {
			if (dbs.isDBSessionActive())
				dbs.commitDBSession();
		}
	}

	@Override
	public void initComponent(TracerPlatform platform, Logger logger) {
		this.platform = platform;
		this.logger = logger;
	}

	@Override
	public boolean startUp() {
		this.dbs = platform.getDB();
		
//		if (((SecurityProfileComponentImpl) platform.getComponent(SecurityProfileComponent.class)).createSecurityLibrary("XSS library name", "XSS library description") != null) {
//			if (((SecurityProfileComponentImpl) platform.getComponent(SecurityProfileComponent.class)).searchSecurityLibrary("XSS library name") != null)
//				System.out.println("Security library created");
//		}
//		else
//			System.out.println("Security library not created");
//		
//		if (((SecurityProfileComponentImpl) platform.getComponent(SecurityProfileComponent.class)).createVulnerabilityType("XSS name", "XSS description") != null) {
//			if (((SecurityProfileComponentImpl) platform.getComponent(SecurityProfileComponent.class)).searchVulnerabilityType("XSS name") != null)
//				System.out.println("Vulnerability type created");
//		}
//		else
//			System.out.println("Vulnerability type not created");
		
		if (((SecurityProfileComponentImpl) platform.getComponent(SecurityProfileComponent.class)).addSecurityLibraryToVulnerabilityType("Sql library name", "Sql name"))
			System.out.println("Vulnerability type associated with Security library");
		else
			System.out.println("Vulnerability type not associated with Security library");
		
		if (((SecurityProfileComponentImpl) platform.getComponent(SecurityProfileComponent.class)).createMonitoredProjectList("Monitored project list 1 name", "Monitored project list 1 description", "fotis") != null) {
			if (((SecurityProfileComponentImpl) platform.getComponent(SecurityProfileComponent.class)).searchMonitoredProjectList("Monitored project list 1 name") != null)
				System.out.println("Monitored project list created");
		}
		else
			System.out.println("Monitored project list not created");
		
		if (((SecurityProfileComponentImpl) platform.getComponent(SecurityProfileComponent.class)).createSecurityProfile("Security profile 1 name", "Security profile 1 description") != null) {
			if (((SecurityProfileComponentImpl) platform.getComponent(SecurityProfileComponent.class)).searchSecurityProfile("Security profile 1 name") != null)
				System.out.println("Security profile not created");
		}
		else
			System.out.println("Security profile not created");
		
		if (((SecurityProfileComponentImpl) platform.getComponent(SecurityProfileComponent.class)).setSecurityProfileToList("Security profile 1 name", "Monitored project list 1 name"))
			System.out.println("Security profile associated with Monitored project list");
		else
			System.out.println("Security profile not associated with Monitored project list");
		
		if (((SecurityProfileComponentImpl) platform.getComponent(SecurityProfileComponent.class)).addVulnerabilityTypeToSecurityProfile("Sql name", "Security profile 1 name"))
			System.out.println("Vulnerability type associated with Security profile");
		else
			System.out.println("Vulnerability type not associated with Security profile");
		
		return true;
	}

	@Override
	public boolean shutDown() {
		return true;
	}

}
