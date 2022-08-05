package com.simplilearn.sessionManagement.servlet.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionServlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// extract data from request
		String username = request.getParameter("uname");

//		create HttpSession object
//		when user logs in,servers creates HttpSession automatically
		HttpSession session = request.getSession();

//		store the data in session object
		session.setAttribute("username", username);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
