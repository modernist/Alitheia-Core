package eu.sqooss.tracer.platform.components;

import eu.sqooss.tracer.platform.TracerComponent;

public interface SimpleTracerComponent<Tinterface> extends TracerComponent {
	
	public Tinterface getComponent();
	
	public Class<Tinterface> getInterfaceClass();
	
	public Class<?> getImplClass();

}
