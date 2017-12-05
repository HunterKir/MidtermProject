package data;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Community;
import entities.User;
public class CommunityDAOImpl implements CommunityDAO{

	@Override
	public Community getCommunity(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		Community community = null; 
		    try {
		    	 community = em.find(Community.class, id);
		    	
		    }
		    catch(Exception e) {
		    		e.printStackTrace();
		    }
		    
		return community;
	}

	@Override
	public Community createCommunity(Community community) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			em.persist(community);
			em.flush();
			em.getTransaction().commit();
			return community; 
		}
		catch(Exception e) {
			e.printStackTrace();
			return null; 
		}
		finally {
			em.close();
			emf.close();
		}
	}

	@Override
	public Community updateCommunity(int id, Community community) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin(); 
			Community managed = em.find(Community.class, id);
			managed.setName(community.getName());
			managed.setOwner(community.getOwner());
			em.getTransaction().commit();
			return managed; 
		}
		catch(Exception e) {
			e.printStackTrace();
			return null; 
		}
		finally {
			em.close();
			emf.close(); 
		}
	}

	@Override
	public Community deleteCommunity(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		
		Community managed = em.find(Community.class,id);
		em.remove(managed);
		if(em.find(Community.class,id) == null) {
			System.out.println("true");
			return managed;
		} else
		System.out.println("false");
		return null;
	}

	@Override
	public List<Community> getCommunitybyDesc(String desc) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		List<Community> c = new ArrayList<>();
		try{
			String q="SELECT c from Community c WHERE c.name LIKE :desc or c.description LIKE :desc";
			c = em.createQuery(q,Community.class).setParameter("desc", "%"+desc+"%")
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	@Override
	public List<Community> getAllCommunities() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
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
}
