package data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
			String q = "SELECT ii from Item ii WHERE ii.content LIKE :text";
			items = em.createQuery(q, Item.class).setParameter("text", "%" + descrip + "%").getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
			emf.close();
		}
		return items;
	}

	public List<Item> getItembyUserId(int uid) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		List<Item> items = new ArrayList<>();
		try {
			String q = "SELECT ii from Item ii where ii.user.id=:uid";
			items = em.createQuery(q, Item.class).setParameter("uid", uid).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
			emf.close();
		}
		return items;

	}
}
