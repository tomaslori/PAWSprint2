package ar.edu.itba.it.paw.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.command.EditUserForm;
import ar.edu.itba.it.paw.command.UserForm;
import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.CommentRepo;
import ar.edu.itba.it.paw.domain.Hashtag;
import ar.edu.itba.it.paw.domain.HashtagRepo;
import ar.edu.itba.it.paw.domain.Notification;
import ar.edu.itba.it.paw.domain.NotificationRepo;
import ar.edu.itba.it.paw.domain.RankedHashtag;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.validator.EditUserFormValidator;
import ar.edu.itba.it.paw.validator.UserFormValidator;

@Controller
public class UserController extends AbstractController {

	private UserRepo userRepo;
	private HashtagRepo hashtagRepo;
	private CommentRepo commentRepo;
	private NotificationRepo notificationRepo;
	private UserFormValidator userFormValidator;
	private EditUserFormValidator editUserFormValidator;
	private static final int MAX_COMMENT_LENGTH = 140;
	private static final int MAX_PASSWORD_LENGTH = 16;
	private static final int MIN_PASSWORD_LENGTH = 8;
	private static final int SUGGESTED_FRIEND_AMOUNT = 3;

	@Autowired
	public UserController(UserRepo userRepo, HashtagRepo hashtagRepo,
			CommentRepo commentService, NotificationRepo notificationRepo,
			UserFormValidator userFormValidator,
			EditUserFormValidator editUserFormValidator) {
		this.userRepo = userRepo;
		this.hashtagRepo = hashtagRepo;
		this.commentRepo = commentService;
		this.notificationRepo = notificationRepo;
		this.userFormValidator = userFormValidator;
		this.editUserFormValidator = editUserFormValidator;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView login(HttpSession s) {
		ModelAndView mav = new ModelAndView();
		if (s.getAttribute("username") != null) {
			mav.setViewName("/user/profile/" + s.getAttribute("username"));
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView login(
			@RequestParam(value = "username", required = false) User user,
			@RequestParam(value = "password", required = false) String password,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		if (user != null && user.getPassword().equals(password)) {
			session.setAttribute("username", user.getUsername());
			mav.setViewName("redirect:./profile/" + user.getUsername());
		} else {
			mav.addObject("error", "Invalid user or password.");
			mav.setViewName("user/login");
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String logout(HttpSession s) {
		s.removeAttribute("username");
		return "redirect:./login";
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
		// DiskFileUpload fu = new DiskFileUpload();
		userFormValidator.validate(userForm, errors);

		if (errors.hasErrors()) {
			return null;
		}

		try {
			userRepo.registerUser(userForm.build());
		} catch (Exception e) {
			errors.rejectValue("username", "duplicated");
			return null;
		}
		session.setAttribute("username", userForm.getUsername());
		return "redirect:profile/" + userForm.getUsername();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String home(HttpSession session) {
		if (session.getAttribute("username") != null) {
			return "redirect:profile/" + session.getAttribute("username");
		} else {
			return "redirect:login";
		}
	}

	@RequestMapping(value = "/user/profile/{profile}", method = RequestMethod.GET)
	public ModelAndView profile(@PathVariable User profile,
			@RequestParam(value = "period", required = false) Integer period,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String userSessionString = (String) session.getAttribute("username");
		User userSession = null;

		if (profile == null) {
			if (userSessionString != null) {
				mav.setViewName("redirect:../profile/" + userSessionString
						+ "&period=" + period);
			} else {
				mav.setViewName("redirect:../login");
			}
			return mav;
		} else {
			if (userSessionString == null) {
				if (profile.isPrivate()) {
					mav.setViewName("privacyError");
					return mav;
				}
			} else {
				session.setAttribute("username", userSessionString);
				userSession = userRepo.getUser(userSessionString);
				boolean following = userSession.isFollowing(profile);
				mav.addObject("isFollowing", following);
				mav.addObject("following", userSession.following());
				mav.addObject("followers", userSession.followedBy());
			}
			profile.visit();
			mav.addObject("notifications",
					userRepo.getUser(profile.getUsername())
							.getUncheckedNotifications());

			mav.addObject("isOwner",
					profile.getUsername().equals(userSessionString));
			mav.addObject("user", profile);
			mav.addObject("following", profile.following());
			mav.addObject("followers", profile.followedBy());
			mav.addObject("isEmptyPicture", profile.getPicture() == null);
			List<Comment> comments = profile.getComments();
			SortedSet<CommentWrapper> transformedComments = transformComments(
					comments, userSession);
			mav.addObject("comments", transformedComments);
		}
		mav.setViewName("/user/profile");
		return mav;
	}

	@RequestMapping(value = "/user/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable Integer id) {
		if (id != null) {
			Comment comment = commentRepo.getComment(id);
			if (comment != null) {
				commentRepo.delete(comment);
			}
		}
		return "redirect:../home";
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView recuthulu(
			@RequestParam(value = "user", required = true) User originalauthor,
			@RequestParam(value = "id", required = true) Integer commentid,
			@RequestParam(value = "url", required = true) String url,
			HttpSession session) {

		ModelAndView mav = new ModelAndView();
		String userSession = (String) session.getAttribute("username");
		User author = userRepo.getUser(userSession);

		Comment comment = commentRepo.getComment(commentid);
		Set<Hashtag> hashtags = new HashSet<Hashtag>(comment.getHashtags());
		Set<User> users = new HashSet<User>(comment.getReferences());

		Comment recuthulu = new Comment(author, new Date(),
				comment.getComment(), hashtags, users,
				comment.getOriginalAuthor());
		commentRepo.addComment(recuthulu);
		mav.setViewName("redirect:../" + url);

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView favourites(
			@RequestParam(value = "user", required = false) User profile,
			HttpSession session) {

		ModelAndView mav = new ModelAndView();
		String usrSession = (String) session.getAttribute("username");
		User usr = userRepo.getUser(usrSession);
		List<Comment> favouritecomments = new ArrayList<Comment>(
				profile.getFavourites());
		SortedSet<CommentWrapper> transformedComments = transformComments(
				favouritecomments, usr);
		mav.addObject("user", profile);
		mav.addObject("comments", transformedComments);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView follow(
			@RequestParam(value = "user", required = false) User profile,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String username = (String) session.getAttribute("username");
		User userSession = userRepo.getUser(username);

		Notification notification = userSession.follow(profile);
		notificationRepo.save(notification);

		mav.setViewName("redirect:../user/profile/" + profile.getUsername());
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView unfollow(
			@RequestParam(value = "user", required = false) User profile,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String username = (String) session.getAttribute("username");
		User userSession = userRepo.getUser(username);

		userSession.unfollow(profile);

		mav.setViewName("redirect:../user/profile/" + profile.getUsername());
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView follows(
			@RequestParam(value = "user", required = true) User profile,
			@RequestParam(value = "type", required = true) String type,
			HttpSession session) {

		ModelAndView mav = new ModelAndView();

		mav.addObject("username", profile.getUsername());
		mav.addObject("type", type);
		if (type.equals("Followers")) {
			mav.addObject("list", profile.getFollowers());
		} else {
			mav.addObject("list", profile.getFollowing());
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView favourite(
			@RequestParam(value = "user", required = true) User profile,
			@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "url", required = true) String url,
			HttpSession session) {

		ModelAndView mav = new ModelAndView();

		Comment comment = commentRepo.getComment(id);
		String userSession_str = (String) session.getAttribute("username");
		User userSession = userRepo.getUser(userSession_str);

		userSession.addFavourite(comment);

		mav.setViewName("redirect:../" + url);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView unfavourite(
			@RequestParam(value = "user", required = true) User profile,
			@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "url", required = true) String url,
			HttpSession session) {

		ModelAndView mav = new ModelAndView();

		Comment comment = commentRepo.getComment(id);
		String userSession_str = (String) session.getAttribute("username");
		User userSession = userRepo.getUser(userSession_str);

		userSession.removeFavourite(comment);

		mav.setViewName("redirect:../" + url);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView profile(
			@RequestParam(value = "comment", required = false) Comment comment,
			@RequestParam(value = "period", required = false) String period,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<RankedHashtag> top10;

		if (period == null) {
			top10 = hashtagRepo.topHashtags(30);
		} else {
			top10 = hashtagRepo.topHashtags(Integer.valueOf(period));
		}

		mav.addObject("ranking", top10);
		String username = (String) session.getAttribute("username");
		User user = userRepo.getUser(username);
		if (comment.getComment().length() > 0
				&& comment.getComment().length() < MAX_COMMENT_LENGTH) {
			commentRepo.addComment(comment);
		}
		mav.setViewName("redirect:./profile/" + user.getUsername());
		return mav;
	}

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
	public ModelAndView recoverPassword(
			@RequestParam(value = "userToRecover", required = false) User user) {
		ModelAndView mav = new ModelAndView();
		if (user != null) {
			mav.addObject("success",
					"Recovering password for " + user.getUsername());
			mav.addObject("user", user);
			mav.addObject("userSelected", true);
		} else {
			mav.addObject("userSelected", false);
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView recoverPassword(
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
				mav.addObject("success",
						"Recovering password for " + user.getUsername());
				mav.addObject("user", user);
			} else {
				if (passwordMatches
						&& newPassword.length() <= MAX_PASSWORD_LENGTH
						&& newPassword.length() >= MIN_PASSWORD_LENGTH) {
					mav.addObject("passwordRecovered", true);
					mav.addObject("success",
							"Your password was changed successfully!");
					user.setPassword(newPassword);
					userRepo.registerUser(user);
				} else {
					mav.addObject("error",
							"Passwords dont match or have less than 8 characters or more than 16.");
					mav.addObject("success",
							"Recovering password for " + user.getUsername());
					mav.addObject("user", user);
				}
			}
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView notifications(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String username = (String) session.getAttribute("username");
		if (username != null) {
			User user = userRepo.getUser(username);
			List<Notification> notifications = user.getNotifications();
			mav.addObject("notifications", notifications);
		} else {
			mav.setViewName("redirect:login");
		}
		return mav;
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

	private List<User> getSuggestedFriends(User user)
			throws InvalidPropertiesFormatException, IOException {
		Set<User> following = user.getFollowing();
		Bag<User> bag = new MapBag<User>();
		int n = getNValue();
		for (User each : following) {
			bag.add(each.getFollowers());
		}
		List<User> aux = new ArrayList<User>();
		List<User> ans = new ArrayList<User>();
		
		bag.getNOrGreaterMatching(n, aux);
		
		
		 int usrs_left= SUGGESTED_FRIEND_AMOUNT - randomize(aux,ans,user);		
		 if(usrs_left != 0 ){
			 ans.addAll(hashtagRepo.mostFollowed(usrs_left));
		 }		
		
		return ans;
	}

	private int randomize(List<User> aux, List<User> ans, User user) {
		Random randomGenerator = new Random();
		int i = ans.size();
		while (i < SUGGESTED_FRIEND_AMOUNT && aux.size() > 0) {
			int rand = randomGenerator.nextInt(aux.size());
			User auxUser = aux.get(rand);
			if (!user.getUsername().equals(auxUser.getUsername())) {
				ans.add(auxUser);
			}
			aux.remove(rand);
			i++;
		}
		return ans.size();
	}

	private int getNValue() throws InvalidPropertiesFormatException,
			IOException {
		File file = new File("src/main/resources/parameters.xml");
		FileInputStream fileInput = new FileInputStream(file);
		Properties properties = new Properties();
		properties.loadFromXML(fileInput);
		fileInput.close();

		int commonFollowers = Integer.parseInt(properties
				.getProperty("common-followers"));
		return commonFollowers;
	}
}
