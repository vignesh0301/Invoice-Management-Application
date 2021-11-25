package com.item;

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

public class DeleteItemServlet extends HttpServlet {
	public DeleteItemServlet() {
		super();
	}

	ItemDAO itemDAO;
	InvoiceDAO invoiceDAO;
	Jwt jwt;

	public void init() throws ServletException {
		try {
			itemDAO = new ItemDAO();
			invoiceDAO = new InvoiceDAO();
			jwt = new Jwt();
		} catch (ClassNotFoundException | ServletException | SQLException e) {
			e.printStackTrace();
		}
	}
	

	//Delete item
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int companyId = Utilities.CheckAuth(request, response);

			int id = Integer.parseInt(request.getParameter("id"));
			if (invoiceDAO.isItemAppliedToInvoice(id)) {
				Utilities.ShowAlert("Item applied to invoice cant be deleted","items", response);

			} 
			else {
				itemDAO.deleteItem(id, companyId);
				Utilities.ShowAlert("Item deleted successfully","items", response);

			}
		} catch (Exception e) {
			e.printStackTrace();
			Utilities.ShowAlert("Something went wrong","login.jsp", response);


		}

	}

}
