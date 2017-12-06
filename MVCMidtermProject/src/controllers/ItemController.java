package controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import data.CommunityDAO;
import data.ItemDAO;
import data.PostDAO;
import data.UserDAO;
import entities.Category;
import entities.Item;
import entities.Post;
import entities.User;

@Controller
public class ItemController {
	
	@Autowired
	private ItemDAO dao;
	
	@Autowired
	private CommunityDAO comDAO;
	
	@Autowired
	private UserDAO uDAO;
	
	@Autowired
	private PostDAO pDAO; 
	
	@RequestMapping(path="newItem.do", method=RequestMethod.GET)
	public ModelAndView goToNewItemForm(int id) {
		ModelAndView mv = new ModelAndView();
		Item i = new Item();
		int num = comDAO.getCommunity(id).getId();
		List<Category> cats = comDAO.getCategories();
		mv.addObject("categories", cats);
		mv.addObject("item", i);
		mv.addObject("cid", num);
		mv.setViewName("views/itemform.jsp");
		return mv;
	}
	
	@RequestMapping(path="newItem.do", method=RequestMethod.POST)
	public ModelAndView createNewItem(@Valid @ModelAttribute("item") Item item, Errors errors, Model model, HttpSession session, int id, int category) {
		ModelAndView mv = new ModelAndView();
		User user = (User) session.getAttribute("activeUser");
//		if (errors.hasErrors()) {
//			mv.setViewName("views/itemform.jsp");
//			return mv;
//		}
		if(dao.createItem(item, user, id, category) != null) {
			mv.setViewName("redirect:getPosts.do?id=" + item.getId());
		}
		return mv;
	}
	@RequestMapping(path="newPost.do", method=RequestMethod.POST)
	public ModelAndView createNewItem(@RequestParam("ownerId") int ownerId,
			@RequestParam("itemId") int itemId, 
			@RequestParam("content")String content ) {
		
		ModelAndView mv = new ModelAndView(); 
		Post post = new Post(); 
		post.setContent(content);
		post.setItem(dao.getItem(itemId));
		post.setUser(uDAO.getUser(ownerId));
		post.setPostTime(LocalDateTime.now());
		pDAO.createPost(post, itemId, ownerId); 
		
		mv.setViewName("redirect: getPosts.do?id=" + itemId);
		return mv;
	}
	@RequestMapping(path="showUpdateArea.do", method=RequestMethod.GET)
	public ModelAndView goToUpdatePost(@RequestParam("postId") int postId,
			@RequestParam("itemId") int itemId) {
		ModelAndView mv = new ModelAndView(); 
		mv.setViewName("getPosts.do?id="+ itemId);
		Post editPost = new Post(); 
		mv.addObject("editPost", editPost); 
		return mv; 
	}
	@RequestMapping(path="updatePost.do", method=RequestMethod.POST)
	public ModelAndView updateNewItem(@RequestParam("postId") int postId,
			@RequestParam("itemId") int itemId, @RequestParam("postContent")String content) {
		ModelAndView mv = new ModelAndView(); 
		mv.setViewName("getPosts.do?id="+ itemId);
		Post post = pDAO.getPost(postId); 
		post.setContent(content);
		pDAO.updatePost(postId, post); 
		return mv; 
	}
	
	@RequestMapping(path="removeItem.do")
	public ModelAndView removeItem(int iid, int cid) {
		ModelAndView mv = new ModelAndView();
		dao.changeActiveStatus(iid);
		mv.setViewName("redirect:viewGroup.do?id=" + cid);
		return mv;
	}
	
	@RequestMapping(path="updateItem.do", method=RequestMethod.GET)
	public ModelAndView goToUpdateItem(int id) {
		ModelAndView mv = new ModelAndView();
		Item i = dao.getItem(id);
		mv.addObject("item", i);
		mv.setViewName("views/itemform.jsp");
		return mv;
	}
	
	@RequestMapping(path="updateItem.do", method=RequestMethod.POST)
	public ModelAndView updateItem(@Valid @ModelAttribute("item") Item item, Errors errors, Model model) {
		ModelAndView mv = new ModelAndView();
		if (errors.hasErrors()) {
			mv.setViewName("views/itemform.jsp");
			return mv;
		}
		dao.updateItem(item.getId(), item);
		mv.setViewName("redirect:getPosts.do?id=" + item.getId());
		return mv;
	}
	@RequestMapping(path="updateItemInModal.do", method=RequestMethod.POST)
	public ModelAndView updateInModalItem(@Valid @ModelAttribute("item") Item item, Errors errors, Model model, int uid, int cid) {
		ModelAndView mv = new ModelAndView();
		// this breaks things for some reason
//		if (errors.hasErrors()) {
//			mv.setViewName("redirect:getPosts.do?id=" + item.getId());
//			return mv;
//		}
		item.setCommunity(comDAO.getCommunity(cid));
		item.setUser(uDAO.getUser(uid));
		dao.updateItem(item.getId(), item);
		mv.setViewName("redirect:getPosts.do?id=" + item.getId());
		return mv;
	}
	@RequestMapping(path="deletePost.do", method=RequestMethod.POST)
	public ModelAndView delteItem(@RequestParam("postId")int postId,
			@RequestParam("itemId") int itemId) {
		ModelAndView mv = new ModelAndView(); 
		mv.setViewName("redirect: getPosts.do?id=" + itemId);
		Post post = pDAO.getPost(postId); 
		pDAO.deletePost(post); 
		return mv; 
	}
	
	@RequestMapping(path="soldItem.do")
	public ModelAndView soldItem(int iid, int cid) {
		ModelAndView mv = new ModelAndView();
		dao.changeSoldStatus(iid);
		mv.setViewName("redirect:viewGroup.do?id=" + cid);
		return mv;
	}
}
