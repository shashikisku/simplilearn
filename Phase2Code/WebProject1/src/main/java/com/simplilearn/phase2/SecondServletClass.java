package com.simplilearn.phase2;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/*
 * This is an example of generic programming using web.xml for URL mapping of Servlet
 */

public class SecondServletClass extends GenericServlet{

	private static final long serialVersionUID = 1L;

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		PrintWriter writer=res.getWriter();
		writer.print("Second");
	}

}
