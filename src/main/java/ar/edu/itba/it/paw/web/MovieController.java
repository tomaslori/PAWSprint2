package ar.edu.itba.it.paw.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.Movie;
import ar.edu.itba.it.paw.domain.MovieRepo;
import ar.edu.itba.it.paw.domain.Review;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;

@Controller
public class MovieController {
	
	private static final int MAX_DESCRIPTION_LENGTH = 124;
	private static final int MIN_RATING = 0;
	private static final int MAX_RATING = 5;

	private UserRepo userRepo;
	private MovieRepo movieRepo;

	@Autowired
	public MovieController(UserRepo userRepo, MovieRepo movieRepo) {
		this.userRepo = userRepo;
		this.movieRepo = movieRepo;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView details(
			@RequestParam(value = "movie", required = true) Movie movie,
			HttpSession s) {
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(movie);
		mav.addObject("reviews", getReviews(movie.getComments()));
		
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
	public ModelAndView details(
			@RequestParam(value = "commentMovie", required = true) Movie movie,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "rating", required = false) int rating,
			HttpSession s) {

		ModelAndView mav = new ModelAndView();
		mav.addObject(movie);
		mav.addObject("reviews", getReviews(movie.getComments()));
		
		boolean commentError = false;
		String error = null;
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
					movieRepo.registerMovie(movie);
				}
				
				if(commentError)
					mav.addObject("commentError", error);
			}
		}
		return mav;
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView delete(
			@RequestParam(value = "commentMovie", required = true) Movie movie,
			@RequestParam(value = "commentOwner", required = true) User commentOwner,
			HttpSession s) {

		ModelAndView mav = new ModelAndView("movie/details");
		movie.removeCommentFrom(commentOwner);
		movieRepo.registerMovie(movie);
		mav.addObject(movie);
		mav.addObject("reviews", getReviews(movie.getComments()));
		
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
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView review(
			@RequestParam(value = "commentMovie", required = true) Movie movie,
			@RequestParam(value = "commentOwner", required = true) User commentOwner,
			@RequestParam(value = "rating", required = false) int rating,
			HttpSession s) {

		ModelAndView mav = new ModelAndView("movie/details");
		mav.addObject(movie);
		mav.addObject("reviews", getReviews(movie.getComments()));
		
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
					movieRepo.registerMovie(movie);
				}
				
				if(reviewError)
					mav.addObject("reviewError", error);
				
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
	
	@RequestMapping(value={"/","/home"}, method = RequestMethod.GET)
	public ModelAndView home() {
		
		ModelAndView mav = new ModelAndView("home");
		List<Movie> topMovies = movieRepo.getTop5();
		List<Float> topRatings = getRatings(topMovies);
		List<Movie> allMovies = movieRepo.getAllMovies();
		Collections.sort(allMovies);
		mav.addObject("topmovies", topMovies);
		mav.addObject("topratings", topRatings);
		mav.addObject("movies", allMovies);
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

	
}
