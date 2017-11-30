package data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import entities.User;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public User getUser(int id) {
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
