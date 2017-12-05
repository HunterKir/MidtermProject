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

import data.PostDAO;
import data.PostDAOImpl;
import data.UserDAO;
import data.UserDAOImpl;
import entities.Community;
import entities.Item;
import entities.Post;
import entities.User;

public class UserTests {
	EntityManagerFactory emf; 
	EntityManager em; 
	UserDAO dao; 
	PostDAO pdao;
	@Before
	public void setUp() throws Exception {
		 this.emf = Persistence.createEntityManagerFactory("MidtermProject");
		 this.em = emf.createEntityManager();
		 dao = new UserDAOImpl(); 
		 pdao = new PostDAOImpl();
	}

	@After
	public void tearDown() throws Exception {
		em.close();
		emf.close();
		dao = null;
		pdao = null;
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
//	@Test
//	public void test_deleteUser() {
//		User deletedUser = dao.deleteUser(5); 
//		
//		assertEquals("Crying", deletedUser.getFirstName());
//	}
//	

//	@Test
//	public void getPostHistTest() {
//		assertEquals(3,dao.getPostHistorybyUid(2).size());
//		
//	}
//	@Test
//	public void test_getUserByUserName() {
//		User user = dao.getUserByUserName("HunterK");
//		assertEquals(user.getFirstName(), "Hunter"); 
//	}
	 
//	@Test
//	public void User_to_CommunityMap() {
//		
//		assertEquals(1,em.find(User.class,2).getCommunities().size());
//	}
	
//	@Test
//	public void FirstOrLastNameTest() {
//		List<User> users = new ArrayList<>();
//		users = dao.getUserbyFirstOrLastName("ames","smit");
//		assertEquals(3,users.size());
//		
//	}
//	@Test
//	public void User_to_CommunityMap() {
//		
//		assertEquals(1,em.find(User.class,2).getCommunities().size());
//	}
	
	
	
//	@Test
//	public void getPostsTest() {
//		User u = em.find(User.class, 5);
//				assertEquals(2,u.getPosts().size());
//		
//	}
//	@Test
//	public void getPostsTestReverse() {
//		Post p = em.find(Post.class, 1);
//		Post p2 = em.find(Post.class, 2);
//		
//		assertEquals(1,p.getUser().getId());
//		assertEquals(1,p2.getUser().getId());
//		
//	}
	
//	@Test
//	public void CommunityOwnerTest() {
//		User u = em.find(User.class,4);
//		List<Community> l = u.getOwnedCommunities();
//		assertEquals(2,l.size());
//		
//	}
	
//	@Test
//	public void UserRemovefromCommunityTest() {
//		User u = em.find(User.class,1);
//		dao.removeUserfromCommunityByCid(u, 3);
//		assertEquals(2,u.getCommunities().size());
//		
//	}
	
//	@Test
//	public void plusViewsTest() {
//		User u = em.find(User.class,1);
//		u = dao.plusProfileViewCount(1);
//		assertEquals(4,u.getProfileViews());
//		
//	}
	
//	@Test 
//	public void getPOST(){
//		Post p = em.find(Post.class,1);
//		
//		assertEquals(1,p.getUser().getId());
//		
//	}
	
//	@Test
//	public void createPost() {
//		Post p = new Post();
//		String content = "this is the GOAT item";
//		p.setContent(content);
//		pdao.createPost(p,2,1);
//		assertEquals(1,p.getUser().getId());
//		
//	}
	
//	@Test
//	public void updatePost() {
//		Post p = em.find(Post.class, 3);
//		p.setContent("Worst ITEM IN THE UNIVERSE!");
//		
//		 p = pdao.updatePost(3, p);
//		
//		assertEquals("Worst ITEM IN THE UNIVERSE!",p.getContent());
//		
//	}
	
	@Test
	public void deletePostTest() {
		Post p = em.find(Post.class, 4);
		Post premoved = pdao.deletePost(p);
		assertEquals("a disgusting squid",premoved.getContent());
		
	}
}
