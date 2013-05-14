package gr.tracer.common.entities.db;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import eu.sqooss.service.db.DAObject;
import eu.sqooss.service.db.StoredProject;
import eu.sqooss.service.db.User;

@XmlRootElement(name="monitoredProjectList")
@Entity
@Table(name="MONITORED_PROJECT_LIST")
/**
 * Represents a list of monitored projects owned by a user
 * @author circular
 */
public class MonitoredProjectList extends DAObject {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="MONITORED_PROJECT_LIST_ID")
	@XmlElement
	private long id;
	
	@XmlElement
	@Column(name="MONITORED_PROJECT_LIST_NAME", nullable=false, unique=true)
	private String name;
	
	@XmlElement
	@Column(name="MONITORED_PROJECT_LIST_DESCRIPTION", nullable=false)
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
	
	/**
     * The User owning this list
     */
	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="USER_ID", referencedColumnName="USER_ID")
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToMany(mappedBy="monitoredProjectList", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<MonitoredProjectListProject> projects = new HashSet<MonitoredProjectListProject>();
	
	/**
     * Get a set of distinct Stored Projects that should be contained in the list
     * @return Set of Stored Projects included in the monitoring list
     */
	public Set<MonitoredProjectListProject> getProjects(){
		return this.projects;
	}
	
	public void setProjects(Set<MonitoredProjectListProject> projects){
		this.projects = projects;
	}
	
	/**
	 * The security profile associated to the monitored project list
	 */	
	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="SECURITY_PROFILE_ID", referencedColumnName="SECURITY_PROFILE_ID")
	private SecurityProfile securityProfile;
	
	public SecurityProfile getSecurityProfile() {
		return securityProfile;
	}

	public void setSecurityProfile(SecurityProfile securityProfile) {
		this.securityProfile = securityProfile;
	}
	
	public boolean addProject(StoredProject project) {
		if(projects != null) {
			MonitoredProjectListProject p = new MonitoredProjectListProject(this, project);
			return projects.add(p);
		}
		return false;
	}
	
	public boolean removeProject(StoredProject project) {
		if(projects != null) {
			MonitoredProjectListProject p = new MonitoredProjectListProject(this, project);
			return projects.remove(p);
		}
		return false;
	}
	
	public boolean containsProject(StoredProject project) {
		if(projects != null) {
			for(MonitoredProjectListProject p : projects) {
				if(p.getProject().equals(project))
					return true;
			}
		}
		return false;
	}
	
	public MonitoredProjectListProject getProject(StoredProject project) {
		if(projects != null) {
			for(MonitoredProjectListProject p : projects) {
				if(p.getProject().equals(project))
					return p;
			}
		}
		return null;
	}
	
	public boolean addProject(MonitoredProjectListProject p) {
		if(projects != null) {
			p.setMonitoredProjectList(this);
			return projects.add(p);
		}
		return false;
	}
	
	public boolean removeProject(MonitoredProjectListProject p) {
		if(projects != null) {
			p.setMonitoredProjectList(this);
			return projects.remove(p);
		}
		return false;
	}
	
}
