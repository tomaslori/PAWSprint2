package ar.edu.itba.it.paw.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToIntegerConverter implements Converter<String, Integer> {

	@Override
	public Integer convert(String arg0) {
		try {
			return Integer.parseInt(arg0);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
