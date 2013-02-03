package gr.tracer.common;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import eu.sqooss.core.AlitheiaCore;

import gr.tracer.common.security.TracerSecurityModel;
import gr.tracer.common.security.TracerSecurityModelImpl;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		
		TracerSecurityModelImpl tracer = AlitheiaCore.getInstance().TracerSecurityModel();
		
		try {
			tracer.initSecurityModel();
		} catch(Exception e) {
			err(e.toString());
		}
	}

	
	@Override
	public void stop(BundleContext context) throws Exception {
		
	}
	
	private void err(String msg) {
		System.err.println("TRACER.Common: " + msg);
	}
}
