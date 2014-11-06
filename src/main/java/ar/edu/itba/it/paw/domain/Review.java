package ar.edu.itba.it.paw.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review extends PersistentEntity implements Comparable<Review> {
	
	private static final int MIN_RATING = 0;
	private static final int MAX_RATING = 5;
	
	@ManyToOne
	private User owner;
	
	@ManyToOne
	private Movie movie;
	
	@ManyToOne
	private Comment comment;
	
	private int rating;

	public Review() { }

	public Review(User owner, Movie movie, Comment comment, int rating) throws IllegalArgumentException {

		setOwner(owner);
		setMovie(movie);
		setComment(comment);
		setRating(rating);
	}


	public User getOwner() {
		return owner;
	}

	private void setOwner(User owner) throws IllegalArgumentException {
		if (owner == null)
			throw new IllegalArgumentException();
		else
			this.owner = owner;
	}
	
	public Movie getMovie() {
		return movie;
	}

	private void setMovie(Movie movie) throws IllegalArgumentException {
		if (movie == null)
			throw new IllegalArgumentException();
		else
			this.movie = movie;
	}
	
	public Comment getComment() {
		return comment;
	}

	private void setComment(Comment comment) throws IllegalArgumentException {
		if (comment == null)
			throw new IllegalArgumentException();
		else
			this.comment = comment;
	}

	public int getRating() {
		return rating;
	}

	private void setRating(int rating) throws IllegalArgumentException {
		if (rating < MIN_RATING || rating > MAX_RATING)
			throw new IllegalArgumentException();
		else
			this.rating = rating;
	}
	
	@Override
	public int compareTo(Review o) {
		return rating - o.getRating();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((movie == null) ? 0 : movie.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
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
		Review other = (Review) obj;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (movie == null) {
			if (other.movie != null)
				return false;
		} else if (!movie.equals(other.movie))
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		return true;
	}
}
