package com.customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwt.Jwt;
import com.utilities.Utilities;

public class EditCustomerServlet extends HttpServlet {

	CustomerDAO customerDAO;
	Jwt jwt;

	public EditCustomerServlet() {
		super();
	}

	public void init() throws ServletException {
		try {
			customerDAO = new CustomerDAO();
			jwt = new Jwt();
		} catch (ClassNotFoundException | ServletException | SQLException e) {
			e.printStackTrace();
		}
	}

	//Editing a customer
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int companyId = Utilities.CheckAuth(request, response);
			
			//Editing customer details
			int customerId = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			String phone = request.getParameter("phone");
			Customer cust = new Customer(name, email, address,  companyId, phone);
			customerDAO.UpdateCustomer(cust,customerId);
			
			Utilities.ShowAlert("Customer details updated","viewcustomer", response);

			
		} catch (Exception e) {
			e.printStackTrace();

			Utilities.ShowAlert("Something went wrong","login.jsp", response);

		}

	}

}
