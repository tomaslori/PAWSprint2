package ar.edu.itba.it.paw.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ar.edu.itba.it.paw.domain.UserRepo;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	private UserRepo userRepo;

	@Autowired
	public AuthenticationFilter(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
	
		 HttpSession session = request.getSession(false);
		 
		 if (session != null) {
			 String userSessionString = (String) session.getAttribute("email");
			 
			 if (userSessionString != null)
				request.setAttribute("user", userRepo.getUser(userSessionString));
		 }
			 

		// Call the next filter (continue request processing)
		filterChain.doFilter(request, response);
	}
}
