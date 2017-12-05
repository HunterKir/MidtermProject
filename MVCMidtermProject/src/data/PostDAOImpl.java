package data;

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
	public Post deletePost(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
