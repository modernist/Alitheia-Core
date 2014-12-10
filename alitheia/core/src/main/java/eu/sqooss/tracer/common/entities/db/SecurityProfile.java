package eu.sqooss.tracer.common.entities.db;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonManagedReference;

import eu.sqooss.core.AlitheiaCore;
import eu.sqooss.service.db.DAObject;
import eu.sqooss.service.db.DBService;

@XmlRootElement(name="securityProfile")
@Entity
@Table(name="SECURITY_PROFILE")
public class SecurityProfile extends DAObject {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="SECURITY_PROFILE_ID")
	@XmlElement
	private long id;
	
	@XmlElement
	@Column(name="SECURITY_PROFILE_NAME", nullable=false, unique=true)
	private String name;
	
	@XmlElement
	@Column(name="SECURITY_PROFILE_DESCRIPTION", nullable=false)
    private String description;
	
	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	@ManyToMany
    @JoinTable(
            name="DETECTED_VULNERABILITY_TYPE",
            joinColumns={@JoinColumn(name="SECURITY_PROFILE_ID", referencedColumnName="SECURITY_PROFILE_ID")},
            inverseJoinColumns={@JoinColumn(name="VULNERABILITY_TYPE_ID", referencedColumnName="VULNERABILITY_TYPE_ID")})
	//@ManyToMany(mappedBy="detectingSecurityProfiles", targetEntity=VulnerabilityType.class, cascade={CascadeType.ALL})
    @JsonManagedReference
	private Set<VulnerabilityType> detectedVulnerabilityTypes = new HashSet<VulnerabilityType>();
	
	/**
     * Get a set of distinct Vulnerability Types that should be detected when the profile is used
     * @return Set of Vulnerability Types detected by the Security Profile
     */
	public Set<VulnerabilityType> getDetectedVulnerabilityTypes() {
		return detectedVulnerabilityTypes;
	}

	public void setDetectedVulnerabilityTypes(
			Set<VulnerabilityType> detectedVulnerabilityTypes) {
		this.detectedVulnerabilityTypes = detectedVulnerabilityTypes;
	}
	
	 /**
     * Get a list of distinct Security Profiles that have been stored in the system
     * @return List of all Security Profiles
     */
    public static List<SecurityProfile> getSecurityProfiles() {
        DBService dbs = AlitheiaCore.getInstance().getDBService();
        
        StringBuilder q = new StringBuilder("from SecurityProfile sp");
        
        return (List<SecurityProfile>) dbs.doHQL(q.toString());  
    }
}
