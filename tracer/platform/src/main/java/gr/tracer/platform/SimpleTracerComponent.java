package gr.tracer.platform;

public interface SimpleTracerComponent<Tinterface> extends TracerComponent {
	
	public Tinterface getComponent();
	
	public Class<Tinterface> getInterfaceClass();
	
	public Class<?> getImplClass();

}
