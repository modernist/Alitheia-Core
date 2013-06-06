package gr.tracer.platform.components;

import gr.tracer.platform.TracerComponent;

public interface SimpleTracerComponent<Tinterface> extends TracerComponent {
	
	public Tinterface getComponent();
	
	public Class<Tinterface> getInterfaceClass();
	
	public Class<?> getImplClass();

}
