package eu.sqooss.tracer.platform.components.impl;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;

import eu.sqooss.core.AlitheiaCore;
import eu.sqooss.service.logging.Logger;
import eu.sqooss.service.rest.RestService;
import eu.sqooss.tracer.platform.components.RestApiComponent;
import eu.sqooss.tracer.platform.rest.RestApiDispatcher;
import eu.sqooss.tracer.platform.rest.RestResourceRegistry;
import eu.sqooss.tracer.platform.TracerPlatform;

public class RestApiComponentImpl implements RestApiComponent {

	private BundleContext bc = null;
	private TracerPlatform platform = null;
	private Logger logger = null;
	private RestResourceRegistry registry = null;
	
	@Override
	public void initComponent(TracerPlatform platform, Logger logger) {
		this.platform = platform;
		this.bc = platform.getBundleContext();
		this.logger = logger;
		registry = RestResourceRegistry.getInstance();
	}

	@Override
	public boolean startUp() {
		addResource(eu.sqooss.tracer.platform.controllers.UserController.class);
		addResource(eu.sqooss.tracer.platform.controllers.SecurityProfileController.class);
		addResource(eu.sqooss.tracer.platform.controllers.VulnerabilityDetectionController.class);
		//Add all JAX-RS annotated POJOs here
		return true;
	}

	@Override
	public boolean shutDown() {
		removeResource(eu.sqooss.tracer.platform.controllers.UserController.class);
		removeResource(eu.sqooss.tracer.platform.controllers.SecurityProfileController.class);
		removeResource(eu.sqooss.tracer.platform.controllers.VulnerabilityDetectionController.class);
		//Remove all JAX-RS annotated POJOs here
		unregisterApp();
		return false;
	}

	@Override
	public void addResource(Class<?> resource) {
		unregisterApp();
		registry.add(resource);
		registerApp();
		
		
		try {
			RestService rs = AlitheiaCore.getInstance().getRestService();
			
			if(rs != null) {
				Class<?> clazz = rs.loadResource(resource.getCanonicalName());
				if(clazz != null)
					rs.addResource(clazz);
			}
			
		} catch (Exception e) {
			logger.error("Could not get REST service!", e);
		}
	}

	@Override
	public void removeResource(Class<?> resource) {
		unregisterApp();
		registry.remove(resource);
		registerApp();
		
		try {
			RestService rs = AlitheiaCore.getInstance().getRestService();
			
			if(rs != null) {
				Class<?> clazz = rs.loadResource(resource.getCanonicalName());
				if(clazz != null)
					rs.removeResource(clazz);
			}
			
		} catch (Exception e) {
			logger.error("Could not get REST service!", e);
		}
	}
	
	private void registerApp() {
		try {
			HttpService http = getHttpService();
	
			Dictionary<String, String> params = new Hashtable<String, String>();
			params.put("resteasy.scan", "false");
			params.put("resteasy.use.builtin.providers", "true");
			params.put("resteasy.servlet.mapping.prefix", "/tracerapi/*");
			params.put("javax.ws.rs.Application", "eu.sqooss.service.rest.RestServiceApp");
			
			RestApiDispatcher dispatcher = new RestApiDispatcher(); 
			
			http.registerServlet("/tracerapi", dispatcher, params, null);
			
		} catch (Exception e) {
			logger.error("Error registering TRACER ResteasyServlet", e);
		}
	}

	private void unregisterApp() {
		try {
			HttpService http = getHttpService();
			http.unregister("/tracerapi");
		} catch (Exception e) {
			logger.error("Error unregistering TRACER ResteasyServlet", e);
		}
	}
	
	private HttpService getHttpService() {
		HttpService http = null;
		ServiceReference httpRef = bc.getServiceReference(
				HttpService.class.getName());

		if (httpRef != null) {
			http = (HttpService) bc.getService(httpRef);
		} else {
			logger.error("Could not find a HTTP service!");
		}
		
		return http;
	}

}
