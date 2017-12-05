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
import entities.Item;
import entities.Post;
import entities.User;

public class PostDAOImpl implements PostDAO {

	private String url = "jdbc:mysql://localhost:3306/swapmeetdb";
	private String user = "blossom";
	private String pass = "blossom";

	@Override
	public Post getPost(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		Post post = null;
		try {
			post = em.find(Post.class, id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		em.close();
		emf.close();
		return post;
	}

	@Override
	public Post createPost(Post post, int iid, int uid) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		User user = em.find(User.class, uid);
		Item item = em.find(Item.class, iid);
		post.setItem(item);
		post.setUser(user);
		em.getTransaction().begin();
		em.persist(post);
		em.flush();
		em.getTransaction().commit();
		em.close();
		emf.close();
		return post;
	}

	@Override
	public Post updatePost(int id, Post post) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();

		Post postToUpdate = em.find(Post.class, id);
		em.getTransaction().begin();

		postToUpdate.setContent(post.getContent());

		em.getTransaction().commit();
		em.close();
		emf.close();

		return postToUpdate;
	}

	@Override
	public Post deletePost(Post p) {
		Connection conn = null;
		String sql;
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
