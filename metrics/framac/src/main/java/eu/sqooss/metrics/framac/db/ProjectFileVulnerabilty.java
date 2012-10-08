package eu.sqooss.metrics.framac.db;

import eu.sqooss.service.db.DAObject;
import eu.sqooss.service.db.ProjectFile;

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
 * a specific project file, as stored in the database
 */
@Entity
@Table(name="PROJECT_FILE_VULNERABILITY")
@XmlRootElement(name="file-vulnerability")
public class ProjectFileVulnerabilty extends Vulnerability {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PROJECT_FILE_VULNERABILITY_ID")
    @XmlElement(name = "id")
	private long id;
	
	/**
     * The type of this vulnerability instance
     */
    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="VULNERABILITY_TYPE_ID", referencedColumnName="VULNERABILITY_TYPE_ID")
	private VulnerabilityType vulnerabilityType;
	
    /**
     * The ProjectFile to which this vulnerability relates
     */
	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="PROJECT_FILE_ID", referencedColumnName="PROJECT_FILE_ID")
	private ProjectFile projectFile;
	
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
	
	public void setProjectFile(ProjectFile pf) {
		this.projectFile = pf;
	}

	public ProjectFile getProjectFile() {
		return projectFile;
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
		return getProjectFile();
	}

	@Override
	public void setAssociatedEntity(DAObject o) {
		if(o instanceof ProjectFile)
			setProjectFile((ProjectFile)o);
	}
	
	public ProjectFileVulnerabilty(ProjectFile pf, VulnerabilityType vt,
			String location, String description) {
		this.projectFile = pf;
		this.vulnerabilityType = vt;
		this.location = location;
		this.description = description;
	}
	
}

//vi: ai nosi sw=4 ts=4 expandtab
