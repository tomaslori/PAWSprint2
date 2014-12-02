package ar.edu.itba.it.paw.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String arg0) {
		try {
			return new SimpleDateFormat("yyyy-MM-DD").parse(arg0);
		} catch (ParseException e) { return new Date(); }
	}
}
