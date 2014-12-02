package ar.edu.itba.it.paw.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CommentTest {

	private User author;
	private Movie movie;
	private Comment comment;

	@Before
	public void setUp() {
		author = new User("testName", "testSurname", "testEmail@email.com", "testPassword",	"testQuestion", "testAnswer", new Date(), false, false);
		movie = new Movie("testName", new Date(), new TreeSet<Genre>(), "testDirector", 100, "testDescription", null, new ArrayList<Comment>(), new ArrayList<Distinction>());
		List<Review> reviews = new ArrayList<Review>();
		comment = new Comment(author, movie, 4, "testDescription", reviews);
		
	}

	@Test
	public void testAddReviews() {
		User reviewer1 = new User("reviewer1", "reviewer1", "reviewer1@email.com", "reviewer1",	"reviewer1", "reviewer1", new Date(), false, false);
		Review rev1 = new Review(reviewer1, movie, comment, 3);
		comment.addReview(rev1);
		User reviewer2 = new User("reviewer2", "reviewer2", "reviewer2@email.com", "reviewer2",	"reviewer2", "reviewer2", new Date(), false, false);
		Review rev2 = new Review(reviewer2, movie, comment, 5);
		comment.addReview(rev2);
		
		Assert.assertEquals(comment.getReviews().size(), 2);
	}
	
	@Test
	public void testAvgReview() {
		User reviewer1 = new User("reviewer1", "reviewer1", "reviewer1@email.com", "reviewer1",	"reviewer1", "reviewer1", new Date(), false, false);
		Review rev1 = new Review(reviewer1, movie, comment, 3);
		comment.addReview(rev1);
		User reviewer2 = new User("reviewer2", "reviewer2", "reviewer2@email.com", "reviewer2",	"reviewer2", "reviewer2", new Date(), false, false);
		Review rev2 = new Review(reviewer2, movie, comment, 5);
		comment.addReview(rev2);
		
		Assert.assertEquals(comment.getReviewsAvg(), 4, 0.05);
	}
	
	@Test
	public void testGetReviewFromUser() {
		User reviewer1 = new User("reviewer1", "reviewer1", "reviewer1@email.com", "reviewer1",	"reviewer1", "reviewer1", new Date(), false, false);
		Review rev1 = new Review(reviewer1, movie, comment, 3);
		comment.addReview(rev1);
		User reviewer2 = new User("reviewer2", "reviewer2", "reviewer2@email.com", "reviewer2",	"reviewer2", "reviewer2", new Date(), false, false);
		Review rev2 = new Review(reviewer2, movie, comment, 5);
		comment.addReview(rev2);
		
		Assert.assertEquals(comment.getReviewFrom(reviewer1), rev1);
	}
	
}
