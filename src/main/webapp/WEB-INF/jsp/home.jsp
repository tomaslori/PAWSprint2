<%@ include file="header.jsp"%>
<div>
	<h2>
		Top Movies
	</h2>
	<ul>
		<c:set var="row" value="0" />
		<c:forEach items="${topmovies}" var="movie">
			<li
				<c:set var="row" value="${row + 1}" />>
				<a
				href="<c:url value="details"><c:param name="movie" value="${movie.name}" /></c:url>">${movie.name}</a>
				<br />
				<span>
					Director: <c:out value="${movie.director}" />
				</span>
				<span>
					Genre: <c:out value="${movie.genre}" />
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
	<h2>
		Others
	</h2>
	<ul>
		<c:set var="row" value="0" />
		<c:forEach items="${movies}" var="movie">
			<li
				<c:set var="row" value="${row + 1}" />>
				<a
				href="<c:url value="details"><c:param name="movie" value="${movie.name}" /></c:url>">${movie.name}</a>
				<br />
				<span>
					Director: <c:out value="${movie.director}" />
				</span>
				<span>
					Genre: <c:out value="${movie.genre}" />
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