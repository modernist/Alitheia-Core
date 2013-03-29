package gr.tracer.platform.controllers;

import gr.tracer.common.entities.db.MonitoredProjectList;
import gr.tracer.platform.components.MonitoredProjectListComponent;

public class MonitoredProjectListController {

	private MonitoredProjectListComponent mplc;
	
	public MonitoredProjectListController(MonitoredProjectListComponent mplc){
		this.mplc = mplc;
	}

	public MonitoredProjectList getMonitoredProjectList(int mplId) {
		return mplc.getMonitoredProjectList(mplId);
	}

	public MonitoredProjectList createMonitoredProjectList(String mplName, String mplDescription, String userName) {
		return mplc.createMonitoredProjectList(mplName, mplDescription, userName);
	}

	public boolean setSecurityProfileToList(int aAProfile_index, int mplId) {
		return mplc.setSecurityProfileToList(aAProfile_index, mplId);
	}

	public MonitoredProjectList searchMonitoredProjectList(String mplName) {
		return mplc.searchMonitoredProjectList(mplName);
	}

	public boolean addProjectFromMonitoredProjectList(String monProjList, String projName) {
		return mplc.addProjectFromMonitoredProjectList(monProjList, projName);
	}
	
	public boolean removeProjectFromMonitoredProjectList(String monProjList, String projName) {
		return mplc.removeProjectFromMonitoredProjectList(monProjList, projName);
	}
}