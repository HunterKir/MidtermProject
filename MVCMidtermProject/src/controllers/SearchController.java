package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import data.ItemDAO;
import data.UserDAO;
import entities.Item;

@Controller
public class SearchController {
	@Autowired
	UserDAO uDAO;
	@Autowired
	ItemDAO iDAO;
	
	@RequestMapping(path="search.do", method=RequestMethod.GET)
	public ModelAndView search(@RequestParam("search") String kw) {
		ModelAndView mv = new ModelAndView(); 
		mv.setViewName("views/userHome.jsp");
		List<Item> itemList = iDAO.getItembyDescription(kw); 
		if(itemList == null)	{
			String error = "No items with keyword: " + kw; 
			mv.addObject("kwError", error);
			return mv; 
		}
		mv.addObject("itemsList", itemList); 
		return mv; 
	}
}
