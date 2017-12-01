package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import data.CommunityDAO;
import entities.Community;

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
}
