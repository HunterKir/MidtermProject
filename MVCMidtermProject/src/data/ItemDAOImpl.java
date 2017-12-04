package data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import entities.Community;
import entities.Item;
import entities.User;

@Repository
@Transactional
public class ItemDAOImpl implements ItemDAO {
	@PersistenceContext
	private EntityManager em;

	@Override
	public Item createItem(Item item) {
		em.persist(item);
		em.flush();
		return item;
	}

	@Override
	public Item getItem(int id) {
		Item item = null;
		// should change this to be more efficient
		item = em.find(Item.class, id);
		item.getPosts().size();
//		String q = "SELECT ii FROM Item ii JOIN FETCH ii.posts WHERE ii.id = :id";
//		item = em.createQuery(q, Item.class).setParameter("id", id).getResultList().get(0);
		return item;
	}

	@Override
	public Item deleteItem(int id) {
		Item item = em.find(Item.class, id);

		if (item != null) {
			item.setActive(false);
		}

		return item;
	}

	@Override
	public Item updateItem(int id, Item item) {

		Item itemToUpdate = em.find(Item.class, id);

		itemToUpdate.setUser(item.getUser());
		itemToUpdate.setCategory(item.getCategory());
		itemToUpdate.setCommunity(item.getCommunity());
		itemToUpdate.setContent(item.getContent());
		itemToUpdate.setPostTime(item.getPostTime());
		itemToUpdate.setPrice(item.getPrice());
		itemToUpdate.setTitle(item.getTitle());

		return itemToUpdate;
	}

	@Override
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

	@Override
	public List<Item> getItembyPrice(double min, double max) {

		List<Item> items = new ArrayList<>();
		try {
			String q = "SELECT ii from Item ii WHERE ii.price BETWEEN :min AND :max";
			items = em.createQuery(q, Item.class).setParameter("min", min).setParameter("max", max).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}

	@Override
	public List<Item> getItembyDescription(String descrip, User user) {
		
		List<Item> tempItems = new ArrayList<>();
		List<Item> finalItems = new ArrayList<>();
		try {
			String q = "SELECT i from Item i WHERE i.content LIKE :text AND i.community.id = :id";
			for (Community c : user.getCommunities()) {
				tempItems = em.createQuery(q, Item.class).setParameter("text", "%" + descrip + "%")
						.setParameter("id", c.getId()).getResultList();
				for (Item i : tempItems) {
					finalItems.add(i);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalItems;
	}
}
