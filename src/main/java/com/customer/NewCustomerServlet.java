package com.customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwt.Jwt;
import com.utilities.Utilities;

public class NewCustomerServlet extends HttpServlet {

	CustomerDAO customerDAO;
	Jwt jwt;

	public NewCustomerServlet() {
	}

	public void init() throws ServletException {
		try {
			customerDAO = new CustomerDAO();
			jwt = new Jwt();
		} catch (ClassNotFoundException | ServletException | SQLException e) {
			e.printStackTrace();
		}
	}

	//Add customer
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			int companyId = Utilities.CheckAuth(request, response);

			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			String phone = request.getParameter("phone");

			Customer cust = new Customer(name, email, address, companyId, phone);
			if (customerDAO.isCustomerNameInUse(name,companyId)) {
				PrintWriter out = response.getWriter();
				Utilities.ShowAlert("Customer name should be unique","addcustomer.jsp", response);

			} else {
				customerDAO.insertCustomer(cust);
				
				Utilities.ShowAlert("Customer created successfully","viewcustomer", response);

			}
		} catch (Exception e) {
			e.printStackTrace();

			Utilities.ShowAlert("Something went wrong","login.jsp", response);

		}

	}

}
