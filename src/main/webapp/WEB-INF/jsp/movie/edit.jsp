<%@ include file="../header.jsp" %>

<div>
	<h2>Movie edit</h2>
	<div><c:out value="${error}" /></div>
	<form:form method="POST" action="registration" modelAttribute="userForm" enctype="multipart/form-data">
		<div>
			<label for="name">Name:</label>
			<div>
				<form:errors path="name" />
			</div>
			<div>
				<form:input type="text" path="name" />
			</div>
		</div>
		<div>
			<label for="director">Director:</label>
			<div>
				<form:errors path="director" />
			</div>
			<div>
				<form:input type="text" path="director" />
			</div>
		</div>
		<div>
			<label for="description">Description:</label>
			<div>
				<form:errors path="description" />
			</div>
			<div>
				<form:input type="text" path="description" />
			</div>
		</div>
		<div>
			<label for="duration">Duration:</label>
			<div>
				<form:errors path="duration" />
			</div>
			<div>
				<form:input type="number"  min="0" path="duration" />
			</div>
		</div>
		<div>
			<label for="releaseDate">ReleaseDate:</label>
			<div>
				<form:errors path="releaseDate" />
			</div>
			<div>
				<form:input type="date" path="releaseDate" />
			</div>
		</div>
		<div>
			<input type="submit" name="submit" value="Submit" />
		</div>
	</form:form>
	
	<h2> Genres </h2>
	<c:forEach items="${movie.genres}" var="genre">
		<c:out value="${genre.name}"/>
		<form method="POST" action="deleteGenre">
			<input type="hidden" name="movie" value="${movie.name}">
			<input type="hidden" name="genre" value="${genre.name}">
			<input type="submit" name="submit" value="Submit" />
		</form>
	</c:forEach>
	<form method="POST" action="addGenre">
		<input type="hidden" name="movie" value="${movie.name}">
		<label for="name">Name:</label>
		<input type="text" name="name" value="${name}"/>
		<input type="submit" name="submit" value="Submit" />
	</form>
	
	<h2> Distinctions </h2>
	<c:forEach items="${movie.distinctions}" var="dist">
		<c:out value="${dist.name}"/>
		<form method="POST" action="deleteDistinction">
			<input type="hidden" name="movie" value="${movie.name}">
			<input type="hidden" name="distinction" value="${dist.name}">
			<input type="submit" name="submit" value="Submit" />
		</form>
	</c:forEach>
	<form method="POST" action="addDistinction">
		<input type="hidden" name="movie" value="${movie.name}">
		<label for="name">Name:</label>
		<input type="text" name="name" value="${name}"/>
		<input type="submit" name="submit" value="Submit" />
	</form>
	
	
</div>
<%@ include file="../footer.jsp" %>