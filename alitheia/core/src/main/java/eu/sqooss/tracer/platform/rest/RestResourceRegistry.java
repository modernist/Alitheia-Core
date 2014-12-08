package eu.sqooss.tracer.platform.rest;

import java.util.HashSet;
import java.util.Set;

/**
 * A Singleton Container for JAX-RS Annotated POJOs
 */
public class RestResourceRegistry {

	private Set<Class<?>> resources;
	private static final RestResourceRegistry instance = new RestResourceRegistry();
	
	private RestResourceRegistry() {
		resources = new HashSet<Class<?>>();
	}
	
	public static RestResourceRegistry getInstance() {
		return instance;
	}
	
	public void add (Class<?> resource) {
		resources.add(resource);
	}
	
	public void remove (Class<?> resource) {
		resources.remove(resource);
	}
	
	public Set<Class<?>> getResources() {
		return resources;
	}
	
}
