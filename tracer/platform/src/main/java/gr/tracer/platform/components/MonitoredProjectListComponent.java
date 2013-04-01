package gr.tracer.platform.components;

import java.util.List;

import gr.tracer.common.entities.db.MonitoredProjectList;
import gr.tracer.platform.TracerComponent;

public interface MonitoredProjectListComponent extends TracerComponent {
	
	public List<MonitoredProjectList> getMonitoredProjectList();

	public MonitoredProjectList getMonitoredProjectList(int mplId);
		
	public MonitoredProjectList createMonitoredProjectList(String mplName, String mplDescription, String userName);

	public MonitoredProjectList searchMonitoredProjectList(String mplName);
	
	public boolean setSecurityProfileToList(String spName, String mplName);
	
	public boolean addProjectFromMonitoredProjectList(String monProjList, String projName);
	
	public boolean removeProjectFromMonitoredProjectList(String monProjList, String projName);

}
