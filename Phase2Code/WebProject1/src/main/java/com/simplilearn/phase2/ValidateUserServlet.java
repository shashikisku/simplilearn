package com.simplilearn.phase2;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ValidateUserServlet
 */
@WebServlet("/ValidateUserServlet")
public class ValidateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uname = request.getParameter("uname");
		String pass = request.getParameter("pword");
		// SERVLET chaining
		if (uname.equals("admin") && pass.equals("admin")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("ValidUserServlet");
			dispatcher.forward(request, response);
		} else {
			response.sendRedirect("InvalidUserServlet");

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
