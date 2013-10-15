package gr.tracer.platform.components.impl;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;

import eu.sqooss.service.logging.Logger;
import gr.tracer.platform.TracerPlatform;
import gr.tracer.platform.components.RestApiComponent;
import gr.tracer.platform.rest.RestApiDispatcher;
import gr.tracer.platform.rest.RestResourceRegistry;

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
		addResource(gr.tracer.platform.controllers.UserController.class);
		//Add all JAX-RS annotated POJOs here
		return true;
	}

	@Override
	public boolean shutDown() {
		removeResource(gr.tracer.platform.controllers.UserController.class);
		//Remove all JAX-RS annotated POJOs here
		unregisterApp();
		return false;
	}

	@Override
	public void addResource(Class<?> resource) {
		unregisterApp();
		registry.add(resource);
		registerApp();
	}

	@Override
	public void removeResource(Class<?> resource) {
		unregisterApp();
		registry.remove(resource);
		registerApp();
	}
	
	private void registerApp() {
		HttpService http = getHttpService();

		Dictionary<String, String> params = new Hashtable<String, String>();
		params.put("resteasy.scan", "false");
		params.put("javax.ws.rs.Application", "gr.tracer.platform.rest.RestApiApplication");

		RestApiDispatcher dispatcher = new RestApiDispatcher();
		try {
			http.registerServlet("/tracerapi", dispatcher, params, null);
		} catch (Exception e) {
			logger.error("Error registering ResteasyServlet", e);
		}
	}

	private void unregisterApp() {
		HttpService http = getHttpService();
		http.unregister("/tracerapi");
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
