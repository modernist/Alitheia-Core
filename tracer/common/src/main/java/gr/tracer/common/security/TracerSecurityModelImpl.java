package gr.tracer.common.security;

import org.osgi.framework.BundleContext;

import eu.sqooss.core.AlitheiaCore;
import eu.sqooss.core.AlitheiaCoreService;
import eu.sqooss.impl.service.security.SecurityManagerImpl;
import eu.sqooss.service.db.DBService;
import eu.sqooss.service.db.Group;
import eu.sqooss.service.db.Privilege;
import eu.sqooss.service.db.PrivilegeValue;
import eu.sqooss.service.db.ServiceUrl;
import eu.sqooss.service.db.User;
import eu.sqooss.service.logging.Logger;
import eu.sqooss.service.security.GroupManager;
import eu.sqooss.service.security.PrivilegeManager;
import eu.sqooss.service.security.SecurityManager;
import eu.sqooss.service.security.ServiceUrlManager;
import eu.sqooss.service.security.UserManager;

public class TracerSecurityModelImpl implements TracerSecurityModel, AlitheiaCoreService {
	//TODO: Implement as an AlitheiaCoreService
	
	private AlitheiaCore core;
	private DBService db;
    private Logger logger;
	private BundleContext bc;
    private static SecurityManager sm;
	
	
	public void initSecurityModel() {
		User user = null;
        Group group = null;
        ServiceUrl serviceUrl = null;
        Privilege privilege = null;
        PrivilegeValue privilegeValue = null;
		
		GroupManager groupManager = sm.getGroupManager();
		UserManager userManager = sm.getUserManager();
		ServiceUrlManager serviceUrlManager = sm.getServiceUrlManager();
		PrivilegeManager privilegeManager = sm.getPrivilegeManager();
		
		privilege = privilegeManager.getPrivilege(TracerSecurityConstants.Privilege.ACTION.toString());
		if (privilege == null) {
			privilege = privilegeManager.createPrivilege(TracerSecurityConstants.Privilege.ACTION.toString());			
		}
		
		if (privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.CREATE_USER.toString()) == null)
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.CREATE_USER.toString());
		
		if (serviceUrlManager.getServiceUrl((TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.CREATE_USER.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE)) == null)
		serviceUrl = serviceUrlManager.createServiceUrl(TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.CREATE_USER.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		
		if (privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.CREATE_OBSERVED_PROJECT_LIST.toString()) == null) {
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.CREATE_OBSERVED_PROJECT_LIST.toString());
		}
		
		if (serviceUrlManager.getServiceUrl((TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.CREATE_OBSERVED_PROJECT_LIST.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE)) == null)
		serviceUrl = serviceUrlManager.createServiceUrl(TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.CREATE_OBSERVED_PROJECT_LIST.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		
		if (privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.ADD_PROJECT_TO_OBSERVED_PROJECT_LIST.toString()) == null) {
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.ADD_PROJECT_TO_OBSERVED_PROJECT_LIST.toString());
		}
		
		if (serviceUrlManager.getServiceUrl((TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.ADD_PROJECT_TO_OBSERVED_PROJECT_LIST.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE)) == null)
		serviceUrl = serviceUrlManager.createServiceUrl(TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.ADD_PROJECT_TO_OBSERVED_PROJECT_LIST.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		
		if (privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.REMOVE_PROJECT_FROM_OBSERVED_PROJECT_LIST.toString()) == null) {
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.REMOVE_PROJECT_FROM_OBSERVED_PROJECT_LIST.toString());
		}
		
		if (serviceUrlManager.getServiceUrl((TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.REMOVE_PROJECT_FROM_OBSERVED_PROJECT_LIST.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE)) == null)
		serviceUrl = serviceUrlManager.createServiceUrl(TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.REMOVE_PROJECT_FROM_OBSERVED_PROJECT_LIST.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		
		if (privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.ADD_BUG_TO_SECURITY_PROFILE.toString()) == null) {
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.ADD_BUG_TO_SECURITY_PROFILE.toString());
		}
		
		if (serviceUrlManager.getServiceUrl((TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.ADD_BUG_TO_SECURITY_PROFILE.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE)) == null)
		serviceUrl = serviceUrlManager.createServiceUrl(TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.ADD_BUG_TO_SECURITY_PROFILE.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		
		if (privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.REMOVE_BUG_FROM_SECURITY_PROFILE.toString()) == null) {
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.REMOVE_BUG_FROM_SECURITY_PROFILE.toString());
		}
		
		if (serviceUrlManager.getServiceUrl((TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.REMOVE_BUG_FROM_SECURITY_PROFILE.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE)) == null)
		serviceUrl = serviceUrlManager.createServiceUrl(TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.REMOVE_BUG_FROM_SECURITY_PROFILE.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		
		if (privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.DETECT_CODE_FOR_BUGS.toString()) == null) {
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.DETECT_CODE_FOR_BUGS.toString());
		}
		
		if (serviceUrlManager.getServiceUrl((TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.DETECT_CODE_FOR_BUGS.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE)) == null)
		serviceUrl = serviceUrlManager.createServiceUrl(TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.DETECT_CODE_FOR_BUGS.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		
		if (privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.RECORD_DETECTED_BUGS.toString()) == null) {
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.RECORD_DETECTED_BUGS.toString());
		}
		
		if (serviceUrlManager.getServiceUrl((TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.RECORD_DETECTED_BUGS.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE)) == null)
		serviceUrl = serviceUrlManager.createServiceUrl(TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.RECORD_DETECTED_BUGS.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		
		if (privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TREAT_DETECTED_BUGS.toString()) == null) {
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TREAT_DETECTED_BUGS.toString());
		}
		
		if (serviceUrlManager.getServiceUrl((TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.TREAT_DETECTED_BUGS.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE)) == null)
		serviceUrl = serviceUrlManager.createServiceUrl(TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.TREAT_DETECTED_BUGS.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		
		if (privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.CREATE_SECURITY_PROFILE.toString()) == null) {
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.CREATE_SECURITY_PROFILE.toString());
		}
		
		if (serviceUrlManager.getServiceUrl((TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.CREATE_SECURITY_PROFILE.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE)) == null)
		serviceUrl = serviceUrlManager.createServiceUrl(TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.CREATE_SECURITY_PROFILE.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		
		if (privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.CREATE_BUG_DETECTOR.toString()) == null) {
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.CREATE_BUG_DETECTOR.toString());
		}
		
		if (serviceUrlManager.getServiceUrl((TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.CREATE_BUG_DETECTOR.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE)) == null)
		serviceUrl = serviceUrlManager.createServiceUrl(TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.CREATE_BUG_DETECTOR.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		
		
		/*Creation of Administrator group*/	
		user = userManager.getUser("admin");
		if (user == null)
			user = userManager.createUser("admin", "admin", "admin@tracer.gr");
		
		group = groupManager.getGroup(TracerSecurityConstants.GroupName.ADMINSTRATOR.toString());
		if (group == null)
			group = groupManager.createGroup(TracerSecurityConstants.GroupName.ADMINSTRATOR.toString());
		
		if (group != null) {
			System.out.println(groupManager.addUserToGroup(group.getId(), user.getId()));
		}
		
		privilegeValue = privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.CREATE_USER.toString());
		serviceUrl = serviceUrlManager.getServiceUrl(TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.CREATE_USER.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		if ((privilegeValue != null) && (group != null))
			groupManager.addPrivilegeToGroup(group.getId(), serviceUrl.getId(), privilegeValue.getId());
		
		privilegeValue = privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.CREATE_BUG_DETECTOR.toString());
		serviceUrl = serviceUrlManager.getServiceUrl(TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.CREATE_BUG_DETECTOR.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		if ((privilegeValue != null) && (group != null))
			groupManager.addPrivilegeToGroup(group.getId(), serviceUrl.getId(), privilegeValue.getId());

		
		/*Creation of Programmer group*/
		user = userManager.getUser("programmer");
		if (user == null)
			user = userManager.createUser("programmer", "programmer", "programmer@tracer.gr");
		
		group = groupManager.getGroup(TracerSecurityConstants.GroupName.PROGRAMMER.toString());
		if (group == null)
			group = groupManager.createGroup(TracerSecurityConstants.GroupName.PROGRAMMER.toString());
			
		if (group != null) {
			groupManager.addUserToGroup(group.getId(), user.getId());
		}
		
		privilegeValue = privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.CREATE_SECURITY_PROFILE.toString());
		serviceUrl = serviceUrlManager.getServiceUrl(TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.CREATE_SECURITY_PROFILE.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		if ((privilegeValue != null) && (group != null)) 
			groupManager.addPrivilegeToGroup(group.getId(), serviceUrl.getId(), privilegeValue.getId());
		
		privilegeValue = privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.CREATE_OBSERVED_PROJECT_LIST.toString());
		serviceUrl = serviceUrlManager.getServiceUrl(TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.CREATE_OBSERVED_PROJECT_LIST.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		if ((privilegeValue != null) && (group != null))
			groupManager.addPrivilegeToGroup(group.getId(), serviceUrl.getId(), privilegeValue.getId());
		
		privilegeValue = privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.ADD_PROJECT_TO_OBSERVED_PROJECT_LIST.toString());
		serviceUrl = serviceUrlManager.getServiceUrl(TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.ADD_PROJECT_TO_OBSERVED_PROJECT_LIST.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		if ((privilegeValue != null) && (group != null)) 
			groupManager.addPrivilegeToGroup(group.getId(), serviceUrl.getId(), privilegeValue.getId());
		
		privilegeValue = privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.REMOVE_PROJECT_FROM_OBSERVED_PROJECT_LIST.toString());
		serviceUrl = serviceUrlManager.getServiceUrl(TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.REMOVE_PROJECT_FROM_OBSERVED_PROJECT_LIST.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		if ((privilegeValue != null) && (group != null))
			groupManager.addPrivilegeToGroup(group.getId(), serviceUrl.getId(), privilegeValue.getId());
		
		privilegeValue = privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.ADD_BUG_TO_SECURITY_PROFILE.toString());
		serviceUrl = serviceUrlManager.getServiceUrl(TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.ADD_BUG_TO_SECURITY_PROFILE.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		if ((privilegeValue != null) && (group != null))
			groupManager.addPrivilegeToGroup(group.getId(), serviceUrl.getId(), privilegeValue.getId());
		
		privilegeValue = privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.REMOVE_BUG_FROM_SECURITY_PROFILE.toString());
		serviceUrl = serviceUrlManager.getServiceUrl(TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.REMOVE_BUG_FROM_SECURITY_PROFILE.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		if ((privilegeValue != null) && (group != null))
			groupManager.addPrivilegeToGroup(group.getId(), serviceUrl.getId(), privilegeValue.getId());
		
		
		
		/*Creation of Vulnerability Manager group*/
		user = userManager.getUser("vulnerability_manager");
		if (user == null)
			user = userManager.createUser("vulnerability_manager", "vulnerability_manager", "vulnerability_manager@tracer.gr");
		
		group = groupManager.getGroup(TracerSecurityConstants.GroupName.VULNERABILITY_MANAGER.toString());
		if (group == null)
			group = groupManager.createGroup(TracerSecurityConstants.GroupName.VULNERABILITY_MANAGER.toString());
		
		privilegeValue = privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.DETECT_CODE_FOR_BUGS.toString());
		serviceUrl = serviceUrlManager.getServiceUrl(TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.DETECT_CODE_FOR_BUGS.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		if ((privilegeValue != null) && (group != null))
			groupManager.addPrivilegeToGroup(group.getId(), serviceUrl.getId(), privilegeValue.getId());
		
		privilegeValue = privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.RECORD_DETECTED_BUGS.toString());
		serviceUrl = serviceUrlManager.getServiceUrl(TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.RECORD_DETECTED_BUGS.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		if ((privilegeValue != null) && (group != null))
			groupManager.addPrivilegeToGroup(group.getId(), serviceUrl.getId(), privilegeValue.getId());
		
		privilegeValue = privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TREAT_DETECTED_BUGS.toString());
		serviceUrl = serviceUrlManager.getServiceUrl(TracerSecurityConstants.URL_TRACER_SECURITY + TracerSecurityConstants.URL_DELIMITER_RESOURCE + 
				TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.TREAT_DETECTED_BUGS.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		if ((privilegeValue != null) && (group != null))
			groupManager.addPrivilegeToGroup(group.getId(), serviceUrl.getId(), privilegeValue.getId());
	}

	@Override
	public boolean startUp() {
		// TODO Auto-generated method stub
		logger.info("Starting the Tracer component.");

        // Get the AlitheiaCore's object
        core = AlitheiaCore.getInstance();

        if (core != null) {
            // Obtain the required core components
            db = core.getDBService();
            if (db == null) {
                logger.error("Can not obtain the DB object!");
            }
        }
        else {
            logger.error("Can not obtain the Core object!");
        }
	
        sm = new SecurityManagerImpl(db, logger);
        
        logger.info("The Tracer component was successfully started.");
        return true;
	}

	@Override
	public void shutDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setInitParams(BundleContext bc, Logger l) {
		// TODO Auto-generated method stub
		
		this.bc = bc;
		this.logger = l;
		
	}

	
}
