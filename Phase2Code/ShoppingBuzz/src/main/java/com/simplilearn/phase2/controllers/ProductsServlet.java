package com.simplilearn.phase2.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.simplilearn.phase2.dto.Product;
import com.simplilearn.phase2.service.ProductServiceImpl;

/**
 * Servlet implementation class ProductsServlet
 */
@WebServlet("/ProductsServlet")
public class ProductsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Set<Product> allProduct = new ProductServiceImpl().getAllProducts();
		PrintWriter writer = response.getWriter();
		response.setContentType("text/html");
		writer.print("<table border =3>");
		writer.print("<tr><td>ID</td><td>Product_Name</td><td>Cost</td></tr>");
		for (Product product : allProduct) {
			writer.print("<tr>");
			writer.print("<td>" + product.getProduct_id() + "</td>" + "<td>" + product.getProduct_name() + "</td>"
					+ "<td>" + product.getProduct_cost() + "</td>");
			writer.print("</tr>");
		}
		writer.print("</table>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
