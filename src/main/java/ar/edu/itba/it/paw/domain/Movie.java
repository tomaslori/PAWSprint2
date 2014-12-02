package ar.edu.itba.it.paw.domain;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.DatatypeConverter;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CollectionOfElements;

@Entity
@Table(name = "movies")
public class Movie extends PersistentEntity implements Comparable<Movie> {

	@Column(nullable = false)
	private Date releaseDate;
	
	@Column(nullable = false)
	private String name;
	
	@CollectionOfElements
	private Set<Genre> genres;
	
	@Column(nullable = false)
	private String director;
	
	private int duration;
	
	@Column(nullable = false)
	private String description;
	
	private byte[] image;
	
	@OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<Comment> comments;
	
	@CollectionOfElements
	private List<Distinction> distinctions;


	Movie() { }
	
	public Movie(String name, Date releaseDate, Set<Genre> genres, String director,
			int duration, String description, byte[] image, List<Comment> comments, List<Distinction> distinctions) throws IllegalArgumentException {

		setName(name);
		setGenres(genres);
		setDirector(director);
		setDuration(duration);
		setDescription(description);
		setReleaseDate(releaseDate);
		setImage(image);
		setComments(comments);
		setDistinctions(distinctions);
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

	public Set<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genre> genres) throws IllegalArgumentException {
		if(genres == null)
			throw new IllegalArgumentException();
		else
			this.genres = genres;
	}
	
	public void addGenre(Genre genre) {
		if (genre != null)
			genres.add(genre);
		else
			throw new IllegalArgumentException();
	}
	
	public void removeGenre(Genre genre) {
		if (genre != null)
			genres.remove(genre);
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

	public byte[] getImage() {
		return image;
	}
	
	public String getImageString() {
		return DatatypeConverter.printBase64Binary(image);
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public boolean getImageIsEmpty() {
		return image == null;
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
		Collections.sort(comments);
		return comments;
	}
	
	public void setComments(List<Comment> comments) {
		if (comments == null)
			throw new IllegalArgumentException();
		else
			this.comments = comments;
	}
	
	public void addComment(Comment comment) {
		if (comment != null && (getCommentFrom(comment.getOwner()) == null))
			comments.add(comment);
		else
			throw new IllegalArgumentException();
	}
	
	public Comment getCommentFrom(User user) {
		for (Comment comment :comments)
			if (comment.getOwner().equals(user))
				return comment;
		return null;
	}
	
	public void removeCommentFrom(User user) {
		int toRemove = -1;
		for (int i=0; i<comments.size() ;i++)
			if (comments.get(i).getOwner().equals(user))
				toRemove = i;
		
		if(toRemove != -1)
			comments.remove(toRemove);
	}
	
	public List<Distinction> getDistinctions() {
		return distinctions;
	}

	public void setDistinctions(List<Distinction> distinctions) throws IllegalArgumentException {
		if(distinctions == null)
			throw new IllegalArgumentException();
		else
			this.distinctions = distinctions;
	}
	
	public void addDistinction(Distinction dist) {
		if (dist != null)
			distinctions.add(dist);
		else
			throw new IllegalArgumentException();
	}
	
	public void removeDistinction(Distinction distinction) {
		int toRemove = -1;
		for (int i=0; i<distinctions.size() ;i++)
			if (distinctions.get(i).getName().equals(distinction.getName()))
				toRemove = i;
		
		if(toRemove != -1)
			distinctions.remove(toRemove);
	}
	
	public void updateData(Movie movie) {
		setName(movie.getName());
		setDirector(movie.getDirector());
		setDuration(movie.getDuration());
		setDescription(movie.getDescription());
		setReleaseDate(movie.getReleaseDate());
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
