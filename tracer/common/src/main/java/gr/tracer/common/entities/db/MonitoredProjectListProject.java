package gr.tracer.common.entities.db;

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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlElement;

import eu.sqooss.service.db.DAObject;
import eu.sqooss.service.db.StoredProject;

@Entity
@Table(name="MONITORED_PROJECT_LIST_PROJECT", uniqueConstraints=@UniqueConstraint(columnNames = {"MONITORED_PROJECT_LIST_ID", "PROJECT_ID"}))
/**
 * Associates a monitored project list to a project
 * @author circular
 */
public class MonitoredProjectListProject extends DAObject{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="MONITORED_PROJECT_LIST_PROJECT_ID")
	@XmlElement
	private long id;
	
	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}
	
	public MonitoredProjectList getMonitoredProjectList() {
		return monitoredProjectList;
	}

	public void setMonitoredProjectList(MonitoredProjectList monitoredProjectList) {
		this.monitoredProjectList = monitoredProjectList;
	}

	public StoredProject getProject() {
		return project;
	}

	public void setProject(StoredProject project) {
		this.project = project;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="MONITORED_PROJECT_LIST_ID", referencedColumnName="MONITORED_PROJECT_LIST_ID")
	private MonitoredProjectList monitoredProjectList;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="PROJECT_ID", referencedColumnName="PROJECT_ID")
	private StoredProject project;
	
	
	
}
