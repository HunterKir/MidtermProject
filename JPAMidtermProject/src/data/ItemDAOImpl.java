package data;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import entities.Community;
import entities.Item;
import entities.User;

public class ItemDAOImpl implements ItemDAO {
	@Override
	public Item createItem(Item item) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(item);
		em.flush();
		em.getTransaction().commit();
		em.close();
		emf.close();
		return item;
	}

	@Override
	public Item getItem(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		Item item = null;
		item = em.find(Item.class, id);
		return item;
	}

	@Override
	public Item deleteItem(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		Item item = em.find(Item.class, id);

		if (item != null) {
			em.getTransaction().begin();
			em.remove(item);
			em.getTransaction().commit();
		}

		return item;
	}

	@Override
	public Item updateItem(int id, Item item) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();

		Item itemToUpdate = em.find(Item.class, id);
		em.getTransaction().begin();

		itemToUpdate.setUser(item.getUser());
		itemToUpdate.setCategory(item.getCategory());
		itemToUpdate.setCommunity(item.getCommunity());
		itemToUpdate.setContent(item.getContent());
		itemToUpdate.setPostTime(item.getPostTime());
		itemToUpdate.setPrice(item.getPrice());
		itemToUpdate.setTitle(item.getTitle());

		em.getTransaction().commit();
		em.close();
		emf.close();

		return itemToUpdate;
	}

	public List<Item> getItembyCatID(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		List<Item> items = new ArrayList<>();
		try {
			String q = "SELECT ii from Item ii WHERE ii.category.id =:cid";
			items = em.createQuery(q, Item.class).setParameter("cid", id).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
			emf.close();
		}
		return items;
	}

	public List<Item> getItembyPrice(double min, double max) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		List<Item> items = new ArrayList<>();
		try {
			String q = "SELECT ii from Item ii WHERE ii.price BETWEEN :min AND :max";
			items = em.createQuery(q, Item.class).setParameter("min", min).setParameter("max", max).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
			emf.close();
		}
		return items;
	}

	public List<Item> getItembyDescription(String descrip) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		List<Item> items = new ArrayList<>();
		try {
			String q = "SELECT ii from Item ii WHERE ii.content LIKE :text OR ii.title LIKE :text";
			items = em.createQuery(q, Item.class).setParameter("text", "%" + descrip + "%").getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
			emf.close();
		}
		return items;
	}

	@Override
	public List<Item> getItembyPastXdaysbyCommunity(int cid, int day) {

		List<Item> list = new ArrayList<>();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		List<Item> filtered = null;
		try {
			String q = "SELECT ii from Item ii WHERE ii.community.id=:cid";
			list = em.createQuery(q, Item.class).setParameter("cid", cid).getResultList();
			filtered = new ArrayList<Item>();
			for (Item item : list) {
				if (item.getPostTime().isAfter(LocalDateTime.now().minusDays(day))
						&& item.getPostTime().isBefore(LocalDateTime.now())) {
					filtered.add(item);

				}
			}

		} catch (Exception e) {
			em.close();
			emf.close();
		}
		return filtered;
	}

	@Override
	public Item changeActiveStatus(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Item itemToUpdate = em.find(Item.class, id);
		if (itemToUpdate.getActive() == true) {
			System.out.println("flipped to false");
			itemToUpdate.setActive(false);
		} else {
			System.out.println("flipped to true");
			itemToUpdate.setActive(true);
		}
		em.getTransaction().commit();
		return itemToUpdate;
	}

	@Override
	public Item changeSoldStatus(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Item itemToUpdate = em.find(Item.class, id);
		if (itemToUpdate.isSold() == false) {
			System.out.println("flipped to true");
			itemToUpdate.setSold(true);
			itemToUpdate.setActive(false);
		} else {
			System.out.println("flipped to false");
			itemToUpdate.setSold(false);
			itemToUpdate.setActive(true);
		}
		
		em.getTransaction().commit();
		return itemToUpdate;
	}

	@Override
	public List<Item> getAllInactiveItems() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		List<Item> items = new ArrayList<>();
		try {
			String q = "SELECT ii from Item ii WHERE ii.active =FALSE";
			items = em.createQuery(q, Item.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
			emf.close();
		}
		return items;
	}
	

}
