package ar.edu.itba.it.paw.command;

import java.io.IOException;
import java.util.Date;

import ar.edu.itba.it.paw.domain.User;

public class UserForm {

	private User user;
	private String name;
	private String surname;
	private String email;
	private String password;
	private String confirmPassword;
	private String secretQuestion;
	private String secretAnswer;
	private Date birthDate;

	public UserForm() {
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
			this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
			this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
			this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
			this.password = password;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getSecretQuestion() {
		return secretQuestion;
	}

	public void setSecretQuestion(String secretQuestion) {
			this.secretQuestion = secretQuestion;
	}

	public String getSecretAnswer() {
		return secretAnswer;
	}

	public void setSecretAnswer(String secretAnswer) {
			this.secretAnswer = secretAnswer;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
			this.birthDate = birthDate;
	}
	

	public User build() throws IOException {
		return new User(name, surname, email, password,	secretQuestion, secretAnswer, birthDate, false, false);
	}
}
