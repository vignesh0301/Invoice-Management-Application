package com.invoice;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwt.Jwt;
import com.utilities.Utilities;

public class PayInvoiceServlet extends HttpServlet {

	
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
	
    public PayInvoiceServlet() {
        super();
    }

    //Payment update
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int companyId = Utilities.CheckAuth(request, response);
			
			int invoiceNo = Integer.parseInt(request.getParameter("invoiceNo"));
			
			java.sql.Date paidDate = Utilities.DateConverter(request.getParameter("paidDate"));
			
			invoiceDAO.pay(companyId,invoiceNo,paidDate); 
			
			Utilities.ShowAlert("Payment made successfully","home", response);

			
		}
		catch(Exception e) {
			e.printStackTrace();

			Utilities.ShowAlert("Something went wrong","login.jsp", response);

		}
	}

}