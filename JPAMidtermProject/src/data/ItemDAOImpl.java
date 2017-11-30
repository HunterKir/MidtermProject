package data;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Community;
import entities.Item;
import entities.User;

public class ItemDAOImpl implements ItemDAO {
	@Override
	public Item createItem(Item item) {
		EntityManagerFactory emf = 
		        Persistence.createEntityManagerFactory("MidtermProject");
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
	    
	    if(item != null) {
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
		
		   Item itemToUpdate  = em.find(Item.class, id);
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
}
