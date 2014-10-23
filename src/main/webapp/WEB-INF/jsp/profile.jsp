<%@ include file="header.jsp" %>
<div>
	<div>
		<div>
			<h3><c:out value="${user.email}" /></h3>
			<h6>Name:</h6>
			<h5>
				<c:out value="${user.name}" />
			</h5>
			<h6>Surname:</h6>
			<h5>
				<c:out value="${user.surname}" />
			</h5>
			<h6>BirthDate:</h6>
			<h5>
				<c:out value="${user.birthDate}" />
			</h5>

		</div>
	</div>
</div>
<%@ include file="footer.jsp" %>