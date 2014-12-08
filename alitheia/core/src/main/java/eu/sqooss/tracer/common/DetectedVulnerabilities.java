package eu.sqooss.tracer.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A container type for detected vulnerability annotations.
 *  
 * @author Kostas Stroggylos <stroggylos@gmail.com>
 * @see gr.tracer.common.DetectedVulnerability
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface DetectedVulnerabilities {

	/**
	 * List of declared vulnerability types supported by a vulnerability detector.
	 */
	DetectedVulnerability[] vulnerabilities() default {};
}
