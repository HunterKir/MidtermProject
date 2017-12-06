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
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import entities.Category;
import entities.Community;
import entities.Item;
import entities.User;

@Repository
@Transactional
public class ItemDAOImpl implements ItemDAO {
	private String url = "jdbc:mysql://localhost:3306/swapmeetdb";
	private String user = "blossom";
	private String pass = "blossom";
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public Item createItem(Item item, User currentUser, int id, int categoryId) {
//		em.persist(item);
//		em.flush();
		Connection conn = null;
		String sql;
		String sql2;
		try {
			conn = DriverManager.getConnection(url, user, pass);
			conn.setAutoCommit(false); // Start transaction
			sql = "INSERT INTO item (title, price, content, user_id, community_id, active, category_id) VALUES (?, ?, ?, ?, ?, ?, ?)" ;
			
			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, item.getTitle());
			st.setDouble(2, item.getPrice());
			st.setString(3, item.getContent());
			st.setInt(4, currentUser.getId());
			st.setInt(5, id);
			st.setInt(6, 1);
			st.setInt(7, categoryId);
			st.executeUpdate();
			
			ResultSet keys = st.getGeneratedKeys();
			int id2 = 0;
			if (keys.next()) {
				id2 = keys.getInt(1);
			}
			
//			item.setUser(currentUser);
			item.setId(id2);
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
		return item;
	}
	@Override
	public Item getItem(int id) {
		Item item = null;
		// should change this to be more efficient
		item = em.find(Item.class, id);
		item.getPosts().size();
//		String q = "SELECT ii FROM Item ii JOIN FETCH ii.posts WHERE ii.id = :id";
//		item = em.createQuery(q, Item.class).setParameter("id", id).getResultList().get(0);
		return item;
	}

	@Override
	public Item deleteItem(int id) {
		Item item = em.find(Item.class, id);

		if (item != null) {
			item.setActive(false);
		}
		return item;
	}

	@Override
	public Item updateItem(int id, Item item) {
		Item itemToUpdate = em.find(Item.class, id);
		itemToUpdate.setCategory(item.getCategory());
		itemToUpdate.setContent(item.getContent());
		itemToUpdate.setPrice(item.getPrice());
		itemToUpdate.setTitle(item.getTitle());
		
		itemToUpdate.getPosts().size(); 

		return itemToUpdate;
	}
	@Override
	public List<Item> getItembyCatID(int id) {
		List<Item> items = new ArrayList<>();
		try {
			String q = "SELECT ii from Item ii WHERE ii.category.id =:cid";
			items = em.createQuery(q, Item.class).setParameter("cid", id).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}

	@Override
	public List<Item> getItembyPrice(double min, double max) {

		List<Item> items = new ArrayList<>();
		try {
			String q = "SELECT ii from Item ii WHERE ii.price BETWEEN :min AND :max";
			items = em.createQuery(q, Item.class).setParameter("min", min).setParameter("max", max).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}

	@Override
	public List<Item> getAllItemsInAllCommunitiesByDescription(String kw, User user) {
		Connection conn = null;
		String sql;
		List<Item> itemList = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(url, this.user, pass);
			conn.setAutoCommit(false); // Start transaction
			sql =  "SELECT DISTINCT i.id, i.user_id, i.content, i.post_time, i.category_id, i.price, i.title, \n" + 
					" i.community_id, i.active, i.sold " + 
					" FROM item i " + 
					" JOIN user_community uc ON i.community_id = uc.community_id " + 
					" JOIN user u ON i.user_id = u.id " + 
					" WHERE ( "; 
			
			user = em.find(User.class, user.getId()); 
			int count =0; 
			for (Community c : user.getCommunities()) {
				if(count == user.getCommunities().size()-1) {
					sql += " uc.community_id =" + c.getId() + " "; 
					sql += ") AND (i.title LIKE ? OR i.content LIKE ?) ORDER BY i.post_time DESC "; 
					break; 
				}
				else
				{
					sql += " uc.community_id =" + c.getId() + " OR "; 
					count++; 
				}
			}

			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, "%" + kw + "%");
			st.setString(2, "%" + kw + "%");
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
	public List<Item> getAllItemsInAllCommunitiesByUser(User user) {
		Connection conn = null;
		String sql;
		List<Item> itemList = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(url, this.user, pass);
			conn.setAutoCommit(false); // Start transaction
			sql =  "SELECT DISTINCT i.id, i.user_id, i.content, i.post_time, i.category_id, i.price, i.title, \n" + 
					" i.community_id, i.active, i.sold " + 
					" FROM item i " + 
					" JOIN user_community uc ON i.community_id = uc.community_id " + 
					" JOIN user u ON i.user_id = u.id " + 
					" WHERE "; 
			
			user = em.find(User.class, user.getId()); 
			int count =0; 
			for (Community c : user.getCommunities()) {
				if(count == user.getCommunities().size()-1) {
					sql += " uc.community_id =" + c.getId() + " "; 
					sql += " ORDER BY i.post_time DESC "; 
					break; 
				}
				else
				{
					sql += " uc.community_id =" + c.getId() + " OR "; 
					count++; 
				}
			}
			
			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
	public List<Item> getAllItemsInAllCommunitiesByUserLimit10(User user) {
		Connection conn = null;
		String sql;
		List<Item> itemList = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(url, this.user, pass);
			conn.setAutoCommit(false); // Start transaction
			sql =  "SELECT DISTINCT i.id, i.user_id, i.content, i.post_time, i.category_id, i.price, i.title, \n" + 
					" i.community_id, i.active, i.sold " + 
					" FROM item i " + 
					" JOIN user_community uc ON i.community_id = uc.community_id " + 
					" JOIN user u ON i.user_id = u.id " + 
					" WHERE "; 
			
			user = em.find(User.class, user.getId()); 
			int count =0; 
			for (Community c : user.getCommunities()) {
				if(count == user.getCommunities().size()-1) {
					sql += " uc.community_id =" + c.getId() + " "; 
					sql += " ORDER BY i.post_time DESC LIMIT 10"; 
					break; 
				}
				else
				{
					sql += " uc.community_id =" + c.getId() + " OR "; 
					count++; 
				}
			}
			
			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
	public Item changeActiveStatus(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Item itemToUpdate = em.find(Item.class, id);
		if (itemToUpdate.getActive() == true) {
			itemToUpdate.setActive(false);
		} else {
			itemToUpdate.setActive(true);
		}
		em.getTransaction().commit();
		return itemToUpdate;
	}
	
	@Override
	public Item changeSoldStatus(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Item itemToUpdate = em.find(Item.class, id);
		if (itemToUpdate.isSold() == false) {
			itemToUpdate.setSold(true);
			itemToUpdate.setActive(false);
		} else {
			itemToUpdate.setSold(false);
			itemToUpdate.setActive(true);
		}
		
		em.getTransaction().commit();
		return itemToUpdate;
	}
}
