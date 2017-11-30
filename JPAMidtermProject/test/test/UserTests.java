package test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.UserDAO;
import data.UserDAOImpl;
import entities.User;

public class UserTests {
	EntityManagerFactory emf; 
	EntityManager em; 
	UserDAO dao; 
	@Before
	public void setUp() throws Exception {
		 this.emf = Persistence.createEntityManagerFactory("MidtermProject");
		 this.em = emf.createEntityManager();
		 dao = new UserDAOImpl(); 
	}

	@After
	public void tearDown() throws Exception {
		em.close();
		emf.close();
		dao = null; 
	}

	@Test
	public void test_createUser() {
		User user = new User(); 
		user.setAdmin(false);
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setPassword("12345");
		user.setUsername("JohnDOE");
		
		
	}

}
