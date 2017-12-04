package controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import data.ItemDAO;
import data.UserDAO;
import entities.Item;
import entities.User;

@Controller
public class SearchController {
	@Autowired
	UserDAO uDAO;
	@Autowired
	ItemDAO iDAO;
	
	@RequestMapping(path="search.do", method=RequestMethod.GET)
	public ModelAndView search(@RequestParam("search") String kw, @RequestParam("searchSelect") String searchSelect ,HttpSession session) {
		User user = (User) session.getAttribute("activeUser");
		ModelAndView mv = new ModelAndView(); 
		mv.setViewName("views/userHome.jsp");
		switch(searchSelect) {
		case "items":
			List<Item> itemList = iDAO.getItembyDescription(kw, user); 
			if(itemList == null || itemList.size() == 0)	{
				String error = "No items with keyword: " + kw; 
				mv.addObject("kwError", error);
				return mv; 
			}	
			mv.addObject("itemsList", itemList); 
			break;
		case "people":
			List<User> userList = uDAO.getUserbyFirstOrLastName(kw, "", user);
			if(userList == null || userList.size() ==0) {
				String error = "No people named: " + kw;
				mv.addObject("kwError", error);
				return mv;
			}
			mv.addObject("userList", userList);
			break;
		}
		return mv; 
	}
}