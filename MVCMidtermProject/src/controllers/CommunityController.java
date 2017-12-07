package controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import data.CommunityDAO;
import data.ItemDAO;
import data.UserDAO;
import entities.Community;
import entities.Item;
import entities.Post;
import entities.User;

@Controller
public class CommunityController {
	@Autowired
	private CommunityDAO comDAO; 
	
	@Autowired
	private ItemDAO itemDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(path="newGroup.do", method=RequestMethod.GET)
	public ModelAndView goToNewGroup() {
		ModelAndView mv = new ModelAndView(); 
		mv.setViewName("views/newgroup.jsp");
		Community comModel = new Community(); 
		mv.addObject("comModel", comModel);
		return mv; 
	}
	@RequestMapping(path="newGroup.do", method=RequestMethod.POST)
	public ModelAndView submitNewGroup(@Valid @ModelAttribute("comModel") Community comModel, Errors errors, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		User sessionUser = (User) session.getAttribute("activeUser");
		if (errors.getErrorCount() != 0) {
			mv.setViewName("views/newgroup.jsp");
			return mv;
		}
		comDAO.createCommunity(comModel, sessionUser);
		User reloaded = userDAO.getLoadedUser(sessionUser.getUsername());
		session.setAttribute("activeUser", reloaded);
		mv.setViewName("views/userHome.jsp");
		return mv;
	}
	
	@RequestMapping(path="getPosts.do")
	public ModelAndView goToPostPage(int id) {
		ModelAndView mv = new ModelAndView();
		Item item = itemDAO.getItem(id);
		Post newPost = new Post();
		mv.setViewName("views/posts.jsp");
		mv.addObject("newPost", newPost);
		mv.addObject("item", item);
		return mv;
	}
	
	@RequestMapping(path="submitPost.do", method=RequestMethod.POST)
	public ModelAndView submitNewPostToItem(@Valid @ModelAttribute("newPost") Post newPost, Errors errors) {
		ModelAndView mv = new ModelAndView();
		if (errors.getErrorCount() != 0) {
			mv.setViewName("views/posts.jsp");
		}
		mv.setViewName("redirect:getPosts.do");
		return mv;
	}
	
	@RequestMapping(path="viewGroup.do")
	public ModelAndView goToGroupPage(@RequestParam("groupId") int id) {
		ModelAndView mv = new ModelAndView();
		Community c = comDAO.getCommunity(id);
		mv.addObject("group", c);
		
		List<User> groupUsers = comDAO.getUsers(c.getId()); 
		mv.addObject("groupUsers", groupUsers);
		mv.addObject("groupSize", groupUsers.size());
		mv.addObject("groupItemsList", comDAO.getAllItemsInCommunity(c.getId())); 
		mv.addObject("categories", comDAO.getCategories()); 
		mv.setViewName("views/grouphome.jsp");
		return mv;
	}
	
//	@RequestMapping(path="groupView.do", method=RequestMethod.GET)
//	public ModelAndView viewGroup(@RequestParam("groupId") int id) {
//		ModelAndView mv = new ModelAndView();
//		mv.setViewName("views/groupView.jsp");
//		Community c= comDAO.getCommunity(id); 
//		mv.addObject("group", c); 
//		return mv; 
//	}
	
	@RequestMapping(path="updateGroup.do")
	public ModelAndView updateGroup(@Valid @ModelAttribute("group") Community community, Errors errors, int cid) {
		ModelAndView mv = new ModelAndView();
		comDAO.updateCommunityName(cid, community);
		mv.setViewName("redirect:viewGroup.do?id=" + cid);
		return mv;
	}
	
	@RequestMapping(path="deleteGroup.do")
	public ModelAndView deleteGroup(int cid) {
		ModelAndView mv = new ModelAndView();
		comDAO.deleteCommunity(cid);
		mv.setViewName("redirect:login.do");
		return mv;
	}
	
	@RequestMapping(path="userJoinGroup.do")
	public ModelAndView userJoinGroup(@RequestParam("groupId") int groupId, @RequestParam("userId") int userId) {
		ModelAndView mv = new ModelAndView(); 
		mv.setViewName("viewGroup.do?groupId="+groupId);
		User user = userDAO.getUser(userId);
		comDAO.addUsertoCommunity(user, groupId);
		return mv;
	}
}
