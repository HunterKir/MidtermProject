package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.CommunityDAO;
import data.CommunityDAOImpl;
import data.UserDAO;
import data.UserDAOImpl;
import entities.Community;

public class CommunityTests {
	EntityManagerFactory emf; 
	EntityManager em; 
	CommunityDAO dao; 
	UserDAO uDao; 
	@Before
	public void setUp() throws Exception {
		 this.emf = Persistence.createEntityManagerFactory("MidtermProject");
		 this.em = emf.createEntityManager();
		 dao = new CommunityDAOImpl();
		 uDao = new UserDAOImpl(); 
	}

	@After
	public void tearDown() throws Exception {
		em.close();
		emf.close();
		dao = null;
		uDao = null; 
	}
	
//	@Test
//	public void CommunitybyDescTest(){
//		List<Community> communities = new ArrayList<>();
//		communities = dao.getCommunitybyDesc("bot");
//		assertEquals(1,communities.size());
//		
//	}
	/*CRUD test are commented out because they make changes to DB*/
//	@Test
//	public void test_createCommunity() {
//		Community com = new Community(); 
//		com.setName("Java crew");
//		com.setOwner(uDao.getUser(1));
//		Community createdCommunity = dao.createCommunity(com); 
//		
//		assertEquals(createdCommunity.getName(), "Java crew"); 
//	}
//	@Test
//	public void test_updateCommunity() {
//		Community com = dao.getCommunity(2); 
//		com.setName("SQL crew" );
//		Community updatedCommunity = dao.updateCommunity(com.getId(), com); 
//		
//		assertEquals(updatedCommunity.getName(), "SQL crew"); 
//	}
//	@Test
//	public void test_getCommunity() {
//		Community com = dao.getCommunity(1); 
//		assertEquals(com.getName(), "Lunar Blossoms");
//	}
//	@Test 
//	public void test_deleteCommunity() {
//		Community deletedCommunity = dao.deleteCommunity(2); 
//		
//		assertEquals(deletedCommunity.getName(), "SQL crew"); 
//	}

//	@Test
//	public void Community_to_MemberMap() {
//		
//		assertEquals(2,em.find(Community.class,1).getMembers().size());
//	}
	
//	@Test
//	public void CommunitySearch() {
//		List <Community> c = dao.getCommunitybyDesc("ink");
//		assertEquals(2,c.size());
//		
//	}
	
//	@Test
//	public void CommunitiesToOwnersTest() {
//		Community c1 = em.find(Community.class,3);
//		Community c2 = em.find(Community.class,5);
//		assertEquals (4,c1.getOwner().getId());
//		assertEquals (4,c2.getOwner().getId());
//		
//	}
	
	
	@Test
	public void GetAllCommunitiesTest() {
		List<Community> list = dao.getAllCommunities();
		assertEquals(4,list.size());
		
	}
}
