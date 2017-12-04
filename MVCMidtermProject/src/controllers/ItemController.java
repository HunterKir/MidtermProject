package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import data.ItemDAO;
import entities.Item;

@Controller
public class ItemController {
	
	@Autowired
	private ItemDAO dao;
	
	public ModelAndView goToNewItemForm() {
		ModelAndView mv = new ModelAndView();
		Item i = new Item();
		mv.addObject("item", i);
		mv.setViewName("views/itemform.jsp");
		return mv;
	}
}
