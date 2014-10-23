
<%@ include file="header.jsp" %>
<div>
	<div>
		<h2>Recovering Password</h2>
		<div><c:out value="${error}" /></div>
		<div><c:out value="${success}" /></div>
		<c:if test="${!userSelected}">
			<form method="GET" action="changePassword">
					<div>
						<label for="userToRecover"> Insert your Email: </label>
						<input type="email" name="userToRecover"/>
					</div>
					<div>
						<input type="submit" name="submit" value="submit email" />
					</div>
			</form>
		</c:if>	
		<c:if test="${userSelected && !passwordRecovered}">
			<form method="POST" action="changePassword">
					<div>
						<label><c:out value="${user.secretQuestion}"/></label>
						<input type="text" name="secretAnswer" />
					</div>
					<div>
						<label>New Password:</label>
						<input type="password" name="password" />
					</div>
					<div>
						<label>Confirm New Password:</label>
						<input type="password" name="confirm" />
					</div>
					<input type="hidden" name="userToRecover" value="<c:out value="${user.email}"/>" />
					<c:if test="${!passwordRecovered}">
						<div>
							<input type="submit" name="submit" value="submit answer" />
						</div>
					</c:if>
			</form>
		</c:if>	
		<a href="login"><button>Go back</button></a>
	</div>
<%@ include file="footer.jsp" %>