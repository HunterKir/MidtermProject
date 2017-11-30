package data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.User;

public class UserDAOImpl implements UserDAO {

	@Override
	public User getUser(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		User user = null;
		try {
			user = em.find(User.class, id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		em.close();
		emf.close();
		return user;
	}

	@Override
	public User createUser(User user) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(user);
		em.flush();
		em.getTransaction().commit();
		em.close();
		emf.close();
		return user;
	}

	@Override
	public User updateUser(int id, User user) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		User userToUpdate = em.find(User.class, id);
		em.getTransaction().begin();

		userToUpdate.setAdmin(user.isAdmin());
		userToUpdate.setFirstName(user.getFirstName());
		userToUpdate.setLastName(user.getLastName());
		userToUpdate.setItemPosts(user.getItemPosts());
		userToUpdate.setPassword(user.getPassword());
		userToUpdate.setUsername(user.getUsername());

		em.getTransaction().commit();

		return userToUpdate;
	}

	@Override
	public User deleteUser(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		User user = em.find(User.class, id);

		if (user != null) {
			em.getTransaction().begin();
			em.remove(user);
			em.getTransaction().commit();
			return user;
		} else {
			return null;
		}
	}
	
}
