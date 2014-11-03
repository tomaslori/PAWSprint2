package ar.edu.itba.it.paw.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.domain.Movie;
import ar.edu.itba.it.paw.domain.MovieRepo;

@Controller
public class MovieController {

	MovieRepo movieRepo;

	@Autowired
	public MovieController(MovieRepo movieRepo) {
		this.movieRepo = movieRepo;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView details(
			@RequestParam(value = "movie", required = true) Movie movie) {
		ModelAndView mav = new ModelAndView();
		mav.addObject(movie);
		return mav;
	}
	
	@RequestMapping(value={"/","/home"}, method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("home");
		List<Movie> topmovies = movieRepo.getAllMovies(); //TODO: change after implementing TOP5
		List<Movie> allmovies = movieRepo.getAllMovies();
		mav.addObject("topmovies", topmovies);
		mav.addObject("movies", allmovies);
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView search(
			@RequestParam(value = "director", required = true) String director) {
		ModelAndView mav = new ModelAndView("movie/results");
		mav.addObject("director", director);
		List<Movie> movies = movieRepo.getMoviesFrom(director);
		mav.addObject("movies", movies);
		return mav;
	}

	
}
