package com.simplilearn.phase2.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.simplilearn.phase2.dto.User;
import com.simplilearn.phase2.service.UserServiceImpl;

/**
 * Servlet implementation class RegisterUserServlet
 */
@WebServlet("/RegisterUserServlet")
public class RegisterUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterUserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String firstName = request.getParameter("fname");
		String lastName = request.getParameter("lname");
		String address = request.getParameter("address");
		String userName = request.getParameter("uname");
		String password = request.getParameter("pass");

		User user = new User(firstName, lastName, address, userName, password);
		boolean isUserRegistered = new UserServiceImpl().registerUser(user);
		if (isUserRegistered) {
			response.sendRedirect("home.html");
		} else {
			response.sendRedirect("regiserUser.html");
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
