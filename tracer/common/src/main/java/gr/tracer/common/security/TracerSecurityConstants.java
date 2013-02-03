package gr.tracer.common.security;

import eu.sqooss.service.security.SecurityConstants;

public class TracerSecurityConstants {
	/**
     * Represents some of the privileges.
     * The user should use the toString() method.  
     */
	
public static final char URL_DELIMITER_RESOURCE  = '?';
    
    /**
     * This character splits the privileges in the security url. 
     */
    public static final char URL_DELIMITER_PRIVILEGE = '&';
    
    /**
     * Represents the url of the SQO-OSS system.
     */
    public static final String URL_TRACER = "svc://sqooss";
    
    /**
     * Represents the url of the service system.
     */
    public static final String URL_TRACER_SERVICE      = TracerSecurityConstants.URL_TRACER + ".service";
    
    /**
     * Represents the url of the database connectivity.
     */
    public static final String URL_TRACER_DATABASE     = TracerSecurityConstants.URL_TRACER + ".database";
    
    /**
     * Represents the url of the security.
     */
    public static final String URL_TRACER_SECURITY     = TracerSecurityConstants.URL_TRACER + ".security";
    
    /**
     * Represents the url of the web services.
     */
    public static final String URL_TRACER_WEB_SERVICES = TracerSecurityConstants.URL_TRACER + ".webservices";
    
    /**
     * Represents the url of the scheduling.
     */
    public static final String URL_TRACER_SCHEDULING   = TracerSecurityConstants.URL_TRACER + ".scheduling";
    
    /**
     * Represents the url of the updater.
     */
    public static final String URL_TRACER_UPDATER      = TracerSecurityConstants.URL_TRACER + ".updater";
    
    public static final String URL_TRACER_PROJECTS     = TracerSecurityConstants.URL_TRACER + ".projects";
	
    public static enum Privilege {
        ACTION,
        PROJECT_ID,
        PROJECT_VERSION_ID,
        METRIC_ID,
        USER_ID,
        ALL;
        public String toString() {
            String name = name();
            if (name.equals(ALL.name())) {
                return "<all privileges>";
            } else {
                return name.toLowerCase(); 
            }
        }
        
    };
    
    public static enum PrivilegeValue {
        READ,
        WRITE,
        CREATE_USER,
        CREATE_OBSERVED_PROJECT_LIST,
        ADD_PROJECT_TO_OBSERVED_PROJECT_LIST,
        REMOVE_PROJECT_FROM_OBSERVED_PROJECT_LIST,
        DETECT_CODE_FOR_BUGS,
        RECORD_DETECTED_BUGS,
        TREAT_DETECTED_BUGS,
        CREATE_SECURITY_PROFILE,
        CREATE_BUG_DETECTOR,
        ADD_BUG_TO_SECURITY_PROFILE,
        REMOVE_BUG_FROM_SECURITY_PROFILE,
        ALL;
        public String toString() {
            String name = name();
            if (name.equals(ALL.name())) {
                return "<all privilege values>";
            } else {
                return name().toLowerCase();
            }
        }
    }
    
    public static enum GroupName {
        PROGRAMMER,
        ADMINSTRATOR,
        VULNERABILITY_MANAGER,
        ALL;
        public String toString() {
            String name = name();
            if (name.equals(ALL.name())) {
                return "<all groups>";
            } else {
                return name.toLowerCase(); 
            }
        }     
    }
}
