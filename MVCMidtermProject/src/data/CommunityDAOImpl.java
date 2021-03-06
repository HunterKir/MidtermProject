package data;

import static org.hamcrest.CoreMatchers.containsString;

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
		Community c = new Community();
		try {
			c = em.find(Community.class, id);
			c.getItems().size();
			c.getMembers().size();
			c.getRatings().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	@Override
	public Community createCommunity(Community community, User owner) {
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
			String sql4 = "INSERT INTO user_rating (community_id, user_id, rating) VALUES(?, ?,5.0)"; 
			
			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			PreparedStatement st2 = conn.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
			PreparedStatement st3 = conn.prepareStatement(sql3, Statement.RETURN_GENERATED_KEYS);
			PreparedStatement st4 = conn.prepareStatement(sql4, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, community.getName());
			st.setInt(2, owner.getId());
			st.setString(3, community.getDescription());
			st.executeUpdate();

			ResultSet keys = st.getGeneratedKeys();
			int comId = 0;
			if (keys.next()) {
				comId = keys.getInt(1);
			}
			community.setId(comId);
			st2.setInt(1, owner.getId());
			st2.setInt(2, comId);
			st2.executeUpdate();

			st3.setInt(1, owner.getId());
			st3.setInt(2, comId);
			st3.executeUpdate();

			st4.setInt(1, comId); 
			st4.setInt(2, owner.getId()); 
			st4.executeUpdate(); 
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
		List<Community> communities = null;
		try {
			String q = "SELECT DISTINCT c from Community c JOIN FETCH c.members WHERE c.id != 1";
			communities = em.createQuery(q, Community.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return communities;
	}

	@Override
	public List<Community> getAllCommunitiesWithouUserCommunities(int userId) {
		List<Community> communities = new ArrayList<>();
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, this.user, pass);
			conn.setAutoCommit(false); // Start transaction
			User u = em.find(User.class, userId);
			String q; 
			if(u.getCommunities().size() == 0) {
				q = " SELECT DISTINCT c.id, c.name, c.owner_id, c.description"
						+ " FROM community c "
						+ " JOIN user_community uc ON uc.community_id = c.id "
						+ " WHERE uc.community_id != 1 "; 
			}
			else
			{
				 q = "SELECT DISTINCT c.id, c.name, c.owner_id, c.description FROM community c "
						+ " JOIN user_community uc ON uc.community_id = c.id" + " WHERE ( uc.community_id != 1 ";
				int count = 1;
				for (Community c : u.getCommunities()) {
					if (count == u.getCommunities().size()) {
						q += " AND uc.community_id !=" + c.getId() + " )";
						break;
					} else {
						q += " AND uc.community_id != " + c.getId();
						count++;
					}
				}
				
			}

			PreparedStatement st = conn.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);

			ResultSet rs = st.executeQuery();
			Community c;
			while (rs.next()) {
				if (rs.getInt(1) == 1) {
					continue;
				} else {
					c = em.find(Community.class, rs.getInt(1));
					c.getMembers().size();
					communities.add(c);

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
					+ " i.community_id, i.active, i.sold FROM item i" + " JOIN community c ON c.id = i.community_id"
					+ " WHERE c.id = ? AND i.price >= ? AND i.price <= ? ORDER BY i.price";

			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, groupId);
			st.setInt(2, min);
			st.setInt(3, max);

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
			sql = "SELECT i.id, i.user_id, i.content, i.post_time, i.category_id, i.price, i.title, "
					+ "					i.community_id, i.active, i.sold FROM item i "
					+ "					JOIN community c ON c.id = i.community_id "
					+ "					WHERE i.category_id = ? AND i.community_id = ? ORDER BY i.post_time DESC";

			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, catId);
			st.setInt(2, groupId);
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
	public List<Category> getCategories() {
		String query = "SELECT c FROM Category c";
		List<Category> categories = em.createQuery(query, Category.class).getResultList();
		return categories;
	}

	@Override
	public User addUsertoCommunity(User user1, int cid) {
		Connection conn = null;
		String sql;
		try {
			conn = DriverManager.getConnection(url, user, pass);
			conn.setAutoCommit(false); // Start transaction
			sql = "INSERT INTO user_community (user_id,community_id) VALUES(?,?)";
			String SQL2 = "INSERT INTO user_rating (community_id, user_id, rating) VALUES(?, ?,5.0)"; 
			
			//Statement 1
			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, user1.getId());
			st.setInt(2, cid);
			st.executeUpdate();
			
			//Statement 2
			PreparedStatement st2 = conn.prepareStatement(SQL2, Statement.RETURN_GENERATED_KEYS); 
			st2.setInt(1,  cid);
			st2.setInt(2, user1.getId());
			st2.executeUpdate();
			

			ResultSet keys = st.getGeneratedKeys();
			int id = 0;
			if (keys.next()) {
				id = keys.getInt(1);
			}
			conn.commit();

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
}
