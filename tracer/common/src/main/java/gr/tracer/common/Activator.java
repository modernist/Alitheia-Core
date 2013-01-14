package gr.tracer.common;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import eu.sqooss.core.AlitheiaCore;
import eu.sqooss.service.security.SecurityManager;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		SecurityManager sm = AlitheiaCore.getInstance().getSecurityManager();
	
		/** TODO: Check and/or set up TRACER's user groups, privileges, service urls, etc. */
		/** This setup will reflect the functionality provided by the TRACER REST API */
		
		/**
		sm.getGroupManager() retrieves the GroupManager
		sm.getUserManager() retrieves the UserManager
		sm.getServiceUrlManager() retrieves the ServiceUrlManager
		sm.getPrivilegeManager() retrieves the PrivilegeManager
		Each one offers the methods required for managing the corresponding entities
		Alternatively you can use the shortcut methods sm.createSecurityConfiguration
		and sm.deleteSecurityConfiguration
		*/
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		
	}
	
}
