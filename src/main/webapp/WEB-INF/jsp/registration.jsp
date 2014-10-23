<%@ include file="header.jsp" %>
<div>
	<h2>Registration</h2>
	<div><c:out value="${error}" /></div>
	<form method="POST" action="register">
		<div>
			<label for="name">Name:</label>
			<input type="text" name="name" value="${name}" />
		</div>
		<div>
			<label for="surname">Surname:</label>
			<input type="text" name="surname" value="${surname}"/>
		</div>
		<div>
			<label for="email">Email:</label>
			<input type="email" name="email" value="${email}"/>
		</div>
		<div>
			<label for="password">Password:</label>
			<input type="password" name="password" value="${password}"/>
		</div>
		<div>
			<label for="passConfirm">Confirm password:</label>
			<input type="password" name="passConfirm" value="${confirm}"/>
		</div>
		<div>
			<label for="secretQuestion">Secret question:</label>
			<input type="text" name="secretQuestion" value="${secretQuestion}"/>
		</div>
		<div>
			<label for="secretAnswer">Secret answer:</label>
			<input type="text" name="secretAnswer" value="${secretAnswer}"/>
		</div>
		<div>
			<label for="birthDate">BirthDate:</label>
			<input type="date" name="birthDate" value="${birthDate}"/>
		</div>
		<div>
			<input type="submit" name="submit" value="Submit" />
		</div>
	</form>
	<div><a href="login">Already registered? Login!</a></div>
</div>
<%@ include file="footer.jsp" %>