package ar.edu.itba.it.paw.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.domain.Movie;
import ar.edu.itba.it.paw.domain.MovieRepo;

@Component
public class StringToMovieConverter implements Converter<String, Movie> {

	private MovieRepo movieRepo;

	@Autowired
	public StringToMovieConverter(MovieRepo movieRepo) {
		this.movieRepo = movieRepo;
	}

	@Override
	public Movie convert(String arg0) {
		return movieRepo.getMovie(arg0);
	}
}
