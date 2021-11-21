package com.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLDataException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwt.Jwt;

public class LoginServlet extends HttpServlet {

	UserDAO userDAO ;
	Jwt jwt;
	public LoginServlet() {
		super();
	}

	public void init() throws ServletException {
		try {
			userDAO = new UserDAO();
			jwt = new Jwt();
		} catch (ClassNotFoundException | ServletException | SQLException e) {
			e.printStackTrace();
		}
	}

	//Post Method for login
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User u = new User();

		try {
			if (userDAO.checkCredentials(username, u.encrypt(password)) == true) {

				String token = jwt.generateToken(userDAO.getCompany(username)); 
				request.getSession().setAttribute("token",token);
				response.sendRedirect(request.getContextPath() + "/home");
				
			} else {

				PrintWriter out = response.getWriter();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Invalid credentials');");
				out.println("location='login.jsp';");
				out.println("</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Something went wrong');");
			out.println("location='login.jsp';");
			out.println("</script>");
		}
	}

}
