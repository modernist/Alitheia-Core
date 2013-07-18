package gr.tracer.platform;

import org.osgi.framework.BundleContext;

import eu.sqooss.core.AlitheiaCore;
import eu.sqooss.service.logging.Logger;
import eu.sqooss.service.pa.PluginAdmin;

/**
 * 
 * @author circular
 * The purpose of TracerPlatformService is to initialize the single 
 * TracerPlatform instance once the initialization of Alitheia Core is complete
 */
public class TracerPlatformServiceImpl implements TracerPlatformService {

	private final static String VULNERABILITY_DETECTOR_CLASS = "*.vulnerabilitydetectors.*";
	
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
		logger.info("TracerPlatformService starting up");
		platform = TracerPlatform.getInstance();
		
		PluginAdmin pa = AlitheiaCore.getInstance().getPluginAdmin();
		if(pa == null) {
			logger.error("TRACER platform service unable to access PluginAdmin");
			return false;
		}
		pa.addServiceReferenceFilters(new String[] { VULNERABILITY_DETECTOR_CLASS });
		
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
