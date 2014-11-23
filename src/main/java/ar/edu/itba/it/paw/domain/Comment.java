package ar.edu.itba.it.paw.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "comments")
public class Comment extends PersistentEntity implements Comparable<Comment> {

	private static final int MAX_DESCRIPTION_LENGTH = 140;
	private static final int MIN_RATING = 0;
	private static final int MAX_RATING = 5;	
	
	@ManyToOne
	private User owner;
	
	@ManyToOne
	private Movie movie;

	@Column(nullable = false)
	private String description;
	
	private int rating;
	
	@OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<Review> reviews;

	public Comment() { }

	public Comment(User owner, Movie movie, int rating, String description, List<Review> reviews) throws IllegalArgumentException {

		setOwner(owner);
		setMovie(movie);
		setRating(rating);
		setDescription(description);
		setReviews(reviews);
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

	public int getRating() {
		return rating;
	}

	private void setRating(int rating) throws IllegalArgumentException {
		if (rating < MIN_RATING || rating > MAX_RATING)
			throw new IllegalArgumentException();
		else
			this.rating = rating;
	}
	
	public String getDescription() {
		return description;
	}

	private void setDescription(String description) throws IllegalArgumentException {
		if (description == null || description.length() > MAX_DESCRIPTION_LENGTH)
			throw new IllegalArgumentException();
		else
			this.description = description;
	}
	
	public List<Review> getReviews() {
		return reviews;
	}
	
	private void setReviews(List<Review> reviews) {
		if (reviews == null)
			throw new IllegalArgumentException();
		else
			this.reviews = reviews;
	}
	
	public void addReview(Review review) {
		if (review != null && (getReviewFrom(review.getOwner()) == null))
			reviews.add(review);
		else
			throw new IllegalArgumentException();
	}
	
	public float getReviewsAvg() {
		float sum = 0;
		for(Review review :reviews)
			sum += review.getRating();
		return sum /reviews.size();
	}
	
	public Review getReviewFrom(User user) {
		for(Review review :reviews)
			if(review.getOwner().equals(user))
				return review;
		return null;
	}
		
	
	@Override
	public int compareTo(Comment c) {
		
		Float myRating = 0.f;
		for (Review r : reviews)
			myRating += r.getRating();
		myRating /= reviews.size();
		
		Float otherRating = 0.f;
		for (Review r : c.getReviews())
			otherRating += r.getRating();
		otherRating /= c.getReviews().size();
		
		return (int)((otherRating - myRating)/Math.abs(otherRating - myRating));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((movie == null) ? 0 : movie.hashCode());
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
		Comment other = (Comment) obj;
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
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		return true;
	}
}
