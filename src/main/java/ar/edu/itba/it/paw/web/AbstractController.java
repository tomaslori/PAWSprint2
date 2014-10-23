package ar.edu.itba.it.paw.web;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.User;

@Controller
public abstract class AbstractController {

	protected SortedSet<CommentWrapper> transformComments(List<Comment> comments, User userSession) {
		SortedSet<CommentWrapper> ans = new TreeSet<CommentWrapper>();
		for (Comment comment : comments) {
			CommentWrapper aux = new CommentWrapper(comment,
					getProcessedComment(comment.getComment()),userSession);
			ans.add(aux);
		}
		return ans;
	}
	
	private String getProcessedComment(String comment) {
		// Search for URLs
		String aux = comment;
		if (aux != null && aux.contains("http:")) {
			int indexOfHttp = aux.indexOf("http:");
			int endPoint = (aux.indexOf(' ', indexOfHttp) != -1) ? aux.indexOf(
					' ', indexOfHttp) : aux.length();
			String url = aux.substring(indexOfHttp, endPoint);
			String targetUrlHtml = "<a href='" + url + "' target='_blank'>"
					+ url + "</a>";
			aux = aux.replace(url, targetUrlHtml);
		}

		// Search for Hashtags
		String patternStr = "#([A-Za-z0-9_]+)";
		Pattern pattern = Pattern.compile(patternStr);
		String[] words = aux.split(" ");
		String ans = "";
		String result = "";

		for (String word : words) {
			Matcher matcher = pattern.matcher(word);
			if (matcher.find()) {
				result = matcher.group();
				result = result.replace(" ", "");
				String search = result.replace("#", "");
				String searchHTML = "<a href='../../hashtag/detail?tag="
						+ search + "'>" + result + "</a>";
				ans += word.replace(result, searchHTML) + " ";
			} else {
				ans += word + " ";
			}
		}

		// Search for Users
		patternStr = "@([A-Za-z0-9_]+)";
		pattern = Pattern.compile(patternStr);
		words = ans.split(" ");
		ans = "";
		for (String word : words) {
			Matcher matcher = pattern.matcher(word);
			if (matcher.find()) {
				result = matcher.group();
				result = result.replace(" ", "");
				String search = result.replace("@", "");
				String userHTML = "<a href='./" + search + "'>" + result
						+ "</a>";
				ans += word.replace(result, userHTML) + " ";
			} else {
				ans += word + " ";
			}
		}
		return ans;
	}
}
