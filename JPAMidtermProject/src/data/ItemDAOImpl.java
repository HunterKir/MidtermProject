package data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Community;
import entities.Item;

public class ItemDAOImpl implements ItemDAO {
	
	public static void main(String[] args) {
		ItemDAO dao = new ItemDAOImpl();
		
		Item x = new Item(); 
		x.setTitle("Test");
				
		dao.postItem(x);
	}

	@Override
	public Item postItem(Item item) {
		EntityManagerFactory emf = 
		        Persistence.createEntityManagerFactory("MidtermProject");
		    EntityManager em = emf.createEntityManager();
		    
		    em.getTransaction().begin();
		    em.persist(item);
		    em.flush();
		    em.getTransaction().commit();
		    em.close();
		    emf.close();
		    return null;
	}

	@Override
	public Item getItem(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item deleteItem(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item updateItem(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
