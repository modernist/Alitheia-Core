package gr.tracer.common.security;

import eu.sqooss.core.AlitheiaCore;
import eu.sqooss.service.db.Group;
import eu.sqooss.service.db.Privilege;
import eu.sqooss.service.db.PrivilegeValue;
import eu.sqooss.service.db.ServiceUrl;
import eu.sqooss.service.db.User;
import eu.sqooss.service.security.GroupManager;
import eu.sqooss.service.security.PrivilegeManager;
import eu.sqooss.service.security.SecurityConstants;
import eu.sqooss.service.security.SecurityManager;
import eu.sqooss.service.security.ServiceUrlManager;
import eu.sqooss.service.security.UserManager;

public class TracerSecurityModel {
	//TODO: Implement as an AlitheiaCoreService
	
	public static void initSecurityModel() {
		User user = null;
        Group group = null;
        ServiceUrl serviceUrl = null;
        Privilege privilege = null;
        PrivilegeValue privilegeValue = null;
		
		SecurityManager sm = AlitheiaCore.getInstance().getSecurityManager();
		GroupManager groupManager = sm.getGroupManager();
		UserManager userManager = sm.getUserManager();
		ServiceUrlManager serviceUrlManager = sm.getServiceUrlManager();
		PrivilegeManager privilegeManager = sm.getPrivilegeManager();
		
		if (privilegeManager.getPrivilege(TracerSecurityConstants.Privilege.CREATE_USER.toString()) == null) {
			privilege = privilegeManager.createPrivilege(TracerSecurityConstants.Privilege.CREATE_USER.toString());
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TRUE.toString());
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.FALSE.toString());			
		}
		
		if (privilegeManager.getPrivilege(TracerSecurityConstants.Privilege.CREATE_OBSERVED_PROJECT_LIST.toString()) == null) {
			privilege = privilegeManager.createPrivilege(TracerSecurityConstants.Privilege.CREATE_OBSERVED_PROJECT_LIST.toString());
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TRUE.toString());
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.FALSE.toString());
		}
		
		if (privilegeManager.getPrivilege(TracerSecurityConstants.Privilege.ADD_PROJECT_TO_OBSERVED_PROJECT_LIST.toString()) == null) {
			privilege = privilegeManager.createPrivilege(TracerSecurityConstants.Privilege.ADD_PROJECT_TO_OBSERVED_PROJECT_LIST.toString());
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TRUE.toString());
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.FALSE.toString());
		}
		
		if (privilegeManager.getPrivilege(TracerSecurityConstants.Privilege.REMOVE_PROJECT_FROM_OBSERVED_PROJECT_LIST.toString()) == null) {
			privilege = privilegeManager.createPrivilege(TracerSecurityConstants.Privilege.REMOVE_PROJECT_FROM_OBSERVED_PROJECT_LIST.toString());
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TRUE.toString());
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.FALSE.toString());
		}
		
		if (privilegeManager.getPrivilege(TracerSecurityConstants.Privilege.ADD_BUG_TO_SECURITY_PROFILE.toString()) == null) {
			privilege = privilegeManager.createPrivilege(TracerSecurityConstants.Privilege.ADD_BUG_TO_SECURITY_PROFILE.toString());
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TRUE.toString());
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.FALSE.toString());
		}
		
		if (privilegeManager.getPrivilege(TracerSecurityConstants.Privilege.REMOVE_BUG_FROM_SECURITY_PROFILE.toString()) == null) {
			privilege = privilegeManager.createPrivilege(TracerSecurityConstants.Privilege.REMOVE_BUG_FROM_SECURITY_PROFILE.toString());
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TRUE.toString());
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.FALSE.toString());
		}
		
		if (privilegeManager.getPrivilege(TracerSecurityConstants.Privilege.DETECT_CODE_FOR_BUGS.toString()) == null) {
			privilege = privilegeManager.createPrivilege(TracerSecurityConstants.Privilege.DETECT_CODE_FOR_BUGS.toString());
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TRUE.toString());
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.FALSE.toString());
		}
		
		if (privilegeManager.getPrivilege(TracerSecurityConstants.Privilege.RECORD_DETECTED_BUGS.toString()) == null) {
			privilege = privilegeManager.createPrivilege(TracerSecurityConstants.Privilege.RECORD_DETECTED_BUGS.toString());
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TRUE.toString());
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.FALSE.toString());
		}
		
		if (privilegeManager.getPrivilege(TracerSecurityConstants.Privilege.TREAT_DETECTED_BUGS.toString()) == null) {
			privilege = privilegeManager.createPrivilege(TracerSecurityConstants.Privilege.TREAT_DETECTED_BUGS.toString());
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TRUE.toString());
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.FALSE.toString());
		}
		
		if (privilegeManager.getPrivilege(TracerSecurityConstants.Privilege.CREATE_SECURITY_PROFILE.toString()) == null) {
			privilege = privilegeManager.createPrivilege(TracerSecurityConstants.Privilege.CREATE_SECURITY_PROFILE.toString());
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TRUE.toString());
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.FALSE.toString());
		}
		
		if (privilegeManager.getPrivilege(TracerSecurityConstants.Privilege.CREATE_BUG_DETECTOR.toString()) == null) {
			privilege = privilegeManager.createPrivilege(TracerSecurityConstants.Privilege.CREATE_BUG_DETECTOR.toString());
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TRUE.toString());
			privilegeValue = privilegeManager.createPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.FALSE.toString());
		}
		
		serviceUrl = serviceUrlManager.createServiceUrl(SecurityConstants.URL_SQOOSS + SecurityConstants.URL_DELIMITER_RESOURCE + SecurityConstants.URL_DELIMITER_PRIVILEGE);
				
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
		
		privilege = privilegeManager.getPrivilege(TracerSecurityConstants.Privilege.CREATE_USER.toString());
		privilegeValue = privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TRUE.toString());
		if ((privilegeValue != null) && (group != null))
			groupManager.addPrivilegeToGroup(group.getId(), serviceUrl.getId(), privilegeValue.getId());
		
		privilege = privilegeManager.getPrivilege(TracerSecurityConstants.Privilege.CREATE_BUG_DETECTOR.toString());
		privilegeValue = privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TRUE.toString());
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
		
		privilege = privilegeManager.getPrivilege(TracerSecurityConstants.Privilege.CREATE_SECURITY_PROFILE.toString());
		privilegeValue = privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TRUE.toString());
		if ((privilegeValue != null) && (group != null)) 
			groupManager.addPrivilegeToGroup(group.getId(), serviceUrl.getId(), privilegeValue.getId());
		
		privilege = privilegeManager.getPrivilege(TracerSecurityConstants.Privilege.CREATE_OBSERVED_PROJECT_LIST.toString());
		privilegeValue = privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TRUE.toString());
		if ((privilegeValue != null) && (group != null))
			groupManager.addPrivilegeToGroup(group.getId(), serviceUrl.getId(), privilegeValue.getId());
		
		privilege = privilegeManager.getPrivilege(TracerSecurityConstants.Privilege.ADD_PROJECT_TO_OBSERVED_PROJECT_LIST.toString());
		privilegeValue = privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TRUE.toString());
		if ((privilegeValue != null) && (group != null)) 
			groupManager.addPrivilegeToGroup(group.getId(), serviceUrl.getId(), privilegeValue.getId());
		
		privilege = privilegeManager.getPrivilege(TracerSecurityConstants.Privilege.REMOVE_PROJECT_FROM_OBSERVED_PROJECT_LIST.toString());
		privilegeValue = privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TRUE.toString());
		if ((privilegeValue != null) && (group != null))
			groupManager.addPrivilegeToGroup(group.getId(), serviceUrl.getId(), privilegeValue.getId());
		
		privilege = privilegeManager.getPrivilege(TracerSecurityConstants.Privilege.ADD_BUG_TO_SECURITY_PROFILE.toString());
		privilegeValue = privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TRUE.toString());
		if ((privilegeValue != null) && (group != null))
			groupManager.addPrivilegeToGroup(group.getId(), serviceUrl.getId(), privilegeValue.getId());
		
		privilege = privilegeManager.getPrivilege(TracerSecurityConstants.Privilege.REMOVE_BUG_FROM_SECURITY_PROFILE.toString());
		privilegeValue = privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TRUE.toString());
		if ((privilegeValue != null) && (group != null))
			groupManager.addPrivilegeToGroup(group.getId(), serviceUrl.getId(), privilegeValue.getId());
		
		/*Creation of Vulnerability Manager group*/
		user = userManager.getUser("vulnerability_manager");
		if (user == null)
			user = userManager.createUser("vulnerability_manager", "vulnerability_manager", "vulnerability_manager@tracer.gr");
		
		group = groupManager.getGroup(TracerSecurityConstants.GroupName.VULNERABILITY_MANAGER.toString());
		if (group == null)
			group = groupManager.createGroup(TracerSecurityConstants.GroupName.VULNERABILITY_MANAGER.toString());
		
		privilege = privilegeManager.getPrivilege(TracerSecurityConstants.Privilege.DETECT_CODE_FOR_BUGS.toString());
		privilegeValue = privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TRUE.toString());
		if ((privilegeValue != null) && (group != null))
			groupManager.addPrivilegeToGroup(group.getId(), serviceUrl.getId(), privilegeValue.getId());
		
		privilege = privilegeManager.getPrivilege(TracerSecurityConstants.Privilege.RECORD_DETECTED_BUGS.toString());
		privilegeValue = privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TRUE.toString());
		if ((privilegeValue != null) && (group != null))
			groupManager.addPrivilegeToGroup(group.getId(), serviceUrl.getId(), privilegeValue.getId());
		
		privilege = privilegeManager.getPrivilege(TracerSecurityConstants.Privilege.TREAT_DETECTED_BUGS.toString());
		privilegeValue = privilegeManager.getPrivilegeValue(privilege.getId(), TracerSecurityConstants.PrivilegeValue.TRUE.toString());
		if ((privilegeValue != null) && (group != null))
			groupManager.addPrivilegeToGroup(group.getId(), serviceUrl.getId(), privilegeValue.getId());
	}

	
}
