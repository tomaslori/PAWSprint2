<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="css/bootstrap-responsive.css" />
<link rel="stylesheet" type="text/css"
	href="css/bootstrap-responsive.min.css" />
<link rel="stylesheet" type="text/css" href="css/custom.css" />


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="css/style.css" />
	</head>
	<body>
		<div>
			<div>
				<form method="POST" action="search">
						<input type="text"  placeholder="Find movie by director" name="search" />
						<input type="submit" name="submit" value="Search" />
				</form>
			</div>
			<div >
				<div>
					<a href="/Sprint1"> home </a>
				</div>
				<c:if test="${isLogged}">
					<div>
						<p><c:out value="${userSession.email}" /></p>
						<br>
						<a href="logout">Log out</a>
					</div>
				</c:if>
				<c:if test="${!isLogged}">
					<div>
						<a href="login">Log in</a>
					</div>
				</c:if>
			</div>
		</div>