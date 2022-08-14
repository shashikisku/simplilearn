<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="data.jsp" %>
	<form action="output.jsp" method="post">
		Username<input type="text" name="uname"></br> <input type="submit"
			value="click">
	</form>

</body>
</html>