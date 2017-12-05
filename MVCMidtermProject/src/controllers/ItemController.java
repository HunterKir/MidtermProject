package controllers;

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
import data.UserDAO;
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
	
	@RequestMapping(path="newItem.do", method=RequestMethod.GET)
	public ModelAndView goToNewItemForm(int id) {
		ModelAndView mv = new ModelAndView();
		Item i = new Item();
		int num = comDAO.getCommunity(id).getId();
		mv.addObject("item", i);
		mv.addObject("cid", num);
		mv.setViewName("views/itemform.jsp");
		return mv;
	}
	
	@RequestMapping(path="newItem.do", method=RequestMethod.POST)
	public ModelAndView createNewItem(@Valid @ModelAttribute("item") Item item, Errors errors, Model model, HttpSession session, int id) {
		ModelAndView mv = new ModelAndView();
		User user = (User) session.getAttribute("activeUser");
		if (errors.hasErrors()) {
			mv.setViewName("views/itemform.jsp");
			return mv;
		}
		if(dao.createItem(item, user, id) != null) {
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
		return mv;
	}
	
	@RequestMapping(path="removeItem.do")
	public ModelAndView removeItem(int id) {
		ModelAndView mv = new ModelAndView();
		dao.changeActiveStatus(id);
		mv.setViewName("views/removed.jsp");
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
}
