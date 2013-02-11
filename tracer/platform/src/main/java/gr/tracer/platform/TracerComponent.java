package gr.tracer.platform;

import eu.sqooss.service.logging.Logger;

/**
 * An interface to control TRACER components. All components implementing 
 * this interface should also contain a parameterless constructor.
 * The component initialization sequence involves setting the initialization
 * parameters with the {@link #initComponent(TracerPlatform, Logger)} method
 * and then calling the {@link #startUp()} method.
 */
public interface TracerComponent {
	
	/* Wires up the component to the TRACER Platform */
	public void initComponent(TracerPlatform platform, Logger logger);
	
	/* Starts the component */
	public boolean startUp();
	
	/* Asks the component to shut down*/
	public boolean shutDown();
	
}
