package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import entities.Item;
import entities.Post;
import entities.User;
@Repository
@Transactional
public class PostDAOImpl implements PostDAO {
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Post getPost(int id) {

		Post post = null;
		try {
			post = em.find(Post.class, id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return post;
	}

	@Override
	public Post createPost(Post post, int iid, int uid) {
		User user = em.find(User.class, uid);
		Item item = em.find(Item.class, iid);
		post.setItem(item);
		post.setUser(user);
		em.persist(post);
		post = em.find(Post.class, post.getId()); 
		em.flush();
		
		return post;
	}

	@Override
	public Post updatePost(int id, Post post) {
		Post postToUpdate = em.find(Post.class, id);
		postToUpdate.setContent(post.getContent());
		return postToUpdate;
	}

	@Override
	public Post deletePost(Post p) {
		Connection conn = null;
		String sql;
		String url = "jdbc:mysql://localhost:3306/swapmeetdb";
		String user = "blossom";
		String pass = "blossom";
		try {
			conn = DriverManager.getConnection(url, user, pass);
			conn.setAutoCommit(false); // Start transaction
			sql = "DELETE FROM post WHERE id=?";
			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, p.getId());
			st.executeUpdate();

			ResultSet keys = st.getGeneratedKeys();
			int rid = 0;
			if (keys.next()) {
				rid = keys.getInt(1);
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
		return p; 
	}
}
