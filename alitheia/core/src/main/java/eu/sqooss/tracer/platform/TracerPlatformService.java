package eu.sqooss.tracer.platform;

import eu.sqooss.core.AlitheiaCoreService;

public interface TracerPlatformService extends AlitheiaCoreService{

	/* Returns a reference to the single instance of TracerPlatform */
	public TracerPlatform getPlatform();
	
}
