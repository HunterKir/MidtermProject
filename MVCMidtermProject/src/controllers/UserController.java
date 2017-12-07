package controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import data.ItemDAO;
import data.UserDAO;
import entities.Item;
import entities.User;

@Controller
public class UserController {
	
	@Autowired
	private UserDAO dao;
	
	@Autowired 
	private ItemDAO iDAO; 
	
	@RequestMapping(path="login.do", method=RequestMethod.POST)
	public ModelAndView userLogIn(@Valid User user, Errors errors, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		if (errors.getErrorCount() != 0) {
		//Check for form errors
			mv.setViewName("views/login.jsp");
			return mv;
		}
		else {
			//Verify Log-in
			User retrievedUser = dao.getLoadedUser(user.getUsername()); 
			if(retrievedUser != null && retrievedUser.getPassword().equals(user.getPassword())) {
				session.setAttribute("activeUser", retrievedUser);
				mv.addObject("user", retrievedUser);
				mv.setViewName("views/userHome.jsp");
				List<Item> itemsList = iDAO.getAllItemsInAllCommunitiesByUserLimit10(retrievedUser);				
				mv.addObject("itemsList", itemsList); 
				return mv;
			}
			else {
			//Failed log-in
				mv.setViewName("views/login.jsp");
				return mv;
			}
		}	
	}
	
	@RequestMapping(path="login.do", method=RequestMethod.GET)
	public ModelAndView goToLoginPage(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		User sessionUser = (User) session.getAttribute("activeUser");
		if(sessionUser != null) {
			User retrievedUser = dao.getLoadedUser(sessionUser.getUsername());
			session.setAttribute("activeUser", retrievedUser);
			mv.addObject("user", retrievedUser);
			mv.setViewName("views/userHome.jsp");
			return mv;
		}
		User u = new User();
		mv.setViewName("views/login.jsp");
		mv.addObject("user", u);
		return mv;
	}
	@RequestMapping(path="logout.do", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		session.setAttribute("activeUser", null);
		return "home.do";
	}
	
	@RequestMapping(path="newuser.do", method=RequestMethod.GET)
	public ModelAndView goToNewUserPage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("activeUser"); 
		ModelAndView mv = new ModelAndView();
		
		//If user exist send them away to their home page
		if(user != null) {
			mv.addObject("user", user);
			mv.setViewName("redirect: views/userHome.jsp");
			return mv; 
		}
		
		User u = new User();
		mv.setViewName("views/newuser.jsp");
		mv.addObject("user", u);
		return mv;
	}
	
	@RequestMapping(path="newuser.do", method=RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, Errors errors, Model model, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		if (errors.hasErrors()) {
			mv.setViewName("views/newuser.jsp");
			return mv;
		}
		else if(dao.getUserByUserName(user.getUsername()) != null){
			mv.setViewName("views/newuser.jsp");
			mv.addObject("userExist", "Username already exists pick a new username");
			return mv;	
		}
		if(dao.createUser(user) != null) {
			user = dao.getLoadedUser(user.getUsername());
			session.setAttribute("activeUser", user);
		}
		mv.addObject("user", user);
		mv.setViewName("views/userHome.jsp");
		return mv;
	}
	@RequestMapping(path="viewProfile.do", method=RequestMethod.GET)
	public ModelAndView viewProfile(@RequestParam("userId")int userId) {
		ModelAndView mv = new ModelAndView(); 
		mv.setViewName("views/viewProfile.jsp");
		User viewedUser = dao.getUser(userId); 
		viewedUser = dao.getLoadedUser(viewedUser.getUsername());
		mv.addObject("viewedUser", viewedUser); 
		return mv; 
	}
	
	@RequestMapping(path="updateUser.do")
	public ModelAndView updateUser(@Valid User user, Errors errors, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		User currentUser = (User) session.getAttribute("activeUser");
		if (user.getId() > 0) {
			if (currentUser.getUsername().equalsIgnoreCase(user.getUsername())) {
				User updated = dao.updateUser(user.getId(), user);
				if (updated != null) {
					session.setAttribute("activeUser", updated);
					mv.setViewName("redirect:login.do");
					return mv;
				}
			}
			else if(dao.getUserByUserName(user.getUsername()) != null){
				mv.setViewName("views/userHome.jsp");
				mv.addObject("usernameError", "Username already exists pick a new username");
				return mv;	
			}
			User updated = dao.updateUser(user.getId(), user);
			if (updated != null) {
				session.setAttribute("activeUser", updated);
				mv.setViewName("redirect:login.do");
				return mv;
			}
		}
		mv.setViewName("views/userHome.jsp");
		return mv;
	}
}
