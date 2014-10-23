package ar.edu.itba.it.paw.domain;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateUserRepo extends AbstractHibernateRepo implements
		UserRepo {

	@Autowired
	public HibernateUserRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public User authenticate(String username, String password) {
		List<User> result = find(
				" from User where username = ? and password = ?", username,
				password);
		return result.size() > 0 ? result.get(0) : null;
	}

	@Override
	public User getUser(String username) {
		List<User> result = find(" from User where username = ?", username);
		return result.size() > 0 ? result.get(0) : null;
	}

	@Override
	public List<User> getUsersWithName(String name) {
		return find(" from User where username like '%" + name
				+ "%' or (name like '%" + name + "%') or (surname like '%"
				+ name + "%')");
	}

	@Override
	public void registerUser(User user) {
		super.save(user);
	}

	@Override
	public User getUser(int id) {
		return super.get(User.class, id);
	}
}
