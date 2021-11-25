package com.customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.invoice.InvoiceDAO;
import com.jwt.Jwt;
import com.utilities.Utilities;

public class DeleteCustomerServlet extends HttpServlet {

	public DeleteCustomerServlet() {
		super();
	}

	CustomerDAO customerDAO;
	InvoiceDAO invoiceDAO;
	Jwt jwt;

	public void init() throws ServletException {
		try {
			customerDAO = new CustomerDAO();
			invoiceDAO = new InvoiceDAO();
			jwt = new Jwt();
		} catch (ClassNotFoundException | ServletException | SQLException e) {
			e.printStackTrace();
		}
	}

	//Deleting a customer
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int companyId = Utilities.CheckAuth(request, response);

			int id = Integer.parseInt(request.getParameter("id"));
			if (invoiceDAO.isCustomerAppliedToInvoice(id)) {
				Utilities.ShowAlert("Customer applied to invoice cannot be deleted","viewcustomer", response);
			} else {
				customerDAO.deleteCustomer(id);
				Utilities.ShowAlert("customer deleted successfully","viewcustomer", response);
			}
		} catch (Exception e) {
			e.printStackTrace();

			Utilities.ShowAlert("Something went wrong","login.jsp", response);

		}

	}

}
