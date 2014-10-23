package ar.edu.itba.it.paw.web;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.User;

public class CommentWrapper implements Comparable<CommentWrapper> {
	
	private Comment comment;
	private String transformedComment;
	private boolean favouritee;

	public CommentWrapper(Comment comment, String transformedComment, User userSession) {
		this.comment = comment;
		this.transformedComment = transformedComment;
		this.favouritee = comment.favouritedBy(userSession);
	}

	public Comment getComment() {
		return comment;
	}

	public String getTransformedComment() {
		return transformedComment;
	}
	
	public boolean isFavouritee(){
		return favouritee;
	}
	
	public boolean isRecuthulu(){
		return comment.isRecuthulu();
	}

	@Override
	public int compareTo(CommentWrapper o) {
		return comment.compareTo(o.getComment());
	}
}
