package data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import entities.Community;
import entities.Item;
import entities.User;

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
		em.persist(community);
		em.flush();
		return community;

	}

	@Override
	public Community updateCommunityName(int id, String name) {
		Community managed = em.find(Community.class, id);
		managed.setName(name);
		return managed;
	}

	@Override
	public Community deleteCommunity(int id) {
		Community managed = em.find(Community.class, id);
		em.remove(managed);
		if (em.find(Community.class, id) == null) {
			System.out.println("true");
			return managed;
		} else
			System.out.println("false");
		return null;
	}

	public List<Item> getItembyCatID(int id) {
		List<Item> items = new ArrayList<>();
		try {
			String q = "SELECT ii from Item ii WHERE ii.category.id =:cid";
			items = em.createQuery(q, Item.class).setParameter("cid", id).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}

	public List<Item> getItembyPrice(double min, double max) {
		List<Item> items = new ArrayList<>();
		try {
			String q = "SELECT ii from Item ii WHERE ii.price BETWEEN :min AND < :max";
			items = em.createQuery(q, Item.class).setParameter("min", min).setParameter("max", max).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}

	public List<Item> getItembyDescription(String descrip) {
		List<Item> items = new ArrayList<>();
		try {
			String q = "SELECT ii from Item ii WHERE ii.content LIKE :text";
			items = em.createQuery(q, Item.class).setParameter("text", "%" + descrip + "%").getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}
}
