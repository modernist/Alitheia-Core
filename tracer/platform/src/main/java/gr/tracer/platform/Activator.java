package gr.tracer.platform;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import eu.sqooss.core.AlitheiaCore;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		try {
			AlitheiaCore core = AlitheiaCore.getInstance();
			
			if(core != null) {
				core.registerService(TracerPlatformService.class, TracerPlatformServiceImpl.class);
			}
			
		} catch(Exception e) {
			System.err.println(e);
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		
	}
	
}
