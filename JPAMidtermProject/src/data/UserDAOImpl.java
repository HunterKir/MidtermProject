package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Community;
import entities.Post;
import entities.User;

public class UserDAOImpl implements UserDAO {

	private String url = "jdbc:mysql://localhost:3306/swapmeetdb";
	private String user = "blossom";
	private String pass = "blossom";

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

		userToUpdate.setFirstName(user.getFirstName());
		userToUpdate.setLastName(user.getLastName());
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
	public User getUserByUserName(String username) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		String query = "SELECT u from User u JOIN FETCH u.communities WHERE u.username LIKE :username";
		User user = null;
		try {
			user = em.createQuery(query, User.class).setParameter("username", "%"+username+"%").getResultList().get(0);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
			emf.close();
		}
		return user;
	}

	public List<User> getUserbyFirstOrLastName(String first, String last) {
		List<User> users = new ArrayList<>();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		try {
			String q = "SELECT u from User u Where u.firstName LIKE :first OR u.lastName LIKE :last";
			users = em.createQuery(q, User.class).setParameter("first", "%" + first + "%")
					.setParameter("last", "%" + last + "%").getResultList();
			return users;
		} catch (Exception e) {
			e.printStackTrace();
		}
		emf.close();
		em.close();
		return users;
	}

	@Override
	public User removeUserfromCommunityByCid(User user1, int cid) {
		Connection conn = null;
		String sql;
		try {
			conn = DriverManager.getConnection(url, user, pass);
			conn.setAutoCommit(false); // Start transaction
			sql = "DELETE from user_community WHERE user_id=? AND community_id=?";
			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, user1.getId());
			st.setInt(2, cid);
			st.executeUpdate();

			ResultSet keys = st.getGeneratedKeys();
			int id = 0;
			if (keys.next()) {
				id = keys.getInt(1);
			}
			conn.commit();
			List<Community> communities = user1.getCommunities();
			Iterator<Community> i = communities.iterator();
			while (i.hasNext()) {
			   Community c = i.next();
			  if(c.getId() == cid) { 
			  
			    i.remove();
			    user1.setCommunities(communities);
			}
		}
			} catch (SQLException e) {
			// Something went wrong.
			System.err.println("Error during inserts.");
			// e.printStackTrace();
			System.err.println("SQL Error: " + e.getErrorCode() + ": " + e.getMessage());
			System.err.println("SQL State: " + e.getSQLState());
			// Need to rollback, which also throws SQLException.
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					System.err.println("Error rolling back.");
					e1.printStackTrace();
				}
			}
		}
		
		return user1;
	}

	@Override
	public User plusProfileViewCount(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		User userToUpdate = em.find(User.class, id);
		int count = (userToUpdate.getProfileViews())+1;
		userToUpdate.setProfileViews(count);
		em.getTransaction().commit();
		System.out.println("you added a view");
		return userToUpdate;
	}
}