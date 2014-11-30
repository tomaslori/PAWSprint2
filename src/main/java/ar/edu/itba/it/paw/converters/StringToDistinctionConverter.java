package ar.edu.itba.it.paw.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.domain.Distinction;

@Component
public class StringToDistinctionConverter implements Converter<String, Distinction> {

	@Override
	public Distinction convert(String arg0) {
		System.out.println("Converting Distinction with description: " + arg0);
		if(arg0 != null)
			return new Distinction(arg0, false);
		return null;
	}
}
