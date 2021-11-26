package com.invoice;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.utilities.Utilities;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.customer.Customer;
import com.customer.CustomerDAO;
import com.item.Item;
import com.item.ItemDAO;
import com.jwt.Jwt;


public class NewInvoiceServlet extends HttpServlet {
	
	InvoiceDAO invoiceDAO;
	CustomerDAO customerDAO;
	ItemDAO itemDAO;
	Jwt jwt;


	public void init() throws ServletException {
		try {
			invoiceDAO = new InvoiceDAO();
			customerDAO = new CustomerDAO();
			itemDAO = new ItemDAO();
			jwt = new Jwt();
		} catch (ClassNotFoundException | ServletException | SQLException e) {
			e.printStackTrace();
		}
	}
    public NewInvoiceServlet() {
        super();
    }

    //Creating invoice
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			int companyId = Utilities.CheckAuth(request, response);
			
			java.sql.Date date = Utilities.DateConverter(request.getParameter("date"));
			java.sql.Date dueDate = Utilities.DateConverter(request.getParameter("dueDate"));
			
			int customerId = Integer.parseInt( request.getParameter("customerId")); 
			int invoiceNo = invoiceDAO.getInvoiceNumber(companyId);
			
			double discount = Double.parseDouble(request.getParameter("discount"));
			
			double Amount = Double.parseDouble(request.getParameter("amt"));
			
			double discountAmount = (discount*Amount)/100;
			
			
			
			List<InvoiceItems> invoiceitems = new ArrayList<>(); 
			
			
			String[] ids = request.getParameterValues("id");
			for (String id : ids) {
				String name = request.getParameter("itemname"+id);
				int itemId = itemDAO.getIdByName(name,companyId);
				double quantity = Double.parseDouble(request.getParameter("quantity"+id)); 
				double price = Double.parseDouble(request.getParameter("price"+id));
				invoiceitems.add(new InvoiceItems(itemId,quantity,price));
			}
			
			invoiceDAO.CreateInvoice(new Invoice(customerId,date,dueDate,companyId,invoiceNo,discount,discountAmount,Amount,Amount-discountAmount));
			invoiceDAO.insertInvoiceItems(invoiceitems,companyId,invoiceNo);
			
			Utilities.ShowAlert("Invoice has been created","home", response);

		}
		catch (Exception e) {
			e.printStackTrace();

			Utilities.ShowAlert("Something went wrong","login.jsp", response);
		}
	}
	
	//Invoice creation values
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			int companyId = Utilities.CheckAuth(request, response);
			
			//setting customers
			List<Customer> customers = customerDAO.getCustomers(companyId);
			request.setAttribute("customers",customers);
			
			//setting items
			List<Item> items = itemDAO.getItems(companyId); 
			request.setAttribute("items", items);
			
			//setting invoiceNo
			request.setAttribute("invoiceNo", invoiceDAO.getInvoiceNumber(companyId));
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("newinvoice.jsp");
			dispatcher.forward(request, response);
			
			
		}
		catch(Exception e) {
			e.printStackTrace();

			Utilities.ShowAlert("Something went wrong","login.jsp", response);
			
		}
		
	}
	

}
