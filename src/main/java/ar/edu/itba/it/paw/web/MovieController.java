package ar.edu.itba.it.paw.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.command.MovieForm;
import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.Distinction;
import ar.edu.itba.it.paw.domain.Genre;
import ar.edu.itba.it.paw.domain.Movie;
import ar.edu.itba.it.paw.domain.MovieRepo;
import ar.edu.itba.it.paw.domain.Review;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.validator.MovieFormValidator;

@Controller
public class MovieController {
	
	private static final int MAX_DESCRIPTION_LENGTH = 124;
	private static final int MIN_RATING = 0;
	private static final int MAX_RATING = 5;
	
	@Value( "${sugg.minRating}" )
	private int MIN_SUG_RATING;
	@Value( "${sugg.minUsers}" )
	private int MIN_USERS;

	private UserRepo userRepo;
	private MovieRepo movieRepo;
	private MovieFormValidator movieFormValidator;

	@Autowired
	public MovieController(UserRepo userRepo, MovieRepo movieRepo, MovieFormValidator movieFormValidator) {
		this.userRepo = userRepo;
		this.movieRepo = movieRepo;
		this.movieFormValidator = movieFormValidator;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView details(
			@RequestParam(value = "movie", required = true) Movie movie,
			@RequestParam(value = "commentError", required = false) String commentError,
			@RequestParam(value = "reviewError", required = false) String reviewError,
			HttpSession s) {
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(movie);
		mav.addObject("reviews", getReviews(movie.getComments()));
		if (commentError != null)
			mav.addObject("commentError", commentError);
		if (reviewError != null)
			mav.addObject("reviewError", reviewError);
		
		String email = (String) s.getAttribute("email");
		if (email != null) {
			User user = userRepo.getUser(email);
			if (user != null) {
				if (user.getAdmin())
					mav.addObject("isAdmin", true);
					
				boolean canComment = movie.getCommentFrom(user) == null;
				List<Boolean> canReview = checkCanReview(movie.getComments(), user);
				
				mav.addObject("canReview", canReview);
				if (canComment)
					mav.addObject("canComment",true);
				else
					mav.addObject("alreadyCommented", true);
				
			}
		}
		return mav;
	}
	
	private List<Float> getReviews(List<Comment> comments) {
		
		List<Float> reviews = new ArrayList<Float>(comments.size());
		for (Comment c : comments) {
			int sum = 0;
			for (Review r : c.getReviews()) {
				sum += r.getRating();
			}
			if (sum != 0)
				reviews.add(sum/(float)c.getReviews().size());
			else
				reviews.add(0.f);
		}
		return reviews;
	}
	
	private List<Boolean> checkCanReview(List<Comment> comments, User user) {
		List<Boolean> canReview = new ArrayList<Boolean>();
		
		for(Comment c : comments) {
			if(c.getOwner().equals(user) || c.getReviewFrom(user) != null)
				canReview.add(false);
			else
				canReview.add(true);
		}
		return canReview;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String details(
			@RequestParam(value = "commentMovie", required = true) Movie movie,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "rating", required = false) int rating,
			HttpSession s) {

		String error = null;
		boolean commentError = false;
		String email = (String) s.getAttribute("email");
		
		if (email != null) {
			User user = userRepo.getUser(email);
			if (user != null) {
				
				if (description == null || description.equals("")) {
					commentError = true;
					error = "Empty description.";
				} else if (description.length() > MAX_DESCRIPTION_LENGTH) {
					commentError = true;
					error = "Description is too long.";
				} else if (rating < MIN_RATING || rating > MAX_RATING) {
					commentError = true;
					error = "Invalid rating, must be an integer between 0 and 5";
				} else {
					Comment c = new Comment(user, movie, rating, description, new ArrayList<Review>());
					movie.addComment(c);
					
					for (Comment comm : movie.getComments())
						System.out.println(comm.getOwner().getEmail() + ": " + comm.getDescription());
				}
			}
		}
		
		String ret;
		if(commentError)
			ret = "redirect:./details?movie=" + movie.getName() + "&commentError=" + error;
		else
			ret = "redirect:./details?movie=" + movie.getName();

		return ret;
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public String delete(
			@RequestParam(value = "commentMovie", required = true) Movie movie,
			@RequestParam(value = "commentOwner", required = true) User commentOwner,
			HttpSession s) {

		String email = (String) s.getAttribute("email");
		
		if (email != null) {
			User user = userRepo.getUser(email);
			if (user != null && user.getAdmin()) {
		
				movie.removeCommentFrom(commentOwner);
			}
		}
		return "redirect:./details?movie=" + movie.getName();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String deleteGenre(
			@RequestParam(value = "movie", required = true) Movie movie,
			@RequestParam(value = "genre", required = true) Genre genre,
			HttpSession s) {

		String email = (String) s.getAttribute("email");
		
		if (email != null) {
			User user = userRepo.getUser(email);
			if (user != null && user.getAdmin()) {
				movie.removeGenre(genre);
			}
		}
		return "redirect:./edit?movie=" + movie.getName();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String deleteDistinction(
			@RequestParam(value = "movie", required = true) Movie movie,
			@RequestParam(value = "distinction", required = true) Distinction distinction,
			HttpSession s) {

		String email = (String) s.getAttribute("email");
		
		if (email != null) {
			User user = userRepo.getUser(email);
			if (user != null && user.getAdmin())
				movie.removeDistinction(distinction);
		}
		return "redirect:./edit?movie=" + movie.getName();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String addGenre(
			@RequestParam(value = "movie", required = true) Movie movie,
			@RequestParam(value = "genre", required = false) Genre genre,
			HttpSession s) {

		String email = (String) s.getAttribute("email");
		
		if (email != null) {
			User user = userRepo.getUser(email);
			if (user != null && user.getAdmin()) {
				if (genre == null) {
					String error = "Error! Genre field was empty.";
					return "redirect:./edit?movie=" + movie.getName() + "&error=" + error;	
				} else {
					movie.addGenre(genre);
				}
			}
		}
		return "redirect:./edit?movie=" + movie.getName();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String addDistinction(
			@RequestParam(value = "movie", required = true) Movie movie,
			@RequestParam(value = "distinction", required = false) Distinction distinction,
			HttpSession s) {

		String email = (String) s.getAttribute("email");
		
		if (email != null) {
			User user = userRepo.getUser(email);
			if (user != null && user.getAdmin()) {
				System.out.println("validations ok!");
				if (distinction == null) {
					String error = "Error! Distinction field was empty.";
					return "redirect:./edit?movie=" + movie.getName() + "&error=" + error;	
				} else {
					System.out.println("adding distinction!");
					movie.addDistinction(distinction);
				}
			}
		}	
		return "redirect:./edit?movie=" + movie.getName();
	}
	

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView edit(
			@RequestParam(value = "movie", required = false) Movie movie,
			@RequestParam(value = "error", required = false) String error,			
			HttpSession s) {

		String email = (String) s.getAttribute("email");
		ModelAndView mav = new ModelAndView();
		
		if (email != null) {
			User user = userRepo.getUser(email);
			if (user != null && user.getAdmin()) {
				MovieForm movieForm;
				if (movie != null)
					movieForm = new MovieForm(movie);
				else
					movieForm = new MovieForm();
				
				mav.addObject("movieForm", movieForm);
				mav.addObject("isEdit", (movie!=null));
				
				if (error != null)
					mav.addObject("error", error);
			}
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String edit(HttpServletRequest req, MovieForm movieForm,
			Errors errors, HttpSession session) {

		String email = (String) session.getAttribute("email");
		
		if (email != null) {
			User user = userRepo.getUser(email);
			if (user != null && user.getAdmin()) {
				movieFormValidator.validate(movieForm, errors);
		
				if (errors.hasErrors())
					return null;
		
				Movie oldMovie = movieRepo.getMovie(movieForm.getName());
				Movie newMovie = movieForm.build();
				if (oldMovie != null) {
					oldMovie.updateData(newMovie);
					movieRepo.registerMovie(oldMovie);
				} else {
					movieRepo.registerMovie(newMovie);
				}
			}
		}
		return "redirect:./edit?movie=" + movieForm.getName();
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public byte[] image(
			@RequestParam(value = "movie", required = true) Movie movie) {
		System.out.println("getting an img request for " + movie.getName());
		return movie.getImage();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String changeImage(
			@RequestParam(value = "movie", required = true) Movie movie,
			@RequestParam("file") MultipartFile file, HttpSession session) {
		
		String error = null;
		String email = (String) session.getAttribute("email");
		
		if (email != null) {
			User user = userRepo.getUser(email);
			if (user != null && user.getAdmin()) {
				if (!file.isEmpty()) {
					try {
						if (file.getOriginalFilename().endsWith(".jpg")) {
							byte[] imageInBytes = file.getBytes();
							movie.setImage(imageInBytes);
						}
						else
							error = "Invalid image file";
					} catch (Exception e) {
						error = "Invalid image file";
					}
				} else
					error = "file is empty";
			}
		}
	            
		if (error == null)
			return "redirect:./edit?movie=" + movie.getName();
		else
			return "redirect:./edit?movie=" + movie.getName() +"&error=" + error;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String deleteImage(
			@RequestParam(value = "movie", required = true) Movie movie,
			HttpSession session) {
		
		String email = (String) session.getAttribute("email");
		
		if (email != null) {
			User user = userRepo.getUser(email);
			if (user != null && user.getAdmin())
				movie.setImage(null);
		}     
		return "redirect:./edit?movie=" + movie.getName();
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String review(
			@RequestParam(value = "commentMovie", required = true) Movie movie,
			@RequestParam(value = "commentOwner", required = true) User commentOwner,
			@RequestParam(value = "rating", required = false) int rating,
			HttpSession s) {
		
		boolean reviewError = false;
		String error = null;
		String email = (String) s.getAttribute("email");
		if (email != null) {
			User user = userRepo.getUser(email);
			if (user != null) {
				Comment c = movie.getCommentFrom(commentOwner);
				if (user.equals(commentOwner)) {
					reviewError = true;
					error = "Can't review your own comment";
				} else if (c == null || c.getReviewFrom(user) != null) {
					reviewError = true;
					error = "Can't review a comment twice";
				}else if (rating < MIN_RATING || rating > MAX_RATING) {
					reviewError = true;
					error = "Invalid rating, must be an integer between 0 and 5";
				} else {
					Review r = new Review(user, movie, c, rating);
					c.addReview(r);
				}
				
			}
		}
		
		String ret;
		if(reviewError)
			ret = "redirect:./details?movie=" + movie.getName() + "&reviewError=" + error;
		else
			ret = "redirect:./details?movie=" + movie.getName();
		
		return ret;
	}
	
	@RequestMapping(value={"/","/home"}, method = RequestMethod.GET)
	public ModelAndView home(HttpSession s) {
		
		ModelAndView mav = new ModelAndView("home");
		List<Movie> topMovies = movieRepo.getTop5();
		List<Float> topRatings = getRatings(topMovies);
		List<Movie> allMovies = movieRepo.getAllMovies();
		Collections.sort(allMovies);
		mav.addObject("topmovies", topMovies);
		mav.addObject("topratings", topRatings);
		mav.addObject("movies", allMovies);
		
		String email = (String) s.getAttribute("email");
		if (email != null) {
			User user = userRepo.getUser(email);
			if (user != null && user.getAdmin())
				mav.addObject("isAdmin", true);
			
		}
		return mav;
	}
	
	private List<Float> getRatings(List<Movie> movies) {
		List<Float> ratings = new ArrayList<Float>(movies.size());
		for (Movie movie : movies) {
			int sum = 0;
			for (Comment c : movie.getComments()) {
				sum += c.getRating();
			}
			if (sum != 0)
				ratings.add(sum/(float)movie.getComments().size());
			else
				ratings.add(0.f);
		}
		return ratings;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView search(
			@RequestParam(value = "director", required = true) String director) {
		ModelAndView mav = new ModelAndView("movie/results");
		mav.addObject("director", director);
		List<Movie> movies = movieRepo.getMoviesFrom(director);
		mav.addObject("movies", movies);
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String suggestion(
			@RequestParam(value = "genre", required = true) Genre genre) {
		
		Movie movie = movieRepo.getSuggestion(genre, MIN_SUG_RATING, MIN_USERS);
		if (movie == null)
			return "redirect:../home";
		return "redirect:./details?movie=" + movie.getName();
	}
	
}
