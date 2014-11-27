package ar.edu.itba.it.paw.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.domain.Genre;

@Component
public class StringToGenreConverter implements Converter<String, Genre> {

	@Override
	public Genre convert(String arg0) {
		if(arg0 != null)
			return new Genre(arg0);
		return null;
	}
}
