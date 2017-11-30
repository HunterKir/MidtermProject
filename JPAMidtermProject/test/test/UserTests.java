package test;

import static org.junit.Assert.assertEquals;

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
	/*CRUD test are commented out because they make changes to DB*/
//	@Test
//	public void test_createUser() {
//		User user = new User(); 
//		user.setAdmin(false);
//		user.setFirstName("John");
//		user.setLastName("Doe");
//		user.setPassword("12345");
//		user.setUsername("JohnDOE");
//		
//		User createdUser = dao.createUser(user);
//		
//		assertEquals(createdUser.getFirstName(), "John");
//		
//	}
//	@Test
//	public void test_updateUser() {
//		User user = dao.getUser(2); 
//		user.setFirstName("Allen");
//		
//		User updatedUser = dao.updateUser(user.getId(), user);
//		assertEquals("Allen", updatedUser.getFirstName()); 
//	}
//	@Test
//	public void test_getUser() {
//		User user = dao.getUser(2);
//		
//		assertEquals("Allen", user.getFirstName());
//	}
//	@Test
//	public void test_deleteUser() {
//		User deletedUser = dao.deleteUser(2); 
//		
//		assertEquals("Allen", deletedUser.getFirstName());
//	}
	

	@Test
	public void getPostHistTest() {
		assertEquals(3,dao.getPostHistorybyUid(2).size());
		
	}
	 
}
