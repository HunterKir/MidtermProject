package controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import data.UserDAO;
import entities.User;

@Controller
public class UserController {
	
	@Autowired
	private UserDAO dao;
	
	@RequestMapping(path="login.do", method=RequestMethod.POST)
	public ModelAndView userLogIn(@Valid User user, Errors errors) {
		ModelAndView mv = new ModelAndView();
		if (errors.getErrorCount() != 0) {
			mv.setViewName("views/login.jsp");
			return mv;
		}
		mv.setViewName("home.jsp");
		return mv;
	}
	
	@RequestMapping(path="login.do", method=RequestMethod.GET)
	public ModelAndView goToLoginPage() {
		ModelAndView mv = new ModelAndView();
		User u = new User();
		mv.setViewName("views/login.jsp");
		mv.addObject("user", u);
		return mv;
	}
	
	@RequestMapping(path="newuser.do", method=RequestMethod.GET)
	public ModelAndView goToNewUserPage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("activeUser"); 
		ModelAndView mv = new ModelAndView();
		
		//If user exist send them away to their home page
		if(user != null) {
			mv.setViewName("redirect: views/userHome.jsp");
			return mv; 
		}
		
		User u = new User();
		mv.setViewName("views/newuser.jsp");
<<<<<<< HEAD
		model.addAttribute("user", u);
=======
		mv.addObject("user", u);
>>>>>>> f6622a1196b998dd63a1bface931fa8e009595c8
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
			session.setAttribute("activeUser", user);
		}
		mv.setViewName("views/userHome.jsp");
		return mv;
	}
}
