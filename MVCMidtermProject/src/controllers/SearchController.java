package controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import data.CommunityDAO;
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
	
	@Autowired 
	CommunityDAO cDAO; 
	
	@RequestMapping(path="search.do", method=RequestMethod.GET)
	public ModelAndView search(@RequestParam("search") String kw, @RequestParam("searchSelect") String searchSelect ,HttpSession session) {
		User user = (User) session.getAttribute("activeUser");
		ModelAndView mv = new ModelAndView(); 
		mv.setViewName("views/userHome.jsp");
		switch(searchSelect) {
			
			case "items":
				List<Item> itemList = iDAO.getAllItemsInAllCommunitiesByDescription(kw, user);
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
	@RequestMapping(path="groupSearch.do", method=RequestMethod.GET)
	public ModelAndView searchBarInsideOfGroupHome(@RequestParam("groupId") int groupId
			, HttpSession session
			, @RequestParam("searchSelect") String searchSelect
			, @RequestParam("search") String kw) {
		
		User user = (User) session.getAttribute("activeUser");
		ModelAndView mv = new ModelAndView(); 
		mv.setViewName("viewGroup.do?id=" + groupId);
		
		if(kw.equals("")) {
			List<Item> itemList = cDAO.getAllItemsInCommunity(groupId) ; 
			mv.addObject("searchItemsList", itemList);
			return mv; 
		}
		else
		{	
			switch(searchSelect) {
			case "items":
				List<Item> itemList = cDAO.getItembyDescription(kw, user, groupId); 
				if(itemList == null || itemList.size() == 0)	{
					String error = "No items with keyword: " + kw; 
					mv.addObject("kwError", error);
					return mv; 
				}	
				mv.addObject("searchItemsList", itemList); 
				break;
			case "people":
				List<User> userList = cDAO.getUserbyFirstOrLastName(kw, kw, user, groupId);
				if(userList == null || userList.size() ==0) {
					String error = "No people named: " + kw;
					mv.addObject("kwError", error);
					return mv;
				}
				mv.addObject("searchUsersList", userList);
				break;
			}
		}
		return mv; 
	}
	@RequestMapping(path="searchByCategory.do", method=RequestMethod.GET)
	public ModelAndView searchByCategory(@RequestParam("catId") int categoryId
			, @RequestParam("groupId") int groupId
			, @RequestParam("catType") String catType) {
		ModelAndView mv = new ModelAndView(); 
		mv.setViewName("viewGroup.do?id=" + groupId);
		List<Item> itemList = cDAO.getItembyCatID(categoryId, groupId); 
		mv.addObject("searchItemsList", itemList); 
		
		if(itemList == null || itemList.size() == 0)	{
			String error = "No items in category: " + catType; 
			mv.addObject("kwError", error);
			return mv; 
		}
		else
		{
			mv.addObject("searchItemsList", itemList);
			return mv; 
		}
	}
	@RequestMapping(path="resetSearch.do", method=RequestMethod.GET)
	public ModelAndView resetSearch(@RequestParam("groupId") int groupId) {
		ModelAndView mv = new ModelAndView(); 
		mv.setViewName("viewGroup.do?id="+ groupId);
		mv.addObject("groupItemsList", cDAO.getAllItemsInCommunity(groupId)); 
		return mv; 
	}
	@RequestMapping(path="searchByRange.do", method=RequestMethod.GET)
	public ModelAndView searchByRange(@RequestParam("groupId") int groupId
			, @RequestParam("min") int min
			, @RequestParam("max") int max) {
		
		ModelAndView mv = new ModelAndView(); 
		mv.setViewName("viewGroup.do?id="+ groupId);
		
		if(min > max) {
			min = 0; 
		}
		List<Item> itemList = cDAO.getAllItemsInCommunityByRange(groupId, min, max); 
		if(itemList == null || itemList.size() == 0) {
			String error = "No items in range: " + min + " - " + max; 
			mv.addObject("kwError", error);
			return mv; 
		}
		else
		{
			mv.addObject("searchItemsList", cDAO.getAllItemsInCommunityByRange(groupId, min, max)); 	
		}
		return mv; 
	}
}
