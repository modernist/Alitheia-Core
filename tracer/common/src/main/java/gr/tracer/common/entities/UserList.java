package gr.tracer.common.entities;

import java.util.ArrayList;

public class UserList {
	private java.util.ArrayList<User> _users = new ArrayList<User>();

	public boolean usernameExists(String aAAUsername) {
		throw new UnsupportedOperationException();
	}

	public boolean addUser(User aAAU) {
		throw new UnsupportedOperationException();
	}

	public LoginReturn loginAttempt(String aAAUsername, String aAAPassword) {
		throw new UnsupportedOperationException();
	}
}