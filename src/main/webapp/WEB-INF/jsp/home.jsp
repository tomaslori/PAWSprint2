<%@ include file="header.jsp"%>
<div>
	<h2>
		Top Movies
	</h2>
	<ul>
		<c:set var="row" value="0" />
		<c:forEach items="${topmovies}" varStatus="loop">
			<li
				<c:set var="row" value="${row + 1}" />>
				<a
				href="<c:url value="movie/details"><c:param name="movie" value="${topmovies[loop.index].name}" /></c:url>">${topmovies[loop.index].name}</a>
				<br />
				<span>
					Rating: <c:out value="${topratings[loop.index]}" />
				</span>
				<span>
					Director: <c:out value="${topmovies[loop.index].director}" />
				</span>
				<span>
					Genres: <c:forEach items="${topmovies[loop.index].genres}" var="genre"> <c:out value="${genre.name}"/> </c:forEach>
				</span>
				<span>
					Description: <c:out value="${topmovies[loop.index].description}" />
				</span>
				<span>
					duration: <c:out value="${topmovies[loop.index].duration}" /> 
				</span>
				<span>
					ReleaseDate: <c:out value="${topmovies[loop.index].releaseDate}" />
				</span>
			</li>
		</c:forEach>
	</ul>
	<h2>
		Others
	</h2>
	<c:if test="${isAdmin}">	 
		<form method="GET" action="movie/edit">
				<input type="submit" name="submit" value="Add Movie" />
		</form>
	</c:if>
	<ul>
		<c:set var="row" value="0" />
		<c:forEach items="${movies}" var="movie">
			<li
				<c:set var="row" value="${row + 1}" />>
				<a
				href="<c:url value="movie/details"><c:param name="movie" value="${movie.name}" /></c:url>">${movie.name}</a>
				<br />
				<span>
					Director: <c:out value="${movie.director}" />
				</span>
				<span>
					Genres: <c:forEach items="${movie.genres}" var="genre"> <c:out value="${genre.name}"/> </c:forEach>
				</span>
				<span>
					Description: <c:out value="${movie.description}" />
				</span>
				<span>
					duration: <c:out value="${movie.duration}" /> 
				</span>
				<span>
					ReleaseDate: <c:out value="${movie.releaseDate}" />
				</span>
			</li>
		</c:forEach>
	</ul>
<%@ include file="footer.jsp"%>