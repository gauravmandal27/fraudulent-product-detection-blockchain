package com.src.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.src.dao.DAOImplementation;
import com.src.dao.DAOInterface;
import com.src.model.Code;
import com.src.model.Retail;

/**
 * Servlet implementation class StandardServlet
 */
@WebServlet("/Serve")
public class StandardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public StandardServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mode = (String) request.getParameter("mode");
		DAOInterface ride = new DAOImplementation();
		if (mode.equals("retail")) {
			String name = (String) request.getParameter("name");
			String email = (String) request.getParameter("email");
			String location = (String) request.getParameter("location");
			String password = (String) request.getParameter("pass");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.html");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			Retail retail = new Retail(name, email, location, password);
			boolean result = ride.insert(retail);
			System.out.print(result);
			requestDispatcher.include(request, response);
		} else if (mode.equals("code")) {
			String brand = (String) request.getParameter("brand");
			String model = (String) request.getParameter("model");
			String description = (String) request.getParameter("description");
			String manufacturerName = (String) request.getParameter("manufacturerName");
			String manufacturerLocation = (String) request.getParameter("manufacturerLocation");
			Code code = new Code(brand, model, description, manufacturerName, manufacturerLocation);
			boolean result = ride.insert(code);
			System.out.print(result);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.html");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			requestDispatcher.include(request, response);
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
