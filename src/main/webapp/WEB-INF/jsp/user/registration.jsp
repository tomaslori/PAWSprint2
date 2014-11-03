<%@ include file="../header.jsp" %>
<div>
	<h2>Registration</h2>
	<div><c:out value="${error}" /></div>
	<form method="POST" action="registration" commandName="userForm" enctype="multipart/form-data">
		<div>
			<label for="name">Name:</label>
			<input type="text" name="name" value="${name}" />
			<form:errors path="name" />
		</div>
		<div>
			<label for="surname">Surname:</label>
			<input type="text" name="surname" value="${surname}"/>
			<form:errors path="surname" />
		</div>
		<div>
			<label for="email">Email:</label>
			<input type="email" name="email" value="${email}"/>
			<form:errors path="email" />
		</div>
		<div>
			<label for="password">Password:</label>
			<input type="password" name="password" value="${password}"/>
			<form:errors path="password" />
		</div>
		<div>
			<label for="confirmPassword">Confirm password:</label>
			<input type="password" name="confirmPassword" value="${confirmPassword}"/>
			<form:errors path="confirmPassword" />
		</div>
		<div>
			<label for="secretQuestion">Secret question:</label>
			<input type="text" name="secretQuestion" value="${secretQuestion}"/>
			<form:errors path="secretQuestion" />
		</div>
		<div>
			<label for="secretAnswer">Secret answer:</label>
			<input type="text" name="secretAnswer" value="${secretAnswer}"/>
			<form:errors path="secretAnswer" />
		</div>
		<div>
			<label for="birthDate">BirthDate:</label>
			<input type="date" name="birthDate" value="${birthDate}"/>
			<form:errors path="birthDate" />
		</div>
		<div>
			<input type="submit" name="submit" value="Submit" />
		</div>
	</form>
	<div><a href="login">Already registered? Login!</a></div>
</div>
<%@ include file="../footer.jsp" %>