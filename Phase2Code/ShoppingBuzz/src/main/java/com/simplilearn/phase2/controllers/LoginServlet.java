package com.simplilearn.phase2.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.simplilearn.phase2.dto.User;
import com.simplilearn.phase2.service.UserServiceImpl;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uname = request.getParameter("uname");
		String pass = request.getParameter("pword");

		User user = new User(uname, pass);
		boolean isValidUser = new UserServiceImpl().validateUser(user);
		if (isValidUser == true) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("ValidUserServlet");
			dispatcher.forward(request, response);

		} else {
			response.sendRedirect("InvalidUserServlet");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
