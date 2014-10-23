package ar.edu.itba.it.paw.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.it.paw.command.UserForm;

@Component
public class UserFormValidator implements Validator {

	private static final int MAX_EMAIL_LENGTH = 64;
	private static final int MAX_STRING_LENGTH = 32;
	private static final int MIN_PASSWORD_LENGTH = 8;
	private static final int MAX_PASSWORD_LENGTH = 16;
	private static final int MAX_QUE_AND_ANS_LENGTH = 64;

	@Override
	public boolean supports(Class<?> clazz) {
		return UserForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) throws IllegalStateException {
		UserForm userForm = (UserForm) target;
		checkEmptyInputs(userForm, errors);
		checkInputLength(userForm, errors);
		if(userForm.getPassword().length() > MIN_PASSWORD_LENGTH 
				&& userForm.getPassword().length() < MAX_PASSWORD_LENGTH) { 
			checkPasswordConfirm(userForm, errors);
		}
	}

	private void checkPasswordConfirm(UserForm userForm, Errors errors) {
		if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
			errors.rejectValue("password", "nonmatch");
		}
	}

	private void checkInputLength(UserForm userForm, Errors errors) {
		if (userForm.getName().length() > MAX_STRING_LENGTH
				|| userForm.getName().length() == 0) {
			errors.rejectValue("name", "length");
		}
		if (userForm.getSurname().length() > MAX_STRING_LENGTH
				|| userForm.getSurname().length() == 0) {
			errors.rejectValue("surname", "length");
		}
		if (userForm.getEmail().length() > MAX_EMAIL_LENGTH
				|| userForm.getEmail().length() == 0) {
			errors.rejectValue("username", "length");
		}
		if (userForm.getPassword().length() < MIN_PASSWORD_LENGTH
				|| userForm.getPassword().length() > MAX_PASSWORD_LENGTH) {
			errors.rejectValue("password", "length");
		}
		if (userForm.getConfirmPassword().length() < MIN_PASSWORD_LENGTH
				|| userForm.getConfirmPassword().length() > MAX_PASSWORD_LENGTH) {
			errors.rejectValue("confirmPassword", "length");
		}
		if (userForm.getSecretQuestion().length() > MAX_QUE_AND_ANS_LENGTH
				|| userForm.getSecretQuestion().length() == 0) {
			errors.rejectValue("secretQuestion", "length");
		}
		if (userForm.getSecretAnswer().length() > MAX_QUE_AND_ANS_LENGTH
				|| userForm.getSecretAnswer().length() == 0) {
			errors.rejectValue("secretAnswer", "length");
		}
	}

	private void checkEmptyInputs(UserForm userForm, Errors errors) {
		if (userForm.getName() == null) {
			errors.rejectValue("name", "empty");
		}
		if (userForm.getSurname() == null) {
			errors.rejectValue("surname", "empty");
		}
		if (userForm.getEmail() == null) {
			errors.rejectValue("username", "empty");
		}
		if (userForm.getPassword() == null) {
			errors.rejectValue("password", "empty");
		}
		if (userForm.getConfirmPassword() == null) {
			errors.rejectValue("confirmPassword", "empty");
		}
		if (userForm.getSecretQuestion() == null) {
			errors.rejectValue("secretQuestion", "empty");
		}
		if (userForm.getSecretAnswer() == null) {
			errors.rejectValue("secretAnswer", "empty");
		}
		if (userForm.getBirthDate() == null)
			errors.rejectValue("birthDate", "empty");
	}
}
