package gr.tracer.platform.components.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import eu.sqooss.service.logging.Logger;
import gr.tracer.platform.TracerPlatform;
import gr.tracer.platform.components.SimpleTracerComponent;

/**
 * Simple Tracer component base implementation
 * @author Kostas Stroggylos
 *
 * @param <T> The type of object to encapsulate in a component. Must contain
 * a parameterless constructor. Needs an abstract factory to be usable.
 */
public class SimpleTracerComponentImpl<Tinterface, Timpl> implements SimpleTracerComponent<Tinterface> {

	private TracerPlatform platform;
	private Logger logger;
	private Timpl component;
	private Class<Tinterface> interfaceClass;
	private Class<Timpl> componentClass;
	
	public SimpleTracerComponentImpl(Class<Timpl> componentClass) {
		this.componentClass = componentClass;
		Type[] interfaces = this.getClass().getGenericInterfaces();
		ParameterizedType ifType = (ParameterizedType)interfaces[0];
		interfaceClass = (Class<Tinterface>)ifType.getRawType();
	}
	
	@Override
	public void initComponent(TracerPlatform platform, Logger logger) {
		this.platform = platform;
		this.logger = logger;
		onInitComponent();
	}

	protected void onInitComponent() {}
	
	@Override
	public boolean startUp() {
		
		try {
			 component = componentClass.newInstance();
		} catch(Exception e) {
			logger.error("Failed to instantiate component", e);
			return false;
		}
		
		return onStartUp();
	}
	
	protected boolean onStartUp() {
		return true;
	}

	@Override
	public boolean shutDown() {
		return onShutDown();
	}

	protected boolean onShutDown() {
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public Tinterface getComponent() {
		return (Tinterface)component;
	}
	
	public Class<Tinterface> getInterfaceClass() {
		return interfaceClass;
	}
	
	public Class<Timpl> getImplClass() {
		return componentClass;
	}

}
