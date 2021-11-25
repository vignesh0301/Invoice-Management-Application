package com.invoice;

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

public class DeleteInvoiceServlet extends HttpServlet {

	public DeleteInvoiceServlet() {
		super();
	}

	InvoiceDAO invoiceDAO;
	Jwt jwt;

	public void init() throws ServletException {
		try {
			invoiceDAO = new InvoiceDAO();
			jwt = new Jwt();
		} catch (ClassNotFoundException | ServletException | SQLException e) {
			e.printStackTrace();
		}
	}

	//Deleting invoice
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int companyId = Utilities.CheckAuth(request, response);

			int invoiceNo = Integer.parseInt(request.getParameter("invoiceNo"));
			// Delete invoice
			invoiceDAO.deleteInvoice(invoiceNo, companyId);

			Utilities.ShowAlert("Invoice has been deleted", "home", response);
			;
		} catch (Exception e) {
			e.printStackTrace();

			Utilities.ShowAlert("Something went wrong", "login.jsp", response);

		}
	}

}
