package com.customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwt.Jwt;
import com.utilities.Utilities;

public class ViewCustomerServlet extends HttpServlet {
	CustomerDAO customerDAO;
	Jwt jwt;

	public void init() throws ServletException {
		try {
			customerDAO = new CustomerDAO();
			jwt = new Jwt();
		} catch (ClassNotFoundException | ServletException | SQLException e) {
			e.printStackTrace();
		}
	}
	//Getting customer details
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			int companyId = Utilities.CheckAuth(request, response);
			
			List<Customer> customers = customerDAO.getCustomers(companyId); 
			request.setAttribute("customers", customers);
			RequestDispatcher dispatcher = request.getRequestDispatcher("viewcustomer.jsp");
			dispatcher.forward(request, response);
		} 
		catch (Exception e) {
			e.printStackTrace();

			Utilities.ShowAlert("Something went wrong","login.jsp", response);

		}
	}

}
