package ar.edu.itba.it.paw.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateMovieRepo extends AbstractHibernateRepo implements
		MovieRepo {

	@Autowired
	public HibernateMovieRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Movie getMovie(String name) {
		List<Movie> results = find(" from Movie where name = ?", name);
		return results.size() > 0 ? results.get(0) : null;
	}

	@Override
	public List<Movie> getMoviesFrom(String director) {
		return find(" from Movie where director = ?", director);
	}
	
	@Override
	public List<Movie> getAllMovies() {
		return find(" from Movie");
	}
	
	@Override
	public List<Movie> getTop5() {
		List<Movie> results = getAllMovies();
		
		Comparator<Movie> comp = new Comparator<Movie>() {

			@Override
			public int compare(Movie m1, Movie m2) {
				
				Float rating1 = 0.f;
				for (Comment c : m1.getComments())
					rating1 += c.getRating();
				rating1 /= m1.getComments().size();
				
				Float rating2 = 0.f;
				for (Comment c : m2.getComments())
					rating2 += c.getRating();
				rating2 /= m2.getComments().size();
				
				return (int)((rating2 - rating1)/Math.abs(rating2 - rating1));
			}
			
		};
		
		Collections.sort(results, comp); 
		List<Movie> ret = new ArrayList<Movie>();
		for (int i=0 ; i<5 && i<results.size() ; i++)
			ret.add(results.get(i));
		
		return ret;
	}

	@Override
	public void registerMovie(Movie movie) {
		super.save(movie);
	}

	@Override
	public Movie getMovie(int id) {
		return super.get(Movie.class, id);
	}

	@Override
	public Movie getSuggestion(Genre genre, int minRating, int minUsers) {
		List<Movie> allMovies = getAllMovies();
		List<Movie> results = new ArrayList<Movie>();
		for (Movie movie :allMovies)
			if(checkGenre(movie, genre) && checkRatings(movie, minRating, minUsers))
				results.add(movie);
		
		return (results.size()==0)? null : results.get((int)(Math.random()*results.size()));
	}
	
	private boolean checkGenre(Movie movie, Genre genre) {
		for (Genre g :movie.getGenres())
			if(genre.equals(g))
				return true;
		return false;
	}
	
	private boolean checkRatings(Movie movie, int minRating, int minUsers) {
		int sum = 0;
		for (Comment c :movie.getComments()) {
			if (c.getRating() >= minRating)
				sum++;
		}
		return sum>=minUsers;
	}
}
