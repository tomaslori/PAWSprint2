package ar.edu.itba.it.paw.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.command.UserForm;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.validator.UserFormValidator;

@Controller
public class UserController {

	private UserRepo userRepo;
	private UserFormValidator userFormValidator;
	
	private static final int MIN_PASSWORD_LENGTH = 8;
	private static final int MAX_PASSWORD_LENGTH = 16;

	@Autowired
	public UserController(UserRepo userRepo, UserFormValidator userFormValidator) {
		this.userRepo = userRepo;
		this.userFormValidator = userFormValidator;
	}
	

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView login(HttpSession s) {
		ModelAndView mav = new ModelAndView();
		if (s.getAttribute("email") != null) {
			mav.setViewName("/user/profile");
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView login(
			@RequestParam(value = "email", required = false) User user,
			@RequestParam(value = "password", required = false) String password,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		if (user != null && user.getPassword().equals(password)) {
			session.setAttribute("email", user.getEmail());
			mav.setViewName("redirect:./profile");
		} else {
			mav.addObject("error", "Invalid user or password.");
			mav.setViewName("user/login");
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String logout(HttpSession s) {
		s.removeAttribute("email");
		return "redirect:./login";
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView profile(HttpSession s) {
		ModelAndView mav = new ModelAndView();
		String userSessionString = (String) s.getAttribute("email");
		
		if (userSessionString == null)
			mav.setViewName("redirect:./login");
		else 
			mav.addObject("user", userRepo.getUser(userSessionString));

		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("userForm", new UserForm());
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String registration(HttpServletRequest req, UserForm userForm,
			Errors errors, HttpSession session) {

		userFormValidator.validate(userForm, errors);

		if (errors.hasErrors())
			return null;

		User user = userForm.build();
		if (userRepo.getUser(user.getEmail()) == null)
			userRepo.registerUser(user);
		else {
			errors.rejectValue("email", "duplicated");
			return null;
		}
		session.setAttribute("email", userForm.getEmail());
		return "redirect:profile";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView changePassword(
			@RequestParam(value = "userToRecover", required = false) User user,
			@RequestParam(value = "userSelected", required = false) Boolean userSelected)  {
		
		ModelAndView mav = new ModelAndView();
		if (user != null) {
			mav.addObject("success", "Recovering password for " + user.getEmail());
			mav.addObject("user", user);
			mav.addObject("userSelected", true);
		} else {
			if (userSelected != null)
				mav.addObject("error", "Invalid user email.");
			mav.addObject("userSelected", false);
		}
			
		return mav;
	}

	
	@RequestMapping(value = "/user/changePassword", method = RequestMethod.POST)
	public ModelAndView recover(
			@RequestParam(value = "secretAnswer", required = false) String secretAnswerSubmited,
			@RequestParam(value = "password", required = false) String newPassword,
			@RequestParam(value = "confirm", required = false) String newPasswordConfirm,
			@RequestParam(value = "userToRecover", required = false) User user) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("userSelected", true);
		if (secretAnswerSubmited != null) {
			boolean matches = user.getSecretAnswer().equals(
					secretAnswerSubmited);
			boolean passwordMatches = false;
			if (newPassword != null && newPasswordConfirm != null) {
				passwordMatches = newPassword.equals(newPasswordConfirm);
			}
			if (!matches) {
				mav.addObject("error", "Wrong secret Answer");
				mav.addObject("success", "Recovering password for " + user.getEmail());
				mav.addObject("user", user);
			} else {
				if (passwordMatches && newPassword.length() <= MAX_PASSWORD_LENGTH && newPassword.length() >= MIN_PASSWORD_LENGTH) {
					mav.addObject("passwordRecovered", true);
					mav.addObject("success",
							"Your password was changed successfully!");
					user.setPassword(newPassword);
					userRepo.registerUser(user);
				} else {
					mav.addObject("error", "Passwords dont match or have less than 8 characters or more than 16.");
					mav.addObject("success", "Recovering password for " + user.getEmail());
					mav.addObject("user", user);
				}
			}
		}
		return mav;
	}
	

/*


	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView editProfile(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String username = (String) session.getAttribute("username");
		User userSession = userRepo.getUser(username);
		EditUserForm editUserForm = new EditUserForm(userSession);
		mav.addObject(editUserForm);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String editProfile(EditUserForm editUserForm, Errors errors)
			throws IOException {
		editUserFormValidator.validate(editUserForm, errors);
		if (errors.hasErrors()) {
			return null;
		}
		User oldUser = userRepo.getUser(editUserForm.getId());
		editUserForm.update(oldUser);
		return "redirect:home";
	}


	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public byte[] image(
			@RequestParam(value = "username", required = false) User user) {
		return user.getPicture();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView suggestedUsers(HttpSession session)
			throws InvalidPropertiesFormatException, IOException {
		ModelAndView mav = new ModelAndView();
		String username = (String) session.getAttribute("username");
		User user = userRepo.getUser(username);
		if (user != null) {
			List<User> suggestedUsers = getSuggestedFriends(user);
			mav.addObject("suggestedUsers", suggestedUsers);
		}
		return mav;
	}
	*/
}
