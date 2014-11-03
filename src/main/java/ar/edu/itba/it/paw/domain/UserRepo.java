package ar.edu.itba.it.paw.domain;

public interface UserRepo {

	public User authenticate(String email, String password);

	public User getUser(String email);

	public void registerUser(User user);
	
	public User getUser(int id);
}
