<%@ page import="com.simplilearn.phase2.jsp.bean.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="data.jsp"></jsp:include>
	This is demo
	<%

	User user=new User();
	user.setUsername("User1");
	user.setPassword("User123@");
	out.print("</br>");
	out.print("usernam:" + user.getUsername());
	out.print("</br>");
	out.print("password:" + user.getPassword());
	out.print("</br>");
	%>

	<jsp:useBean class="com.simplilearn.phase2.jsp.bean.User" id="user1"></jsp:useBean>
	<jsp:setProperty property="username" value="usingjspname" name="user1" />
	<jsp:setProperty property="password" value="usingjspnamepassword"
		name="user1" />
	</br> User using JSP<jsp:getProperty property="username" name="user1" />
	</br> Password using JSP<jsp:getProperty property="password" name="user1" />

</body>
</html>