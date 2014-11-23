<%@ include file="../header.jsp" %>
<div>
	<div>
		<div>
			<h3><c:out value="${movie.name}" /></h3>
			<h6>Release Date:</h6>
			<h5>
				<c:out value="${movie.releaseDate}" />
			</h5>
			<h6>Genres:</h6>
			<h5>
				<c:forEach items="${movie.genres}" var="genre">
					<c:out value="${genre.name}"/>
				</c:forEach>
			</h5>
			<h6>Director:</h6>
			<h5>
				<c:out value="${movie.director}" />
			</h5>
			<h6>Duration:</h6>
			<h5>
				<c:out value="${movie.duration}" />
			</h5>
			<h6>Description:</h6>
			<h5>
				<c:out value="${movie.description}" />
			</h5>
			<h6>Distinctions:</h6>
			<c:forEach items="${movie.distinctions}" var="distinction">
				<h5> ${distinction.name} </h5>
			</c:forEach>
		</div>
	</div>
	<h2>
		Comments
	</h2>
	<ul>
		<c:set var="row" value="0" />
		<c:forEach items="${movie.comments}" varStatus="loop">
			<li	<c:set var="row" value="${row + 1}" />>
				<span>
					User: <c:out value="${movie.comments[loop.index].owner.name} ${movie.comments[loop.index].owner.surname}" />
				</span>
				<span>
					Description: <c:out value="${movie.comments[loop.index].description}" />
				</span>
				<span>
					Rating: <c:out value="${movie.comments[loop.index].rating}" /> 
				</span>
				<span>
					Avg. Review: <c:out value="${reviews[loop.index]}" />
				</span>
				<c:if test="${isAdmin}">
					<form method="POST" action="delete">
							<input type="hidden" name="commentOwner" value="${movie.comments[loop.index].owner.email}">
							<input type="hidden" name="commentMovie" value="${movie.name}">
							<input type="submit" name="submit" value="Delete" />
					</form>
				</c:if>
				<c:if test="${canReview[loop.index]}">
					<h4> Review </h4>
					<form method="POST" action="review">
						<input type="hidden" name="commentOwner" value="${movie.comments[loop.index].owner.email}">
						<input type="hidden" name="commentMovie" value="${movie.name}">
							<label for="rating">Rating:</label>
							<input type="number" min="0" max="5" name="rating" value="${review}"/>
							<input type="submit" name="submit" value="Submit" />
					</form>
				</c:if>
			</li>
		</c:forEach>
	</ul>
	
	<c:if test="${not empty reviewError}">
		<h3>
			<c:out value="${reviewError}" />
		</h3>
	</c:if>
	
	<c:if test="${canComment}">
		<h2>
			Comment
		</h2>
		<c:if test="${not empty commentError}">
			<h3>
				<c:out value="${commentError}"/>
			</h3>
		</c:if>
		<form method="POST" action="details">
			<input type="hidden" name="commentMovie" value="${movie.name}">
			<div>
				<label for="description">Description:</label>
				<input type="text" name="description" value="${description}" />
			</div>
			<div>
				<label for="rating">Rating:</label>
				<input type="number" min="0" max="5" name="rating" value="${rating}"/>
			</div>
			<div>
				<input type="submit" name="submit" value="Submit" />
			</div>
		</form>
	</c:if>
	
	<c:if test="${alreadyCommented}">
		<h4>
			Already commented!
		</h4>
	</c:if>
	
	
	
</div>
<%@ include file="../footer.jsp" %>