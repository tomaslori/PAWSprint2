package ar.edu.itba.it.paw.command;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.Distinction;
import ar.edu.itba.it.paw.domain.Genre;
import ar.edu.itba.it.paw.domain.Movie;

public class MovieForm {

	private String name;
	private String director;
	private String description;
	private int duration;
	private Date releaseDate;
	private byte[] image;
	private List<Comment> comments;
	private Set<Genre> genres;
	private List<Distinction> distinctions;

	public MovieForm() {
		comments = new ArrayList<Comment>();
		genres = new TreeSet<Genre>();
		distinctions = new ArrayList<Distinction>();
	}
	
	public MovieForm(Movie movie) {
		name = movie.getName();
		director = movie.getDirector();
		description = movie.getDescription();
		duration = movie.getDuration();
		releaseDate = movie.getReleaseDate();
		image = movie.getImage();
		comments = movie.getComments();
		genres = movie.getGenres();
		distinctions = movie.getDistinctions();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
			this.name = name;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
			this.director = director;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
			this.description = description;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
			this.duration = duration;
	}
	
	public String getReleaseDate() {
		
		if (releaseDate == null)
			return "";
		
		int day =  releaseDate.getDate();
		int month = releaseDate.getMonth() +1;
		int year = releaseDate.getYear() + 1900;
		String dateStr = year + "-" + ((month<10)? "0"+month:month) + "-" + ((day<10)? "0"+day : day);
		return dateStr;
	}

	public void setReleaseDate(Date releaseDate) {
		System.out.println("MovieForm got a releaseDate!!!");
		this.releaseDate = releaseDate;
	}

//	public void setReleaseDate(String releaseDate) {
//		System.out.println("MovieForm got a releaseDate of type string!!! " + releaseDate );
//	}

	public String getImage() {
		return new String();
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public boolean getImageIsEmpty() {
		return image == null;
	}
	
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
			this.comments = comments;
	}
	
	public Set<Genre> getGenres() {
		return genres;
	}

	public void getGenres(Set<Genre> genres) {
			this.genres = genres;
	}
	
	public List<Distinction> getDistinctions() {
		return distinctions;
	}

	public void setDistinctions(List<Distinction> distinctions) {
			this.distinctions = distinctions;
	}

	public Movie build() {
		String date = (releaseDate != null)? releaseDate.getDay() + "/" + releaseDate.getMonth() + "/" + releaseDate.getYear() : "noDate";
		System.out.println("DEBUG name: " + name + ", releaseDate: " + date + ", director: " + director +
				", duration: " + duration + ", description: " + description);
		return new Movie(name, releaseDate, genres, director, duration, description, image, comments, distinctions);
	}
}
