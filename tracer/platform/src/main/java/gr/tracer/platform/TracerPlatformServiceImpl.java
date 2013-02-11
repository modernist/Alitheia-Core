package gr.tracer.platform;

import org.osgi.framework.BundleContext;

import eu.sqooss.service.logging.Logger;

/**
 * 
 * @author circular
 * The purpose of TracerPlatformService is to initialize the single 
 * TracerPlatform instance once the initialization of Alitheia Core is complete
 */
public class TracerPlatformServiceImpl implements TracerPlatformService {

	private BundleContext bc;
	private Logger logger;
	private TracerPlatform platform;	
	
	public TracerPlatformServiceImpl() {
	}
	
	public TracerPlatform getPlatform() {
		return platform;
	}
	
	@Override
	public boolean startUp() {
		logger.info("TracerPlatformSeervice starting up");
		platform = TracerPlatform.getInstance();
		return true;
	}

	@Override
	public void shutDown() {
		logger.info("TracerPlatformService shutting down");
		platform.shutDown();
	}

	@Override
	public void setInitParams(BundleContext bc, Logger l) {
		this.bc = bc;
		this.logger = l;
	}

}
