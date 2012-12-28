package gr.tracer.common.entities;

public abstract class User {
	protected String _username;
	protected String _passward;
	protected String _name;
	protected String _email;

	public boolean isUsername(String aAAUsername) {
		throw new UnsupportedOperationException();
	}

	public LoginReturn isUser(String aAAUsername, String aAAPassword) {
		throw new UnsupportedOperationException();
	}
}