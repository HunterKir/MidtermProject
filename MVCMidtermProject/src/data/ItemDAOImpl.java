package data;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Community;
import entities.Item;
import entities.User;

public class ItemDAOImpl implements ItemDAO {
	
	public static void main(String[] args) {
		ItemDAO dao = new ItemDAOImpl();
		UserDAO uDAO = new UserDAOImpl(); 
		CommunityDAO cDAO = new CommunityDAOImpl(); 
		
		Item x = new Item();
		User user = uDAO.getUser(1);
		Community com = cDAO.getCommunity(1); 
		
		x.setTitle("Test");
		x.setCommunity(com);
		x.setContent("test");
		x.setPostTime(LocalDateTime.now());
		
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
