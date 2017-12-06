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
import data.RatingDAO;
import data.RatingDAOImpl;
import data.UserDAO;
import data.UserDAOImpl;
import entities.Community;
import entities.Item;
import entities.User;
import entities.UserRating;

public class RatingsTest {

	private EntityManagerFactory emf;
	private EntityManager em;
	UserDAO uDao;
	CommunityDAO cDao;
	RatingDAO rDao;

	@Before
	public void setUp() throws Exception {
		this.emf = Persistence.createEntityManagerFactory("MidtermProject");
		this.em = emf.createEntityManager();
		uDao = new UserDAOImpl();
		cDao = new CommunityDAOImpl();
		rDao = new RatingDAOImpl();
	}

	@After
	public void tearDown() throws Exception {
		this.em.close();
		this.emf.close();
		cDao = null;
		uDao = null;
		rDao = null;
	}

//	@Test
//	public void GetRatingbyIdTest() {
//		UserRating r = rDao.getRating(1);
//		assertEquals(5, r.getRating());
//
//	}
	
//	@Test
//	public void createRatingTest() {
//	UserRating r = new UserRating();
//	UserRating rprime = rDao.createRating(r, 1, 4);
//	assertEquals(5,rprime.getRating());
//
//}
	
//	@Test
//	public void updateRatingTest() {
//		UserRating  rprime = rDao.updateRating(1, 5);
//		assertEquals(15,rprime.getRating());
//		
//	}
	
//	@Test
//	public void deleteRatingTest() {
//		UserRating rprime = rDao.deleteRating(4);
//		assertEquals(5,rprime.getRating());
//		
//	}

@Test
public void getAllRatingsbyCid() {
	List <UserRating> rlist = rDao.getallRatingsbyCommunity(1);
	assertEquals(3,rlist.size());
}
	
	}
