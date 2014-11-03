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
				<form method="POST" action="${pageContext.request.contextPath}/bin/movie/search">
						<input type="text"  placeholder="Find movie by director" name="director" />
						<input type="submit" name="submit" value="Search" />
				</form>
			</div>
			<div >
				<div>
					<a href="${pageContext.request.contextPath}/bin/home"> home </a>
				</div>
				<c:if test="${not empty email}">
					<div>
						<a href="${pageContext.request.contextPath}/bin/user/profile">${email}</a>
						<br>
						<a href="${pageContext.request.contextPath}/bin/user/logout">Log out</a>
					</div>
				</c:if>
				<c:if test="${empty email}">
					<div>
						<a href="${pageContext.request.contextPath}/bin/user/login">Log in</a>
					</div>
				</c:if>
			</div>
		</div>