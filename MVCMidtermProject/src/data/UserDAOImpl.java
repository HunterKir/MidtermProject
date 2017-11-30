package data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.User;

public class UserDAOImpl implements UserDAO {

	@Override
	public User getUser(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MidtermProject");
		EntityManager em = emf.createEntityManager();
		User user = null; 
		    try {
		    	 user = em.find(User.class, id);
		    	
		    }
		    catch(Exception e) {
		    		e.printStackTrace();
		    }
		    
		return user;
	}

	@Override
	public User createUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updateUser(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User deleteUser(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
