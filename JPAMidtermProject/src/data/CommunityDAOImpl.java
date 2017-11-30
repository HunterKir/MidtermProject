package data;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Community;
import entities.User;

@Repository
@Transactional
public class CommunityDAOImpl implements CommunityDAO{

	@Override
<<<<<<< HEAD
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
=======
	public Community getCommunitybyId( int id) {
		Community c = new Community();
		
		c=em.
		
		return null;
>>>>>>> e7691e9417bbffce1acfd583233344b32dc8fb73
	}

	@Override
	public Community createCommunity(Community community) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Community updateCommunity(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Community deleteCommunity(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
