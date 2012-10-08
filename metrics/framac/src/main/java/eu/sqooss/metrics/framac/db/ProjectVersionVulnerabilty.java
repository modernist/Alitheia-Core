package eu.sqooss.metrics.framac.db;

import eu.sqooss.service.db.DAObject;
import eu.sqooss.service.db.ProjectVersion;

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
@Table(name="PROJECT_VERSION_VULNERABILITY")
@XmlRootElement(name="version-vulnerability")
public class ProjectVersionVulnerabilty extends Vulnerability {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PROJECT_VERSION_VULNERABILITY_ID")
    @XmlElement(name = "id")
	private long id;
	
	/**
     * The type of this vulnerability instance
     */
    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="VULNERABILITY_TYPE_ID", referencedColumnName="VULNERABILITY_TYPE_ID")
	private VulnerabilityType vulnerabilityType;
	
    /**
     * The ProjectVersion to which this vulnerability relates
     */
	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="PROJECT_VERSION_ID", referencedColumnName="PROJECT_VERSION_ID")
	private ProjectVersion projectVersion;
	
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
	
	public void setProjectVersion(ProjectVersion pv) {
		this.projectVersion = pv;
	}

	public ProjectVersion getProjectVersion() {
		return projectVersion;
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
		return getProjectVersion();
	}

	@Override
	public void setAssociatedEntity(DAObject o) {
		if(o instanceof ProjectVersion)
			setProjectVersion((ProjectVersion)o);
	}
	
}

//vi: ai nosi sw=4 ts=4 expandtab
