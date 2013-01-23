package gr.tracer.common.security;

public class TracerSecurityConstants {
	/**
     * Represents some of the privileges.
     * The user should use the toString() method.
     */
    public static enum Privilege {
        ACTION,
        PROJECT_ID,
        PROJECT_VERSION_ID,
        METRIC_ID,
        USER_ID,
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
                return "<all privileges>";
            } else {
                return name.toLowerCase(); 
            }
        }
        
    };
    
    public static enum PrivilegeValue {
        READ,
        WRITE,
        TRUE,
        FALSE,
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
