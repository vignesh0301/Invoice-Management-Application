package com.user;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.utilities.Utilities;

public class DeleteUser extends HttpServlet {
	UserDAO userDAO;

	public DeleteUser() {
		super();
	}

	public void init() throws ServletException {
		try {
			userDAO = new UserDAO();
		} catch (ClassNotFoundException | ServletException | SQLException e) {
			e.printStackTrace();
		}
	}
 //Deleting user from db
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int companyId = Utilities.CheckAuth(request, response);
			String password = request.getParameter("password");
			
			boolean flag = userDAO.DeleteUser(companyId,password);
			if(flag) {
		    	request.getSession().setAttribute("token", "");
		    	Utilities.ShowAlert("Bye!!!", "login.jsp", response);
			} 
			else {
				Utilities.ShowAlert("Wrong Password!!!", "settings.jsp",response);
			}
		} catch (Exception e) {
			e.printStackTrace();

			Utilities.ShowAlert("Something went wrong", "login.jsp", response);
		}

	}

}
