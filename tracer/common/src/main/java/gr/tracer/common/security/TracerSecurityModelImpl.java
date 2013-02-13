package gr.tracer.common.security;

import eu.sqooss.core.AlitheiaCore;
import eu.sqooss.service.db.Group;
import eu.sqooss.service.db.User;
import eu.sqooss.service.logging.Logger;
import eu.sqooss.service.security.GroupManager;
import eu.sqooss.service.security.SecurityManager;
import eu.sqooss.service.security.UserManager;

public class TracerSecurityModelImpl implements TracerSecurityModel {
	
	private Logger logger;
	private SecurityManager sm;
	
	public TracerSecurityModelImpl() {
		sm = AlitheiaCore.getInstance().getSecurityManager();
	}
	
	public void initSecurityModel() {
		User user = null;
        Group group = null;
		
		GroupManager groupManager = sm.getGroupManager();
		UserManager userManager = sm.getUserManager();
				
		
		if (groupManager.getGroup(TracerSecurityConstants.GroupName.ADMINISTRATOR.toString()) == null)
			groupManager.createGroup(TracerSecurityConstants.GroupName.ADMINISTRATOR.toString());
				
		if (groupManager.getGroup(TracerSecurityConstants.GroupName.PROGRAMMER.toString()) == null)
			groupManager.createGroup(TracerSecurityConstants.GroupName.PROGRAMMER.toString());
		
		if (groupManager.getGroup(TracerSecurityConstants.GroupName.VULNERABILITY_MANAGER.toString()) == null)
			groupManager.createGroup(TracerSecurityConstants.GroupName.VULNERABILITY_MANAGER.toString());
		
		
		sm.createSecurityConfiguration(TracerSecurityConstants.GroupName.ADMINISTRATOR.toString(), TracerSecurityConstants.Privilege.ACTION.toString(), TracerSecurityConstants.PrivilegeValue.CREATE_USER.toString(), TracerSecurityConstants.URL_TRACER + TracerSecurityConstants.URL_DELIMITER_RESOURCE + TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.CREATE_USER.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		sm.createSecurityConfiguration(TracerSecurityConstants.GroupName.ADMINISTRATOR.toString(), TracerSecurityConstants.Privilege.ACTION.toString(), TracerSecurityConstants.PrivilegeValue.CREATE_BUG_DETECTOR.toString(), TracerSecurityConstants.URL_TRACER + TracerSecurityConstants.URL_DELIMITER_RESOURCE + TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.CREATE_BUG_DETECTOR.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		
		sm.createSecurityConfiguration(TracerSecurityConstants.GroupName.PROGRAMMER.toString(), TracerSecurityConstants.Privilege.ACTION.toString(), TracerSecurityConstants.PrivilegeValue.CREATE_OBSERVED_PROJECT_LIST.toString(), TracerSecurityConstants.URL_TRACER + TracerSecurityConstants.URL_DELIMITER_RESOURCE + TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.CREATE_OBSERVED_PROJECT_LIST.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		sm.createSecurityConfiguration(TracerSecurityConstants.GroupName.PROGRAMMER.toString(), TracerSecurityConstants.Privilege.ACTION.toString(), TracerSecurityConstants.PrivilegeValue.ADD_PROJECT_TO_OBSERVED_PROJECT_LIST.toString(), TracerSecurityConstants.URL_TRACER + TracerSecurityConstants.URL_DELIMITER_RESOURCE + TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.ADD_PROJECT_TO_OBSERVED_PROJECT_LIST.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		sm.createSecurityConfiguration(TracerSecurityConstants.GroupName.PROGRAMMER.toString(), TracerSecurityConstants.Privilege.ACTION.toString(), TracerSecurityConstants.PrivilegeValue.REMOVE_PROJECT_FROM_OBSERVED_PROJECT_LIST.toString(), TracerSecurityConstants.URL_TRACER + TracerSecurityConstants.URL_DELIMITER_RESOURCE + TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.REMOVE_PROJECT_FROM_OBSERVED_PROJECT_LIST.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		sm.createSecurityConfiguration(TracerSecurityConstants.GroupName.PROGRAMMER.toString(), TracerSecurityConstants.Privilege.ACTION.toString(), TracerSecurityConstants.PrivilegeValue.CREATE_SECURITY_PROFILE.toString(), TracerSecurityConstants.URL_TRACER + TracerSecurityConstants.URL_DELIMITER_RESOURCE + TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.CREATE_SECURITY_PROFILE.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		sm.createSecurityConfiguration(TracerSecurityConstants.GroupName.PROGRAMMER.toString(), TracerSecurityConstants.Privilege.ACTION.toString(), TracerSecurityConstants.PrivilegeValue.ADD_BUG_TO_SECURITY_PROFILE.toString(), TracerSecurityConstants.URL_TRACER + TracerSecurityConstants.URL_DELIMITER_RESOURCE + TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.ADD_BUG_TO_SECURITY_PROFILE.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		sm.createSecurityConfiguration(TracerSecurityConstants.GroupName.PROGRAMMER.toString(), TracerSecurityConstants.Privilege.ACTION.toString(), TracerSecurityConstants.PrivilegeValue.REMOVE_BUG_FROM_SECURITY_PROFILE.toString(), TracerSecurityConstants.URL_TRACER + TracerSecurityConstants.URL_DELIMITER_RESOURCE + TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.REMOVE_BUG_FROM_SECURITY_PROFILE.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		
		sm.createSecurityConfiguration(TracerSecurityConstants.GroupName.VULNERABILITY_MANAGER.toString(), TracerSecurityConstants.Privilege.ACTION.toString(), TracerSecurityConstants.PrivilegeValue.DETECT_CODE_FOR_BUGS.toString(), TracerSecurityConstants.URL_TRACER + TracerSecurityConstants.URL_DELIMITER_RESOURCE + TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.DETECT_CODE_FOR_BUGS.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		sm.createSecurityConfiguration(TracerSecurityConstants.GroupName.VULNERABILITY_MANAGER.toString(), TracerSecurityConstants.Privilege.ACTION.toString(), TracerSecurityConstants.PrivilegeValue.RECORD_DETECTED_BUGS.toString(), TracerSecurityConstants.URL_TRACER + TracerSecurityConstants.URL_DELIMITER_RESOURCE + TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.RECORD_DETECTED_BUGS.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		sm.createSecurityConfiguration(TracerSecurityConstants.GroupName.VULNERABILITY_MANAGER.toString(), TracerSecurityConstants.Privilege.ACTION.toString(), TracerSecurityConstants.PrivilegeValue.TREAT_DETECTED_BUGS.toString(), TracerSecurityConstants.URL_TRACER + TracerSecurityConstants.URL_DELIMITER_RESOURCE + TracerSecurityConstants.Privilege.ACTION.toString() + '=' + TracerSecurityConstants.PrivilegeValue.TREAT_DETECTED_BUGS.toString() + TracerSecurityConstants.URL_DELIMITER_PRIVILEGE);
		
		
		/* Creation of user Administrator */	
		user = userManager.getUser("admin");
		if (user == null)
			user = userManager.createUser("admin", "admin", "admin@tracer.gr");
		
		group = groupManager.getGroup(TracerSecurityConstants.GroupName.ADMINISTRATOR.toString());		
		if (group != null)
			groupManager.addUserToGroup(group.getId(), user.getId());
		
		
		/* Creation of user Programmer */
		user = userManager.getUser("programmer");
		if (user == null)
			user = userManager.createUser("programmer", "programmer", "programmer@tracer.gr");
		
		group = groupManager.getGroup(TracerSecurityConstants.GroupName.PROGRAMMER.toString());			
		if (group != null)
			groupManager.addUserToGroup(group.getId(), user.getId());
		
		
		/* Creation of user Vulnerability Manager */
		user = userManager.getUser("vulnerability_manager");
		if (user == null)
			user = userManager.createUser("vulnerability_manager", "vulnerability_manager", "vulnerability_manager@tracer.gr");
		
		group = groupManager.getGroup(TracerSecurityConstants.GroupName.VULNERABILITY_MANAGER.toString());
		if (group != null) 
			groupManager.addUserToGroup(group.getId(), user.getId());
		
	}
	
	public boolean createNewUser(String aAUsername, String aAPassword, String aAType, String aAName, String aAEmail) {
		
		User user = null;
        Group group = null;
        
		GroupManager groupManager = sm.getGroupManager();
		UserManager userManager = sm.getUserManager();
		
		userManager = sm.getUserManager();
		groupManager = sm.getGroupManager();
		
		user = userManager.getUser(aAUsername);
		if ( user == null) {
			user = userManager.createUser(aAUsername, aAPassword, aAEmail);
		} else {
			logger.info("User already exists"); 
			return false;
		}
		
		group = groupManager.getGroup(aAType);
		
		if (group != null) {
			groupManager.addUserToGroup(group.getId(), user.getId());
		} else {
			logger.info("Group does not exist"); 
			return false;
		}
		
		logger.info("Created user with the name " + aAUsername + " as " + aAType);
		return true;
	}
	
	public boolean userLoginAttempt(String aAUsername, String aAPassword) {
		
		User user = null;       
		UserManager userManager;

		userManager = sm.getUserManager();		
		user = userManager.getUser(aAUsername);
		
		if (user != null) {
			if (user.getPassword().equals(aAPassword))
				logger.info("Authenticated user with username " + aAUsername);
			else {
				logger.info("Wrong password for user with username " + aAUsername);
				return false;
			}
		} else {
			logger.info("There is no user with username " + aAUsername);
			return false;
		}
		
		return true;
	}
	
}
