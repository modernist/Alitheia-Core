package gr.tracer.platform.components;

import gr.tracer.platform.TracerComponent;

/**
 * TRACER Platform REST API component. Allows custom paths to be registered under 
 * the /tracerapi namespace.
 */
public interface RestApiComponent extends TracerComponent {

	/**
	 * Add a resource to the registry. A resource is a JAX-RS annotated POJO.
	 * The class-level path annotation must always be equal to <code>/tracerapi</code>
	 * (i.e. <code>@Path("/tracerapi")</code>), otherwise the resource will not be
	 * accessible.
	 * 
	 * @param resource The resource to add.
	 */
	public void addResource(Class<?> resource);
	
	/**
	 * Remove a resource from the resource registry.
	 * @param resource  The resource to remove.
	 */
	public void removeResource(Class<?> resource);
	
}
