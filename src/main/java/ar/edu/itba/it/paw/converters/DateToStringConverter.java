package ar.edu.itba.it.paw.converters;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DateToStringConverter implements Converter<Date, String> {

	@Override
	public String convert(Date date) {
		if (date == null)
			return "";
		
		int day =  date.getDate();
		int month = date.getMonth() +1;
		int year = date.getYear() + 1900;
		String dateStr = year + "-" + ((month<10)? "0"+month:month) + "-" + ((day<10)? "0"+day : day);
		return dateStr;
	}
}
