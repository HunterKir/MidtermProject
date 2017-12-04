package controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import data.ItemDAO;
import entities.Item;
import entities.User;

@Controller
public class ItemController {
	
	@Autowired
	private ItemDAO dao;
	
	@RequestMapping(path="newItem.do", method=RequestMethod.GET)
	public ModelAndView goToNewItemForm() {
		ModelAndView mv = new ModelAndView();
		Item i = new Item();
		mv.addObject("item", i);
		mv.setViewName("views/itemform.jsp");
		return mv;
	}
	
	@RequestMapping(path="newItem.do", method=RequestMethod.POST)
	public ModelAndView createNewItem(@Valid Item item, Errors errors, Model model, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		if (errors.hasErrors()) {
			mv.setViewName("views/itemform.jsp");
			return mv;
		}
		if(dao.createItem(item) != null) {
			mv.setViewName("getPosts.do?id=" + item.getId());
		}
		return mv;
	}
}
