package ar.edu.itba.it.paw.domain;

import java.util.List;

public interface MovieRepo {
	
	public Movie getMovie(String name);

	public List<Movie> getMoviesFrom(String director);
	
	public List<Movie> getAllMovies();
	
	public List<Movie> getTop5();

	public void registerMovie(Movie movie);
	
	public Movie getMovie(int id);
	
	public Movie getSuggestion(Genre genre, int minRating, int minUsers);
}
