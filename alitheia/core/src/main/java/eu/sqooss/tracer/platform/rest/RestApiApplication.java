package eu.sqooss.tracer.platform.rest;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 * The JAX-RS Application used in the TRACER REST Api implementation
 *
 */
public class RestApiApplication extends Application {
	
	public RestApiApplication() {
	}
	
	@Override
	public Set<Class<?>> getClasses() {
		return RestResourceRegistry.getInstance().getResources();
	}
	
	@Override
	public Set<Object> getSingletons() {
		return null;
	}
}
