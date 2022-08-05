package com.simplilearn.sessionManagement.servlet.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HiddenServlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve the name from the request
		String uname = request.getParameter("uname");

		// create a response so that a form with hidden field is sent to the user
		PrintWriter writer = response.getWriter();
		response.setContentType("text/html");
		writer.print("<form action='HiddenServlet2'>");
		writer.print("<input type='hidden' value='" + uname + "' name='uname'/>");
		writer.print("<input type='submit' value='Click'/>");
		writer.print("</form>");
	}

}
