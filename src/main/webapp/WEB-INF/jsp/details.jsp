<%@ include file="header.jsp" %>
<div>
	<div>
		<div>
			<h3><c:out value="${movie.name}" /></h3>
			<h6>Release Date:</h6>
			<h5>
				<c:out value="${movie.releaseDate}" />
			</h5>
			<h6>Genre:</h6>
			<h5>
				<c:out value="${movie.genre}" />
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
		</div>
	</div>
	<h2>
		Comments
	</h2>
	<ul>
		<c:set var="row" value="0" />
		<c:forEach items="${comments}" var="comment">
			<li	<c:set var="row" value="${row + 1}" />>
				<span>
					User: <c:out value="${comment.owner.name} ${comment.owner.surname}" />
				</span>
				<span>
					Description: <c:out value="${comment.description}" />
				</span>
				<span>
					Rating: <c:out value="${comment.rating}" /> 
				</span>
			</li>
		</c:forEach>
	</ul>
	
	<c:if test="${canComment}">
		<h2>
			Comment
		</h2>
		<form method="POST" action="details">
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
<%@ include file="footer.jsp" %>