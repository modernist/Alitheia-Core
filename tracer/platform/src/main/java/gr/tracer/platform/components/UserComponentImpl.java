package gr.tracer.platform.components;

import eu.sqooss.core.AlitheiaCore;
import eu.sqooss.service.db.Group;
import eu.sqooss.service.db.User;
import eu.sqooss.service.logging.Logger;
import eu.sqooss.service.security.GroupManager;
import eu.sqooss.service.security.SecurityManager;
import eu.sqooss.service.security.UserManager;
import gr.tracer.platform.TracerPlatform;
import gr.tracer.platform.security.TracerSecurityConstants;

public class UserComponentImpl implements UserComponent {
	
	private TracerPlatform platform;
	private Logger logger;
	private SecurityManager sm;

	public User createNewUser(String aAUsername, String aAPassword, String aAType, String aAName, String aAEmail) {
		
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
			return null;
		}
		
		if (aAType.toLowerCase().equals(TracerSecurityConstants.GroupName.ADMINISTRATOR.toString()))
			group = groupManager.getGroup(TracerSecurityConstants.GroupName.ADMINISTRATOR.toString());
		else if (aAType.toLowerCase().equals(TracerSecurityConstants.GroupName.PROGRAMMER.toString()))
			group = groupManager.getGroup(TracerSecurityConstants.GroupName.PROGRAMMER.toString());
		else if (aAType.toLowerCase().equals(TracerSecurityConstants.GroupName.VULNERABILITY_MANAGER.toString()))
			group = groupManager.getGroup(TracerSecurityConstants.GroupName.VULNERABILITY_MANAGER.toString());
		group = groupManager.getGroup(TracerSecurityConstants.GroupName.PROGRAMMER.toString());
		if (group != null) {
			groupManager.addUserToGroup(group.getId(), user.getId());
		} else {
			logger.info("Group does not exist"); 
			return null;
		}
		
		logger.info("Created user with the name " + aAUsername + " as " + aAType);
		return user;
	}
	
	public User userLoginAttempt(String aAUsername, String aAPassword) {
		
		User user = null;       
		UserManager userManager;

		userManager = sm.getUserManager();		
		user = userManager.getUser(aAUsername);
		
		if (user != null) {
			if (user.getPassword().equals(userManager.getHash(aAPassword)))
				logger.info("Authenticated user with username " + aAUsername);
			else {
				logger.info("Wrong password for user with username " + aAUsername);
				return null;
			}
		} else {
			logger.info("There is no user with username " + aAUsername);
			return null;
		}
		
		return user;
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
		sm = AlitheiaCore.getInstance().getSecurityManager();
		userLoginAttempt("admin","admin");
		createNewUser("fotis","fotis","programmer","kostas","kostas@tracer.gr");
		return true;
	}

	@Override
	public boolean shutDown() {
		// TODO Auto-generated method stub
		return false;
	}
}
