package ar.edu.itba.it.paw.domain;

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
		return find("");  //TODO: copy query
	}

	@Override
	public void registerMovie(Movie movie) {
		super.save(movie);
	}

	@Override
	public Movie getMovie(int id) {
		return super.get(Movie.class, id);
	}
}
