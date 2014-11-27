package ar.edu.itba.it.paw.command;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.Distinction;
import ar.edu.itba.it.paw.domain.Genre;
import ar.edu.itba.it.paw.domain.Movie;

public class MovieForm {

	private Movie movie;
	private String name;
	private String director;
	private String description;
	private int duration;
	private Date releaseDate;
	private List<Comment> comments;
	private Set<Genre> genres;
	private List<Distinction> distinctions;

	public MovieForm() {
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
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


	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
			this.releaseDate = releaseDate;
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

	public Movie build() throws IOException {
		return new Movie(name, releaseDate, genres, director, duration, description, comments, distinctions);
	}
}
