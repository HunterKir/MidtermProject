package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
import data.ItemDAO;
import data.ItemDAOImpl;
import data.UserDAO;
import data.UserDAOImpl;
import entities.Community;
import entities.Item;
import entities.User;

public class ItemTests {

	private EntityManagerFactory emf;
	private EntityManager em;
	ItemDAO dao;
	UserDAO uDao;
	CommunityDAO cDao;

	@Before
	public void setUp() throws Exception {
		this.emf = Persistence.createEntityManagerFactory("MidtermProject");
		this.em = emf.createEntityManager();
		dao = new ItemDAOImpl();
		uDao = new UserDAOImpl();
		cDao = new CommunityDAOImpl();
	}

	@After
	public void tearDown() throws Exception {
		this.em.close();
		this.emf.close();
		dao = null;
		uDao = null;
	}

//	@Test
//	public void CatTest() {
//		assertEquals(2, dao.getItembyCatID(2).size());
//
//	}
////
//	@Test
//	public void PriceFilterTest() {
//		assertEquals(2, dao.getItembyPrice(2, 3).size());
//
//	}
//
//	@Test
//	public void descTest() {
//		assertEquals(3, dao.getItembyDescription("air").size());
//
//	}

	/* CRUD test are commented out because they make changes to DB */
	// @Test
	// public void test_createItem() {
	// Item item = new Item();
	// User user = em.find(User.class, 1);
	// Community com = cDao.getCommunity(1);
	// item.setCommunity(com);
	// item.setUser(user);
	// item.setTitle("test");
	// item.setContent("test");
	// Item testItem = dao.createItem(item);
	//
	// assertEquals(item.getUser().getId(), testItem.getUser().getId());
	// }
	// @Test
	// public void test_updateItem() {
	// Item item = dao.getItem(1);
	// item.setContent("NewTest");
	// Item testItem = dao.updateItem(item.getId(), item);
	//
	// assertEquals(item.getTitle(), testItem.getTitle());
	// }
//	 @Test
//	 public void test_getItem() {
//	 Item item = dao.getItem(1);
//	
//	 assertEquals(item.getTitle(), "Test");
//	 }
	// @Test
	// public void test_deleteItem() {
	// Item deletedItem = dao.deleteItem(5);
	//
	// assertNotNull(deletedItem);
	// }

//	 @Test
//	 public void getTitle(){
//		 Item i = em.find(Item.class,1);
//		 assertEquals("Chair",i.getTitle());
//		 
//	 }
	
//	@Test
//	public void getitmebytimefilter() {
//		List<Item> ii = new ArrayList<>();
//		ii = dao.getItembyPastXdaysbyCommunity(4, 7);
//		
//		assertEquals(2,ii.size());
//		
//		
//	}
	
	@Test
	public void changeItemStatusTest() {
		Item ii = em.find(Item.class, 1);
		ii = dao.changeActiveStatus(ii.getId());
		assertEquals(false,ii.getActive());
		
	}
	
}
