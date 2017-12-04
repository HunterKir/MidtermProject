package data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Item;
import entities.Post;
import entities.User;

public class PostDAOImpl implements PostDAO {

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
	public Post deletePost(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
