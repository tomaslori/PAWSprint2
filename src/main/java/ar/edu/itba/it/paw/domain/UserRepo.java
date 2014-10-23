package ar.edu.itba.it.paw.domain;

import java.util.List;

public interface UserRepo {

	public User authenticate(String username, String password);

	public User getUser(String username);

	public List<User> getUsersWithName(String name);

	public void registerUser(User user);
	
	public User getUser(int id);
}
