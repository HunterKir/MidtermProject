package controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import data.CommunityDAO;
import data.ItemDAO;
import entities.Community;
import entities.Item;
import entities.User;

@Controller
public class ItemController {
	
	@Autowired
	private ItemDAO dao;
	
	@Autowired
	private CommunityDAO comDAO;
	
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
}
