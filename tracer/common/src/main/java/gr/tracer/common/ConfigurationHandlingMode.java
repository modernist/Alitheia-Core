package gr.tracer.common;

/** An enumeration of the different configuration handling modes */
public enum ConfigurationHandlingMode {
	/** No configuration options required by the external tool */
	NO_CONFIGURATION,
	/** The processing will take place using only the active configuration */
	ACTIVE_CONFIGURATION,
	/** The processing will take place using all available configurations sequentially */
	ALL_CONFIGURATIONS
}
