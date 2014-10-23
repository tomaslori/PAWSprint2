<%@ include file="header.jsp" %>
<div>
	<div>	
		<h2>Login</h2>
		<div class="error"><c:out value="${error}" /></div>
		<form method="POST" action="login">
			<div>
				<div>
					<label for="email">Email:</label>
					<input type="email" name="email" value="<c:out value="${email}" />" />
				</div>
				<div>
					<label for="password">Password:</label>
					<input type="password" name="password"/>
				</div>
			</div>
			<div>
				<input type="submit" name="submit" value="Login" />
			</div>
		</form>
		<a href="changePassword">forgotten password?</a>
	</div>
	</p>
	<div>
		<a href="register">
			<button>
				Register
			</button>
		</a>
	</div>
</div>
<%@ include file="footer.jsp" %>