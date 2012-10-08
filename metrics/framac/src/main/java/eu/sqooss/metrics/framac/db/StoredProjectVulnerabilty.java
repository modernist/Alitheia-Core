package eu.sqooss.metrics.framac.db;

import eu.sqooss.service.db.DAObject;
import eu.sqooss.service.db.StoredProject;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Instances of this class represent a vulnerability detected on
 * a specific stored project, as stored in the database
 */
@Entity
@Table(name="STORED_PROJECT_VULNERABILITY")
@XmlRootElement(name="project-vulnerability")
public class StoredProjectVulnerabilty extends Vulnerability {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "STORED_PROJECT_VULNERABILITY_ID")
    @XmlElement(name = "id")
	private long id;
	
	/**
     * The type of this vulnerability instance
     */
    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="VULNERABILITY_TYPE_ID", referencedColumnName="VULNERABILITY_TYPE_ID")
	private VulnerabilityType vulnerabilityType;
	
    /**
     * The StoredProject to which this vulnerability relates
     */
	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="STORED_PROJECT_ID", referencedColumnName="PROJECT_ID")
	private StoredProject storedProject;
	
	@Column(name="LOCATION")
	private String location;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}
	
	public VulnerabilityType getType() {
		return vulnerabilityType;
	}
	
	public void setType(VulnerabilityType type) {
		vulnerabilityType = type;
	}
	
	public void setStoredProject(StoredProject sp) {
		this.storedProject = sp;
	}

	public StoredProject getStoredProject() {
		return storedProject;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public DAObject getAssociatedEntity() {
		return getStoredProject();
	}

	@Override
	public void setAssociatedEntity(DAObject o) {
		if(o instanceof StoredProject)
			setStoredProject((StoredProject)o);
	}
	
}

//vi: ai nosi sw=4 ts=4 expandtab
