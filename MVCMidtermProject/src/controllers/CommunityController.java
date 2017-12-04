package controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import data.CommunityDAO;
import data.ItemDAO;
import entities.Community;
import entities.Item;
import entities.Post;
import entities.User;

@Controller
public class CommunityController {
	@Autowired
	CommunityDAO comDAO; 
	
	@Autowired
	ItemDAO itemDAO;
	
	@RequestMapping(path="newGroup.do", method=RequestMethod.GET)
	public ModelAndView goToNewGroup() {
		ModelAndView mv = new ModelAndView(); 
		mv.setViewName("views/newgroup.jsp");
		Community comModel = new Community(); 
		mv.addObject("comModel", comModel);
		return mv; 
	}
	@RequestMapping(path="newGroup.do", method=RequestMethod.POST)
	public ModelAndView submitNewGroup(@Valid @ModelAttribute("comModel") Community comModel, Errors errors) {
		ModelAndView mv = new ModelAndView();
		if (errors.getErrorCount() != 0) {
			mv.setViewName("views/newgroup.jsp");
			return mv;
		}
		mv.setViewName("views/grouphome.jsp");
		return mv;
	}
	
	@RequestMapping(path="getPosts.do", method=RequestMethod.GET)
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
	
	@RequestMapping(path="viewGroup.do", method=RequestMethod.GET)
	public ModelAndView goToGroupPage(int id) {
		ModelAndView mv = new ModelAndView();
		Community c = comDAO.getCommunity(id);
		mv.addObject("group", c);
		
		List<User> groupUsers = comDAO.getUsers(c.getId()); 
		mv.addObject("groupUsers", groupUsers);
		mv.addObject("groupSize", groupUsers.size());
		mv.setViewName("views/grouphome.jsp");
		return mv;
	}
}
