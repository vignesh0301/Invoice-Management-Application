package com.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.utilities.Utilities;

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
				Utilities.ShowAlert("Username is in use","signup.jsp", response);
				return;

			}
			User newUser = new User(username, email,company, password);

			userDAO.insertUser(newUser);
			Utilities.ShowAlert("User created successfully","login.jsp", response);

		}

		catch (Exception e) {
			e.printStackTrace();
			Utilities.ShowAlert("Something went wrong","login.jsp", response);

		}
	}

}
