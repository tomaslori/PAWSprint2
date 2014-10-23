package ar.edu.itba.it.paw.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;

@Component
public class StringToUserConverter implements Converter<String, User> {

	private UserRepo userRepo;

	@Autowired
	public StringToUserConverter(UserRepo userService) {
		this.userRepo = userService;
	}

	@Override
	public User convert(String arg0) {
		return userRepo.getUser(arg0);
	}
}
