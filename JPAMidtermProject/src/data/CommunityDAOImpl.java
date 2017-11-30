package data;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import entities.Community;

@Repository
@Transactional
public class CommunityDAOImpl implements CommunityDAO{

	@Override
	public Community getCommunitybyId( int id) {
		Community c = new Community();
		
		c=em.
		
		return null;
	}

	@Override
	public Community createCommunity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Community updateCommunity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Community deleteCommunity() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
