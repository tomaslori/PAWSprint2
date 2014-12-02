package ar.edu.itba.it.paw.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MovieTest {

	private User author;
	private Movie movie;

	@Before
	public void setUp() {
		author = new User("testName", "testSurname", "testEmail@email.com", "testPassword",	"testQuestion", "testAnswer", new Date(), false, false);
		movie = new Movie("testName", new Date(), new TreeSet<Genre>(), "testDirector", 100, "testDescription", null, new ArrayList<Comment>(), new ArrayList<Distinction>());
		
	}

	@Test
	public void testRemoveCommentFrom() {
		List<Review> reviews = new ArrayList<Review>();
		Comment comment = new Comment(author, movie, 4, "testDescription", reviews);
		movie.addComment(comment);
		Assert.assertEquals(movie.getComments().size(), 1);
		movie.removeCommentFrom(author);
		Assert.assertEquals(movie.getComments().size(), 0);
	}
	
	@Test
	public void testGetCommentFrom() {
		List<Review> reviews = new ArrayList<Review>();
		Comment comment = new Comment(author, movie, 4, "testDescription", reviews);
		movie.addComment(comment);
		Assert.assertEquals(movie.getCommentFrom(author), comment);
	}
	
	@Test
	public void testGetReviewFromUser() {
		Distinction dist = new Distinction("testDistinction", false);
		movie.addDistinction(dist);
		Assert.assertEquals(movie.getDistinctions().size(), 1);
		movie.removeDistinction(dist);
		Assert.assertEquals(movie.getDistinctions().size(), 0);
	}
	
	@Test
	public void testUpdateMovieData() {
		Assert.assertEquals(movie.getDirector(), "testDirector");
		Assert.assertEquals(movie.getDuration(), 100);
		Assert.assertEquals(movie.getDescription(), "testDescription");
		Movie newData = new Movie("testName", new Date(), new TreeSet<Genre>(), "testDirector2", 200, "testDescription2", null, new ArrayList<Comment>(), new ArrayList<Distinction>());
		movie.updateData(newData);
		Assert.assertEquals(movie.getDirector(), "testDirector2");
		Assert.assertEquals(movie.getDuration(), 200);
		Assert.assertEquals(movie.getDescription(), "testDescription2");
	}
}
