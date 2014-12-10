package eu.sqooss.tracer.common.entities.db;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import eu.sqooss.core.AlitheiaCore;
import eu.sqooss.service.db.DAObject;
import eu.sqooss.service.db.DBService;

import javax.persistence.CascadeType;
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

@XmlRootElement(name="securityLibrary")
@Entity
@Table(name="SECURITY_LIBRARY")
public class SecurityLibrary extends DAObject {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="SECURITY_LIBRARY_ID")
	@XmlElement
	private long id;
	
	@XmlElement
	@Column(name="SECURITY_LIBRARY_NAME", nullable=false, unique=true)
	private String name;
	
	@XmlElement
	@Column(name="SECURITY_LIBRARY_DESCRIPTION", nullable=false)
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
            name="TREATED_VULNERABILITY_TYPE",
            joinColumns={@JoinColumn(name="SECURITY_LIBRARY_ID", referencedColumnName="SECURITY_LIBRARY_ID")},
            inverseJoinColumns={@JoinColumn(name="VULNERABILITY_TYPE_ID", referencedColumnName="VULNERABILITY_TYPE_ID")})
	//@ManyToMany(mappedBy="treatingSecurityLibraries", targetEntity=VulnerabilityType.class, cascade={CascadeType.ALL})
    @JsonManagedReference
	private Set<VulnerabilityType> treatedVulnerabilityTypes = new HashSet<VulnerabilityType>();
	
	/**
     * Get a set of distinct Vulnerability Types that can be treated by the library
     * @return Set of Vulnerability Types treated by the Security Library
     */
	public Set<VulnerabilityType> getTreatedVulnerabilityTypes() {
		return treatedVulnerabilityTypes;
	}

	public void setTreatedVulnerabilityTypes(
			Set<VulnerabilityType> treatedVulnerabilityTypes) {
		this.treatedVulnerabilityTypes = treatedVulnerabilityTypes;
	}
	
	 /**
     * Get a list of distinct Security Libraries that have been stored in the system
     * @return List of all Security Libraries
     */
    public static List<SecurityLibrary> getSecurityLibraries() {
        DBService dbs = AlitheiaCore.getInstance().getDBService();
        
        StringBuilder q = new StringBuilder("from SecurityLibrary sl");
        
        return (List<SecurityLibrary>) dbs.doHQL(q.toString());  
    }
}
