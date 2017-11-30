package data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import entities.Community;
import entities.User;
@Repository
@Transactional
public class CommunityDAOImpl implements CommunityDAO{
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	public Community getCommunity(int id) {
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
		em.persist(community);
		em.flush();
		return community;
	
	}

	@Override
	public Community updateCommunityName(int id, String name) {
		Community managed = em.find(Community.class,id);
		managed.setName(name);
		return managed;
	}

	@Override
	public Community deleteCommunity(int id) {
		// TODO Auto-generated method stub
		return null;
	}



	
	
}
