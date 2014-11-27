package ar.edu.itba.it.paw.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.it.paw.command.MovieForm;
import ar.edu.itba.it.paw.command.UserForm;

@Component
public class MovieFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) throws IllegalStateException {
		MovieForm movieForm = (MovieForm) target;
		checkEmptyInputs(movieForm, errors);
		checkInputLength(movieForm, errors);
		if (movieForm.getDuration() <= 0) {
			errors.rejectValue("duration", "invalid");
		}
	}

	private void checkInputLength(MovieForm movieForm, Errors errors) {
		if (movieForm.getName().length() == 0) {
			errors.rejectValue("name", "empty");
		}
		if (movieForm.getDirector().length() == 0) {
			errors.rejectValue("director", "empty");
		}
		if (movieForm.getDescription().length() == 0) {
			errors.rejectValue("description", "empty");
		}	
	}
	

	private void checkEmptyInputs(MovieForm movieForm, Errors errors) {
		if (movieForm.getName() == null) {
			errors.rejectValue("name", "empty");
		}
		if (movieForm.getDirector() == null) {
			errors.rejectValue("director", "empty");
		}
		if (movieForm.getDescription() == null) {
			errors.rejectValue("description", "empty");
		}
		if (movieForm.getReleaseDate() == null)
			errors.rejectValue("releaseDate", "empty");
	}
}
