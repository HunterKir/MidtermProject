package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import data.CommunityDAO;
import entities.Community;
import entities.Post;

@Controller
public class CommunityController {
	@Autowired
	CommunityDAO dao; 
	
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
	public ModelAndView goToPostPage() {
		ModelAndView mv = new ModelAndView();
		Post post = new Post();
		mv.setViewName("views/posts.jsp");
		mv.addObject("newPost", post);
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
		Community c = dao.getCommunity(id);
		mv.addObject("group", c);
		mv.setViewName("views/grouphome.jsp");
		return mv;
	}
}
