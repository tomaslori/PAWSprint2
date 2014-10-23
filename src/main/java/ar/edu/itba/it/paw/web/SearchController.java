package ar.edu.itba.it.paw.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;

@Controller
public class SearchController {

	UserRepo userRepo;

	@Autowired
	public SearchController(UserRepo userService) {
		this.userRepo = userService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String results() {
		return "search/results";
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView results(
			@RequestParam(value = "search", required = false) String search) {
		ModelAndView mav = new ModelAndView();
		if (search == null) {
			search = " ";
		}
		List<User> users = userRepo.getUsersWithName(search);
		mav.addObject("users", users);
		mav.addObject("search", search);
		return mav;
	}
}
