package gr.tracer.common;

/**
 * Defines the ways in which an external vulnerability detection tool can be invoked
 * 
 * @author Kostas Stroggylos
 */
public enum ExternalToolInvocationMode {
	/* The tool can detect vulnerabilities on the whole project source tree */
	PerProjectVersion,
	/* The tool must be invoked separately for each file */
	PerProjectFile
}
