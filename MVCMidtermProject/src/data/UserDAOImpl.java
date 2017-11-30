package data;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import entities.User;
@Repository
@Transactional
public class UserDAOImpl implements UserDAO {
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public User getUser(int id) {
		User user = null;
		try {
			user = em.find(User.class, id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User createUser(User user) {

		em.persist(user);
		em.flush();
		return user;
	}

	@Override
	public User updateUser(int id, User user) {

		User userToUpdate = em.find(User.class, id);

		userToUpdate.setAdmin(user.isAdmin());
		userToUpdate.setFirstName(user.getFirstName());
		userToUpdate.setLastName(user.getLastName());
		userToUpdate.setItemPosts(user.getItemPosts());
		userToUpdate.setPassword(user.getPassword());
		userToUpdate.setUsername(user.getUsername());

		return userToUpdate;
	}

	@Override
	public User deleteUser(int id) {

		User user = em.find(User.class, id);

		if (user != null) {
			em.remove(user);
			return user;
		} else {
			return null;
		}
	}

}