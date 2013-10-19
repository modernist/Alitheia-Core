package gr.tracer.platform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.osgi.framework.BundleContext;

import eu.sqooss.core.AlitheiaCore;
import eu.sqooss.core.AlitheiaCoreService;
import eu.sqooss.service.db.DBService;
import eu.sqooss.service.logging.LogManager;
import eu.sqooss.service.logging.Logger;
import gr.tracer.platform.components.RestApiComponent;
import gr.tracer.platform.components.SecurityModelComponent;
import gr.tracer.platform.components.VulnerabilityDetectionComponent;
import gr.tracer.platform.components.SecurityProfileComponent;
import gr.tracer.platform.components.UserComponent;
import gr.tracer.platform.components.VulnerabilityDetectorActivatorComponent;
import gr.tracer.platform.components.impl.RestApiComponentImpl;
import gr.tracer.platform.components.impl.SecurityModelComponentImpl;
import gr.tracer.platform.components.impl.SecurityProfileComponentImpl;
import gr.tracer.platform.components.impl.UserComponentImpl;
import gr.tracer.platform.components.impl.VulnerabilityDetectionComponentImpl;
import gr.tracer.platform.components.impl.VulnerabilityDetectorActivatorComponentImpl;

/**
 * 
 * @author Kostas Stroggylos
 * 
 * TracerPlatform acts as the point of reference for TRACER's components
 */
public class TracerPlatform {

	/* The single instance of the Platform */
	private static TracerPlatform instance;
	
	private AlitheiaCore core;
    private LogManager logManager;
    private Logger logger;
    private DBService dbs;
    private static BundleContext bc;
	
	/** Holds initialized component instances */
    private Map<Class<? extends TracerComponent>, Object> instances;
    
    /* Component Configuration */
    private static Vector<Class<? extends TracerComponent>> components;
    private static Map<Class<? extends TracerComponent>, Class<?>> implementations;
	
    static {
        components = new Vector<Class<? extends TracerComponent>>();
        implementations = new HashMap<Class<? extends TracerComponent>, Class<?>>();

    	/* 
    	 * Components are initialized in the order they appear in this list
    	 */
        
        components.add(SecurityModelComponent.class);
        components.add(SecurityProfileComponent.class);
        components.add(VulnerabilityDetectionComponent.class);
        components.add(UserComponent.class);
        components.add(VulnerabilityDetectorActivatorComponent.class);
        components.add(RestApiComponent.class);
        
        implementations.put(SecurityModelComponent.class, SecurityModelComponentImpl.class);
        implementations.put(SecurityProfileComponent.class, SecurityProfileComponentImpl.class);
        implementations.put(VulnerabilityDetectionComponent.class, VulnerabilityDetectionComponentImpl.class);
        implementations.put(UserComponent.class, UserComponentImpl.class);
        implementations.put(VulnerabilityDetectorActivatorComponent.class, VulnerabilityDetectorActivatorComponentImpl.class);
        implementations.put(RestApiComponent.class, RestApiComponentImpl.class);
    }
	
	/* Private constructor to ensure single instance creation */
	private TracerPlatform(BundleContext bc) {
		instances = new HashMap<Class<? extends TracerComponent>, Object>();
		
		core = AlitheiaCore.getInstance();
		logManager = core.getLogManager();
		logger = logManager.createLogger("tracer");
		dbs = core.getDBService();
		init();
	}
	
	static void setBundleContext(BundleContext bc) {
		TracerPlatform.bc = bc;
	}
	
	public BundleContext getBundleContext() {
		return bc;
	}
	
	public DBService getDB() {
		return this.dbs;
	}
	
	/**
	 * Retrieves a reference to the instance of the class implementing the requested
	 * component interface
	 * @param component The component interface requested
	 * @return A reference to the class that implements the component interface
	 */
	@SuppressWarnings("unchecked")
	public synchronized <T extends TracerComponent> T getComponent(Class<? extends TracerComponent> component) {
		try {
			if(components.contains(component)){
				return (T) instances.get(component);
			}
		} catch(Exception e) {
			logger.error("Unable to retrieve Component of type " + component, e);
		}
		return null;
	}
	
	/* Returns the single instance of the Platform */
	public static synchronized TracerPlatform getInstance() {
		if(instance == null) {
			instance = new TracerPlatform(TracerPlatform.bc);
		}
		return instance;
	}
	
	/**
     * Register an external implementation of a TracerComponent component. It
     * will override any internally defined implementation.
     *
     * @param component The component interface to register an implementation for
     * @param impl The class that implements the registered component interface
     */
    public synchronized void registerComponent(
            Class<? extends TracerComponent> component,
            Class<?> impl) {

        if (!components.contains(component))
            components.add(component);
        implementations.put(component, impl);
        initComponent(component);
    }

    /**
     * Unregisters an external implementation of a TracerComponent component. 
     * This method does not check if external entities hold references to the
     * component to be unregistered.
     */
    public synchronized void unregisterComponent(
            Class<? extends TracerComponent> component) {
        implementations.remove(component);
    }
    
    /**
     * Utility method to retrieve the instance of an Alitheia Core Service
     * @param clazz The type of service to retrieve
     * @return The instance implementing the requested service interface
     */
    public <T extends AlitheiaCoreService> T getAlitheiaCoreService(Class<? extends AlitheiaCoreService> clazz) {
    	try {
    		return core.getAlitheiaCoreService(clazz);
    	} catch(Exception e) {
    		logger.error("Unable to retrieve AlitheiaCore service of type " + clazz, e);
    		return null;
    	}
    }
	
    /**
     * Shuts down the platform by shutting down all components.
     */
    public void shutDown() {
    	/* Shutdown the components in reverse order */
    	List<Class<? extends TracerComponent>> keys = new 
    			ArrayList<Class<? extends TracerComponent>>(implementations.keySet());
    	Collections.reverse(keys);
    	
    	for(Class<? extends TracerComponent> component: keys) {
    		Object o = instances.get(component);
    		try	{
    			component.cast(o).shutDown();
    			instances.remove(component);
    		} catch (Throwable t) {
    			t.printStackTrace();
			}   
    	}
    }
    
    /**
     * This method performs initialization of the <code>TracerPlatform</code>
     * object by instantiating the platform components, by calling the 
     * method on their component interface. Failures are reported but do not 
     * block the instantiation of other components).
     */
    private void init() {
                for (Class<? extends TracerComponent> c : components) {
            initComponent(c);
        }

    }
	
    private synchronized void initComponent(Class<? extends TracerComponent> c) {
        Class<?> impl = implementations.get(c);

        if (impl == null) {
            logger.error("No implementation found for component " + c);
            return;
        }

        try {
            Object o = impl.newInstance();

            if (o == null) {
                logger.error("Component object for " + c
                        + " component could not be created");
                return;
            }

            //Extract the unique component portion of the class FQN.
            //e.g. from gr.tracer.platorm.rest.RestApi -> rest
            String[] paths = c.getCanonicalName().split("\\.");

            c.cast(o).initComponent(this, 
            		logManager.createLogger("tracer." + paths[3]));

            if (!c.cast(o).startUp()) {
                logger.error("Component " + c + " could not be started");
                return;
            }

            instances.put(c, c.cast(o));
            logger.info("Component " + impl.getName() + " started");
        } catch (Exception e) {
            logger.error("Error while initializing component " + c, e);
        }
    }
}
