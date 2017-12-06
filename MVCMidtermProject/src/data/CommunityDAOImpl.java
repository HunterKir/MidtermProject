package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import entities.Category;
import entities.Community;
import entities.Item;
import entities.User;

@Repository
@Transactional
public class CommunityDAOImpl implements CommunityDAO {

	private String url = "jdbc:mysql://localhost:3306/swapmeetdb";
	private String user = "blossom";
	private String pass = "blossom";
	@PersistenceContext
	private EntityManager em;

	@Override
	public Community getCommunity(int id) {
		Community community = null;
		try {
			String q = "SELECT c from Community c JOIN FETCH c.items WHERE c.id = :id";
			community = em.createQuery(q, Community.class).setParameter("id", id).getResultList().get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return community;
	}

	@Override
	public Community createCommunity(Community community, User owner) {
		// try {
		// em.persist(owner);
		// community.setOwner(owner);
		// em.persist(community);
		// em.flush();
		// }
		// catch(Exception e) {
		// e.printStackTrace();
		// return null;
		// }
		Connection conn = null;
		String sql;
		String sql2;
		String sql3;
		try {
			conn = DriverManager.getConnection(url, user, pass);
			conn.setAutoCommit(false); // Start transaction
			sql = "INSERT INTO community (name, owner_id, description) VALUES (?, ?, ?)";
			sql2 = "INSERT INTO user_community (user_id, community_id) VALUES (?, ?)";
			sql3 = "INSERT INTO item (user_id, content, title, community_id, active) VALUES (?, \"Dummy\", \"Dummy\", ?, 0)";

			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			PreparedStatement st2 = conn.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
			PreparedStatement st3 = conn.prepareStatement(sql3, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, community.getName());
			st.setInt(2, owner.getId());
			st.setString(3, community.getDescription());
			st.executeUpdate();

			ResultSet keys = st.getGeneratedKeys();
			int id = 0;
			if (keys.next()) {
				id = keys.getInt(1);
			}
			community.setId(id);
			st2.setInt(1, owner.getId());
			st2.setInt(2, id);
			st2.executeUpdate();

			st3.setInt(1, owner.getId());
			st3.setInt(2, id);
			st3.executeUpdate();

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
		return community;

	}

	@Override
	public Community updateCommunityName(int id, Community community) {
		Community managed = em.find(Community.class, id);
		managed.setName(community.getName());
		managed.setDescription(community.getDescription());
		return managed;
	}

	@Override
	public Community deleteCommunity(int id) {
		Community managed = em.find(Community.class, id);
		// em.remove(managed);
		// if (em.find(Community.class, id) == null) {
		// System.out.println("true");
		// return managed;
		// }
		// else {
		// System.out.println("false");
		// }
		Connection conn = null;
		String sql;
		String sql2;
		String sql3;
		try {
			conn = DriverManager.getConnection(url, user, pass);
			conn.setAutoCommit(false); // Start transaction
			sql = "DELETE FROM community WHERE id = ?";
			// sql2 = "DELETE FROM item WHERE community_id = ?";
			sql2 = "UPDATE item SET community_id = 1 WHERE community_id = ?";
			sql3 = "DELETE FROM user_community WHERE community_id = ?";

			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			PreparedStatement st2 = conn.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
			PreparedStatement st3 = conn.prepareStatement(sql3, Statement.RETURN_GENERATED_KEYS);

			st3.setInt(1, managed.getId());
			st3.executeUpdate();

			st2.setInt(1, managed.getId());
			st2.executeUpdate();

			st.setInt(1, managed.getId());
			st.executeUpdate();

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
		return managed;
	}

	@Override
	public List<Item> getItems(int id) {
		String query = "SELECT i from Item I WHERE i.Community = :communityId ";
		List<Item> items = em.createQuery(query, Item.class).setParameter("communityId", id).getResultList();
		return items;
	}

	@Override
	public List<User> getUsers(int id) {
		String query = "SELECT c FROM Community c JOIN FETCH c.members WHERE c.id = :id ";
		List<Community> community = em.createQuery(query, Community.class).setParameter("id", id).getResultList();
		List<User> userList = community.get(0).getMembers();
		return userList;
	}

	@Override
	public List<Community> getAllCommunities() {
		List<Community> communities = new ArrayList<>();
		try {
			String q = "SELECT c from Community c";
			communities = em.createQuery(q, Community.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return communities;
	}

	@Override
	public List<Item> getItembyDescription(String descrip, User user, int groupId) {
		Connection conn = null;
		String sql;
		List<Item> itemList = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(url, this.user, pass);
			conn.setAutoCommit(false); // Start transaction
			sql = "SELECT i.id, i.user_id, i.content, i.post_time, i.category_id, i.price, i.title, "
					+ " i.community_id, i.active, i.sold FROM item i " + " JOIN community c ON c.id = i.community_id"
					+ " 	WHERE c.id = ? AND i.content LIKE ? OR i.title LIKE ? ORDER BY i.title";

			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, groupId);
			st.setString(2, "%" + descrip + "%");
			st.setString(3, "%" + descrip + "%");
			ResultSet rs = st.executeQuery();
			Item i;
			while (rs.next()) {
				if (rs.getBoolean(9) == true) {
					i = new Item();
					int id = rs.getInt(1);
					i.setId(id);
					User itemUser = em.find(User.class, rs.getInt(2));
					i.setUser(itemUser);
					String content = rs.getString(3);
					i.setContent(content);
					LocalDateTime time = rs.getTimestamp(4).toLocalDateTime();
					i.setPostTime(time);
					Category itemCategory = em.find(Category.class, rs.getInt(5));
					i.setCategory(itemCategory);
					double price = rs.getDouble(6);
					i.setPrice(price);
					String title = rs.getString(7);
					i.setTitle(title);
					Community community = em.find(Community.class, rs.getInt(8));
					i.setCommunity(community);
					boolean active = rs.getBoolean(9);
					i.setActive(active);
					boolean sold = rs.getBoolean(10);
					i.setSold(sold);
					itemList.add(i);
				} else {
					continue;
				}
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
		return itemList;
	}
	@Override
	public List<Item> getAllItemsInCommunity(int groupId) {
		Connection conn = null;
		String sql;
		List<Item> itemList = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(url, this.user, pass);
			conn.setAutoCommit(false); // Start transaction
			sql = "SELECT i.id, i.user_id, i.content, i.post_time, i.category_id, i.price, i.title, "
					+ " i.community_id, i.active, i.sold FROM item i " + " JOIN community c ON c.id = i.community_id"
					+ " 	WHERE c.id = ? ORDER BY i.post_time DESC";
			
			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, groupId);
			ResultSet rs = st.executeQuery();
			Item i;
			while (rs.next()) {
				if (rs.getBoolean(9) == true) {
					i = new Item();
					int id = rs.getInt(1);
					i.setId(id);
					User itemUser = em.find(User.class, rs.getInt(2));
					i.setUser(itemUser);
					String content = rs.getString(3);
					i.setContent(content);
					LocalDateTime time = rs.getTimestamp(4).toLocalDateTime();
					i.setPostTime(time);
					Category itemCategory = em.find(Category.class, rs.getInt(5));
					i.setCategory(itemCategory);
					double price = rs.getDouble(6);
					i.setPrice(price);
					String title = rs.getString(7);
					i.setTitle(title);
					Community community = em.find(Community.class, rs.getInt(8));
					i.setCommunity(community);
					boolean active = rs.getBoolean(9);
					i.setActive(active);
					boolean sold = rs.getBoolean(10);
					i.setSold(sold);
					itemList.add(i);
				} else {
					continue;
				}
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
		return itemList;
	}
	@Override
	public List<Item> getAllItemsInCommunityByRange(int groupId, int min, int max) {
		Connection conn = null;
		String sql;
		List<Item> itemList = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(url, this.user, pass);
			conn.setAutoCommit(false); // Start transaction
			sql = "SELECT i.id, i.user_id, i.content, i.post_time, i.category_id, i.price, i.title,"
					+ " i.community_id, i.active, i.sold FROM item i"
                    + " JOIN community c ON c.id = i.community_id"
					+ " WHERE c.id = ? AND i.price >= ? AND i.price <= ? ORDER BY i.price";
			
			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			st.setInt(1, groupId);
			st.setInt(2,  min);
			st.setInt(3,  max);
			
			ResultSet rs = st.executeQuery();
			Item i;
			while (rs.next()) {
				if (rs.getBoolean(9) == true) {
					i = new Item();
					int id = rs.getInt(1);
					i.setId(id);
					User itemUser = em.find(User.class, rs.getInt(2));
					i.setUser(itemUser);
					String content = rs.getString(3);
					i.setContent(content);
					LocalDateTime time = rs.getTimestamp(4).toLocalDateTime();
					i.setPostTime(time);
					Category itemCategory = em.find(Category.class, rs.getInt(5));
					i.setCategory(itemCategory);
					double price = rs.getDouble(6);
					i.setPrice(price);
					String title = rs.getString(7);
					i.setTitle(title);
					Community community = em.find(Community.class, rs.getInt(8));
					i.setCommunity(community);
					boolean active = rs.getBoolean(9);
					i.setActive(active);
					boolean sold = rs.getBoolean(10);
					i.setSold(sold);
					itemList.add(i);
				} else {
					continue;
				}
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
		return itemList;
	}

	@Override
	public List<User> getUserbyFirstOrLastName(String first, String last, User user, int groupId) {
		Connection conn = null;
		String sql;
		List<User> userList = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(url, this.user, pass);
			conn.setAutoCommit(false); // Start transaction
			sql = "SELECT u.id, u.first_name, u.last_name, u.username " + " FROM user u"
					+ " JOIN user_community uc ON uc.user_id = u.id "
					+ " 	WHERE (uc.community_id = ?) AND (u.first_name LIKE ? OR u.last_name LIKE ? OR u.username Like ?) ORDER BY u.first_name ";

			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, groupId);
			st.setString(2, "%" + first + "%");
			st.setString(3, "%" + last + "%");
			st.setString(4, "%" + first + "%");
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

	@Override
	public List<Item> getItembyCatID(int catId, int groupId) {
		Connection conn = null;
		String sql;
		List<Item> itemList = new ArrayList<>(); 
		try {
			conn = DriverManager.getConnection(url, this.user, pass);
			conn.setAutoCommit(false); // Start transaction
			sql = "SELECT i.id, i.user_id, i.content, i.post_time, i.category_id, i.price, i.title, " + 
					"					i.community_id, i.active, i.sold FROM item i " + 
					"					JOIN community c ON c.id = i.community_id " + 
					"					WHERE i.category_id = ? AND i.community_id = ? ORDER BY i.post_time DESC" ;
			
			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, catId);
			st.setInt(2,  groupId);
			ResultSet rs = st.executeQuery();
			Item i; 
			while(rs.next()) {
				if(rs.getBoolean(9) == true) {
					i = new Item(); 
					int id = rs.getInt(1);
					i.setId(id); 
					User itemUser = em.find(User.class, rs.getInt(2)); 
					i.setUser(itemUser);
					String content = rs.getString(3); 
					i.setContent(content);
					LocalDateTime time = rs.getTimestamp(4).toLocalDateTime(); 
					i.setPostTime(time);
					Category itemCategory = em.find(Category.class, rs.getInt(5)); 
					i.setCategory(itemCategory);
					double price = rs.getDouble(6); 
					i.setPrice(price);
					String title = rs.getString(7); 
					i.setTitle(title);
					Community community = em.find(Community.class, rs.getInt(8)); 
					i.setCommunity(community);
					boolean active = rs.getBoolean(9); 
					i.setActive(active);
					boolean sold = rs.getBoolean(10);
					i.setSold(sold);	
					itemList.add(i); 
				}
				else {
					continue;
				}
 
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
		return itemList;
	}
	@Override
	public List<Category> getCategories() {
		String query = "SELECT c FROM Category c";
		List<Category> categories = em.createQuery(query, Category.class).getResultList();
		return categories;
	}
}
