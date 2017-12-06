package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	private String url = "jdbc:mysql://localhost:3306/swapmeetdb";
	private String user = "blossom";
	private String pass = "blossom";
	
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
		Connection conn = null;
		String sql;
		List<User> userList = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(url, this.user, pass);
			conn.setAutoCommit(false); // Start transaction
			sql = "SELECT DISTINCT u.id, u.first_name , u.last_name, u.username " + 
					" FROM user u " + 
					" JOIN user_community uc ON uc.user_id = u.id " + 
					" JOIN community c ON uc.community_id = c.id " + 
					" WHERE ";
			int count = 0; 
			user = em.find(User.class, user.getId()); 
			for (Community c : user.getCommunities()) {
				if(count == user.getCommunities().size()-1) {
					sql += " c.id=" + c.getId() + " "; 
					sql += " ORDER BY u.first_name DESC "; 
					break; 
				}
				else
				{
					sql += " c.id =" + c.getId() + " OR "; 
					count++; 
				}
			}
			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = st.executeQuery();
			User u;
			while (rs.next()) {
				u = new User();
				int id = rs.getInt(1);
				u.setId(id);
				String fname = rs.getString(2);
				u.setFirstName(fname);
				String lname = rs.getString(3);
				u.setLastName(lname);
				String uname = rs.getString(4);
				u.setUsername(uname);
				userList.add(u);
			}
			conn.commit(); // Commit the transaction

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
		return userList;
	}
}