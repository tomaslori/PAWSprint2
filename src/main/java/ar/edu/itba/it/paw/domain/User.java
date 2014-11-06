package ar.edu.itba.it.paw.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends PersistentEntity {

	public static final int MIN_PASSWORD_LENGTH = 8;
	public static final int MAX_PASSWORD_LENGTH = 16;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String surname;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String secretQuestion;
	
	@Column(nullable = false)
	private String secretAnswer;
	
	@Column(nullable = false)
	private Date birthDate;

	private boolean vip;
	
	private boolean admin;

	public User() { }
	
	public User(String name, String surname, String email, String password,
			String secretQuestion, String secretAnswer, Date birthDate, boolean vip, boolean admin)
			throws IllegalArgumentException {

		setName(name);
		setSurname(surname);
		setEmail(email);
		setPassword(password);
		setSecretQuestion(secretQuestion);
		setSecretAnswer(secretAnswer);
		setBirthDate(birthDate);
		setVip(vip);
		setAdmin(admin);
	}

	public String getName() {
		return name;
	}

	private void setName(String name) throws IllegalArgumentException {
		if(name == null)
			throw new IllegalArgumentException();
		else
			this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	private void setSurname(String surname) throws IllegalArgumentException {
		if(surname == null)
			throw new IllegalArgumentException();
		else
			this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	private void setEmail(String email) throws IllegalArgumentException {
		if (email == null || !email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))
			throw new IllegalArgumentException();
		else
			this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws IllegalArgumentException {
		if (password == null || password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH)
			throw new IllegalArgumentException();
		else
			this.password = password;
	}

	public String getSecretQuestion() {
		return secretQuestion;
	}

	private void setSecretQuestion(String secretQuestion) throws IllegalArgumentException {
		if(secretQuestion == null)
			throw new IllegalArgumentException();
		else
			this.secretQuestion = secretQuestion;
	}

	public String getSecretAnswer() {
		return secretAnswer;
	}

	private void setSecretAnswer(String secretAnswer) throws IllegalArgumentException {
		if(secretAnswer == null)
			throw new IllegalArgumentException();
		else
			this.secretAnswer = secretAnswer;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	private void setBirthDate(Date birthDate) throws IllegalArgumentException {
		if(birthDate == null)
			throw new IllegalArgumentException();
		else
			this.birthDate = birthDate;
	}
	
	public boolean getVip() {
		return vip;
	}
	
	private void setVip(boolean vip) {
		this.vip = vip;
	}
	
	public boolean getAdmin() {
		return admin;
	}
	
	private void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
}