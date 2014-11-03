package ar.edu.itba.it.paw.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "movies")
public class Movie extends PersistentEntity implements Comparable<Movie> {

	@Column(nullable = false)
	private Date releaseDate;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String genre;
	
	@Column(nullable = false)
	private String director;
	
	private int duration;
	
	@Column(nullable = false)
	private String description;
	
	@OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
	private List<Comment> comments;

	// TODO: preguntar si esto es necesario!
	public Movie() { }
	
	public Movie(String name, Date releaseDate, String genre, String director,
			int duration, String description) throws IllegalArgumentException {

		setName(name);
		setGenre(genre);
		setDirector(director);
		setDuration(duration);
		setDescription(description);
		setReleaseDate(releaseDate);
		comments = new ArrayList<Comment>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws IllegalArgumentException {
		if(name == null)
			throw new IllegalArgumentException();
		else
			this.name = name;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) throws IllegalArgumentException {
		if(genre == null)
			throw new IllegalArgumentException();
		else
			this.genre = genre;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) throws IllegalArgumentException {
		if (director == null)
			throw new IllegalArgumentException();
		else
			this.director = director;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) throws IllegalArgumentException {
		if (duration <= 0)
			throw new IllegalArgumentException();
		else
			this.duration = duration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) throws IllegalArgumentException {
		if(description == null)
			throw new IllegalArgumentException();
		else
			this.description = description;
	}


	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) throws IllegalArgumentException {
		if(releaseDate == null)
			throw new IllegalArgumentException();
		else
			this.releaseDate = releaseDate;
	}
	
	public List<Comment> getComments() {
		return comments;
	}
	
	public void setComments(List<Comment> comments) {
		if (comments == null)
			throw new IllegalArgumentException();
		else
			this.comments = comments;
	}
	
	public void addComment(Comment comment) {
		if (comment != null && canCommented(comment.getOwner()))
			comments.add(comment);
		else
			throw new IllegalArgumentException(); // TODO: check if another exception should be used..
	}
	
	private boolean canCommented(User user) {
		for(Comment comment :comments)
			if(comment.getOwner().equals(user))
				return false;
		return true;
	}
	
	@Override
	public int compareTo(Movie o) {
		return -releaseDate.compareTo(o.getReleaseDate());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((releaseDate == null) ? 0 : releaseDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Movie other = (Movie) obj;
		if (releaseDate == null) {
			if (other.releaseDate != null)
				return false;
		} else if (!releaseDate.equals(other.releaseDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
