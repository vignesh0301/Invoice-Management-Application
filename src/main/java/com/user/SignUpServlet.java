package com.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpServlet extends HttpServlet {
	public SignUpServlet() {
		super();
	}

	private UserDAO userDAO;

	public void init() throws ServletException {
		try {
			userDAO = new UserDAO();
		} catch (ClassNotFoundException | ServletException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Post Method for Signup
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String username = request.getParameter("username");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String company = request.getParameter("company");

			if (userDAO.isUsernameInUse(username)) {
				PrintWriter out = response.getWriter();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Username is already in use');");
				out.println("location='signup.jsp';");
				out.println("</script>");
				return;

			}
			User newUser = new User(username, email,company, password);

			userDAO.insertUser(newUser);
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('User created successfully');");
			out.println("location='login.jsp';");
			out.println("</script>");
		}

		catch (Exception e) {
			e.printStackTrace();
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Something went wrong');");
			out.println("location='login.jsp';");
			out.println("</script>");

		}
	}

}
