package gr.tracer.platform.controllers;

import java.util.ArrayList;

import gr.tracer.common.entities.ObservedProjectList;
import gr.tracer.common.entities.Project;

public class ObservedProjectListController {

	private ObservedProjectList opl;

	public ArrayList<ObservedProjectList> getExistingObservedProjectLists() {
		throw new UnsupportedOperationException();
	}

	public boolean getSelectedObservedProjectList(int aAListId, String aAJarProjectPath, String aASrcProjectPath, String aAProj_name) {
		throw new UnsupportedOperationException();
	}

	public ObservedProjectList getObservedProjectList(int aAListId) {
		throw new UnsupportedOperationException();
	}

	public void createObservedProjectList(String aAList_name) {
		throw new UnsupportedOperationException();
	}

	public boolean setSecurityProfileToList(int aAProfile_index) {
		throw new UnsupportedOperationException();
	}

	public boolean searchObservedProjectList(String aAList_name) {
		throw new UnsupportedOperationException();
	}

	public void detectVulnerabilitiesOnObservedProjectList(int aAListId) {
		throw new UnsupportedOperationException();
	}

	public ArrayList<ObservedProjectList> getExistingObservedFileLists() {
		throw new UnsupportedOperationException();
	}

	public ArrayList<Project> getAvailableProjectFromObservedProjectList(int aAListId) {
		throw new UnsupportedOperationException();
	}

	public boolean removeProjectFromObservedProjectList(int aAProj_index) {
		throw new UnsupportedOperationException();
	}
}