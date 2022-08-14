<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="data.jsp"%>

	<%
	String name = request.getParameter("uname");
	out.println(name + "</br>");
	session.setAttribute("username", name);
	String uname = application.getInitParameter("username");
	out.println("Context param : " + uname);
	Date date = new Date();
	%></br>
	<%="Today date is :" + date%>

</body>
</html>