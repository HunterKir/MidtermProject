package data;

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
			String q = "SELECT c from Community c JOIN FETCH c.items WHERE c.id = :id";
			community = em.createQuery(q, Community.class).setParameter("id", id).getResultList().get(0);
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

}
