package data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Post;
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

	@Override
	public List<Post> getPostHistorybyUid(int uid) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		List<Post> posts = new ArrayList<>();
		String q = "SELECT p from Post p WHERE p.user.id=:uid";
		posts = em.createQuery(q,Post.class).setParameter("uid",uid).getResultList();
		
		return posts;
	}

	@Override
	public User getUserByUserName(String username) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		String query = "SELECT u from User u WHERE username LIKE :username";
		User user = null; 
		try {
			user = em.createQuery(query, User.class )
					.setParameter("username", username)
					.getResultList().get(0); 
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			em.close();
			emf.close();
		}
		return user;
	}
	
}
