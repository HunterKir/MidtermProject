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
import javax.persistence.PersistenceContext;

import org.junit.experimental.categories.Categories;
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
//		try {
//			em.persist(owner);
//			community.setOwner(owner);
//			em.persist(community);
//			em.flush();
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//			return null; 
//		}
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
		managed.setOwner(community.getOwner());
		return managed;
	}

	@Override
	public Community deleteCommunity(int id) {
		Community managed = em.find(Community.class, id);
		em.remove(managed);
		if (em.find(Community.class, id) == null) {
			System.out.println("true");
			return managed;
		} 
		else {
			System.out.println("false");
		return null;
		}
	}
	@Override
	public List<Item> getItems(int id){
		String query = "SELECT i from Item I WHERE i.Community = :communityId "; 
		List<Item> items = em.createQuery(query, Item.class)
			.setParameter("communityId", id)
			.getResultList();
		return items;
	}
	@Override
	public List<User> getUsers(int id){
		String query = "SELECT c FROM Community c JOIN FETCH c.members WHERE c.id = :id "; 
		List<Community> community = em.createQuery(query, Community.class)
				.setParameter("id", id)
				.getResultList(); 
		List<User> userList = community.get(0).getMembers(); 
		return userList;
	}

	@Override
	public List<Community> getAllCommunities() {
		List<Community> communities = new ArrayList<>();
		try{
			String q="SELECT c from Community c";
			communities = em.createQuery(q,Community.class)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return communities;
	}
	@Override
	public List<Item> getItembyDescription(String descrip, User user, int groupId) {
		
		List<Item> tempItems = new ArrayList<>();
		try {
			String q = "SELECT i from Item i WHERE i.content LIKE :text OR i.title LIKE :text AND i.community.id = :id";
				tempItems = em.createQuery(q, Item.class).setParameter("text", "%" + descrip + "%")
						.setParameter("id", groupId).getResultList();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempItems;
	}
	@Override
	public List<User> getUserbyFirstOrLastName(String first, String last, User user, int groupId) {
		List<User> finalUsers = new ArrayList<>();
		try{
		String q = "SELECT u FROM User u INNER JOIN u.communities c WHERE (c in :community AND c.id = :cid) AND (u.firstName LIKE :first OR u.lastName LIKE :last)";
		finalUsers = em.createQuery(q,User.class)
				.setParameter("first", "%"+first+"%")
				.setParameter("last","%"+last+"%")
				.setParameter("cid", groupId)
				.setParameter("community", getCommunity(groupId))
				.getResultList();
		}
		catch (Exception e) {
		e.printStackTrace();
		}
		return finalUsers;
	}
	@Override
	public List<Item> getItembyCatID(int id, int groupId) {
		List<Item> items = new ArrayList<>();
		
			String q = "SELECT ii from Item ii WHERE ii.category.id =:cid AND ii.community.id =:comId";
			items = em.createQuery(q, Item.class)
					.setParameter("cid", id)
					.setParameter("comId", groupId)
					.getResultList();
		return items;
	}
	@Override 
	public List<Category> getCategories(){
		String query = "SELECT c FROM Category c"; 
		List<Category> categories = em.createQuery(query, Category.class)
				.getResultList(); 
		return categories; 
	}
}
