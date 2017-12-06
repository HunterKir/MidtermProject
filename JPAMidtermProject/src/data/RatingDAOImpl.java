package data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Community;
import entities.Item;
import entities.Post;
import entities.User;
import entities.UserRating;

public class RatingDAOImpl implements RatingDAO {

	@Override
	public UserRating getRating(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		UserRating r = null;
		try {
			r = em.find(UserRating.class, id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		em.close();
		emf.close();
		return r;
	}
		
	@Override
	public UserRating getRatingbyCidAndUid(int cid, int uid) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		UserRating r = null;
		try {
			String q = "SELECT r from UserRating r WHERE r.community.id =:cid AND r.user.id=:uid";
			r = em.createQuery(q, UserRating.class).setParameter("cid", cid).setParameter("uid",uid).
					getResultList().get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		em.close();
		emf.close();
		return r;
	}

	@Override
	public UserRating createRating(UserRating rating, int cid, int uid) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		User u = em.find(User.class, uid);
		Community c = em.find(Community.class,cid);
		rating.setCommunity(c);
		rating.setUser(u);
		rating.setRating(5);
		em.getTransaction().begin();
		em.persist(rating);
		em.flush();
		em.getTransaction().commit();
		em.close();
		emf.close();
		return rating;
	
		
		
	}

	@Override
	public UserRating updateRating(int id, int score) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		UserRating ratingToUpdate = em.find(UserRating.class, id);
		em.getTransaction().begin();
		ratingToUpdate.setRatingCount(ratingToUpdate.getRatingCount()+1); 
		int countprime = ratingToUpdate.getRatingCount();
		int countold = countprime-1;
		double scoreprime = (ratingToUpdate.getRating()*countold)+score;
		double averageprime = (scoreprime/countprime);
		ratingToUpdate.setRating(averageprime);

		em.getTransaction().commit();
		em.close();
		emf.close();

		return ratingToUpdate;
	}

	@Override
	public UserRating deleteRating(int id) {
		 
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
			EntityManager em = emf.createEntityManager();
			UserRating r = em.find(UserRating.class, id);

			if (r != null) {
				em.getTransaction().begin();
				em.remove(r);
				em.getTransaction().commit();
			}

			return r;
		}
	

	@Override
	public List<UserRating> getallRatingsbyCommunity(int cid) {
		List<UserRating> list = new ArrayList<>();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		try {
			String q = "SELECT r from UserRating r WHERE r.community.id =:cid";
			list = em.createQuery(q, UserRating.class).setParameter("cid", cid).
					getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		em.close();
		emf.close();
		
		
		return list ;
	}



	

	
	
}
