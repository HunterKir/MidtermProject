package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
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
			mv.setViewName("login.jsp");
			return mv;
		}
		mv.setViewName("home.jsp");
		return mv;
	}
	
	@RequestMapping(path="login.do", method=RequestMethod.GET)
	public ModelAndView goToLoginPage() {
		ModelAndView mv = new ModelAndView();
		User u = new User();
		mv.setViewName("login.jsp");
		mv.addObject("user", u);
		return mv;
	}
}
