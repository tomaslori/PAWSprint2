<%@ include file="../header.jsp" %>
<div>
	<h2>Registration</h2>
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
			<label for="surname">Surname:</label>
			<div>
				<form:errors path="surname" />
			</div>
			<div>
				<form:input type="text" path="surname" />
			</div>
		</div>
		<div>
			<label for="email">Email:</label>
			<div>
				<form:errors path="email" />
			</div>
			<div>
				<form:input type="email" path="email" />
			</div>
		</div>
		<div>
			<label for="password">Password:</label>
			<div>
				<form:errors path="password" />
			</div>
			<div>
				<form:input type="password" path="password" />
			</div>
		</div>
		<div>
			<label for="confirmPassword">Confirm password:</label>
			<div>
				<form:errors path="confirmPassword" />
			</div>
			<div>
				<form:input type="password" path="confirmPassword" />
			</div>
		</div>
		<div>
			<label for="secretQuestion">Secret question:</label>
			<div>
				<form:errors path="secretQuestion" />
			</div>
			<div>
				<form:input type="text" path="secretQuestion" />
			</div>
		</div>
		<div>
			<label for="secretAnswer">Secret answer:</label>
			<div>
				<form:errors path="secretAnswer" />
			</div>
			<div>
				<form:input type="text" path="secretAnswer" />
			</div>
		</div>
		<div>
			<label for="birthDate">BirthDate:</label>
			<div>
				<form:errors path="birthDate" />
			</div>
			<div>
				<form:input type="date" path="birthDate" />
			</div>
		</div>
		<div>
			<input type="submit" name="submit" value="Submit" />
		</div>
	</form:form>
	<div><a href="login">Already registered? Login!</a></div>
</div>
<%@ include file="../footer.jsp" %>