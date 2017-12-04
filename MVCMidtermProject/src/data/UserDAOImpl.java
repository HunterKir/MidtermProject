package data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import entities.Community;
import entities.Item;
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
	@Override
	public User getUserByUserName(String username) {
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
		return user;
	}
	@Override
	public User getLoadedUser(String username) {
		//Needs to be Optimized totally inefficient
		String query = "SELECT u from User u WHERE username = :username"; 
		User user = null; 
		User managedUser = null; 
		try {
			user = em.createQuery(query, User.class )
					.setParameter("username", username)
					.getResultList().get(0); 
			managedUser = em.find(User.class, user.getId());
			
			for (Community c : managedUser.getCommunities()) {
				c.getItems().size(); 
			}
			managedUser.getItemsPosted().size(); 
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return managedUser;
	}
	@Override 
	public int getUserTotalPost(User user) {
		String query = "SELECT i FROM Item i WHERE i.user.id = :id"; 
		int totalPosts = 0; 
		List<Item> items = em.createQuery(query, Item.class)
				.setParameter("id", user.getId())
				.getResultList();
		totalPosts = items.size();
		return totalPosts; 
	}
	@Override
	public List<User> getUserbyFirstOrLastName(String first, String last, User user) {
		List<User> tempUsers = new ArrayList<>();
		List<User> finalUsers = new ArrayList<>();
		try{
		String q = "SELECT u FROM User u Where u.firstName LIKE :first OR u.lastName LIKE :last";
//		for (Community c : user.getCommunities()) {
//			tempUsers = em.createQuery(q,User.class).setParameter("first", "%"+first+"%").setParameter("last","%"+last+"%")
//					.setParameter("id",c.getId())
//					.getResultList();
//			for (User u : tempUsers) {
//				finalUsers.add(u);
//			}
//		}
					finalUsers = em.createQuery(q,User.class).setParameter("first", "%"+first+"%").setParameter("last","%"+last+"%")
				.getResultList();
		return finalUsers;
		} 
		catch (Exception e) {
		e.printStackTrace();
		}
		return finalUsers;
	}
}