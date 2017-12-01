package data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import entities.Community;
import entities.Item;

@Repository
@Transactional
public class CommunityDAOImpl implements CommunityDAO {
	@PersistenceContext
	private EntityManager em;

	@Override
	public Community getCommunity(int id) {
		Community community = null;
		try {
			community = em.find(Community.class, id);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return community;
	}

	@Override
	public Community createCommunity(Community community) {
		try {
			em.persist(community);
			em.flush();	
		}
		catch(Exception e) {
			e.printStackTrace();
			return null; 
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
	

}
