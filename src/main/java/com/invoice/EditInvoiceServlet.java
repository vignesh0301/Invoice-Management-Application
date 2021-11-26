package com.invoice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import com.utilities.Utilities;

public class EditInvoiceServlet extends HttpServlet {

	public EditInvoiceServlet() {
		super();
	}

	Jwt jwt;
	InvoiceDAO invoiceDAO;
	CustomerDAO customerDAO;
	ItemDAO itemDAO;

	public void init() throws ServletException {
		jwt = new Jwt();
		try {
			invoiceDAO = new InvoiceDAO();
			customerDAO = new CustomerDAO();
			itemDAO = new ItemDAO();
		} catch (ClassNotFoundException | ServletException | SQLException e) {
			e.printStackTrace();
		}
	}

	//Getting invoice details
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			int companyId = Utilities.CheckAuth(request, response);

			int invoiceNo = Integer.parseInt(request.getParameter("invoiceNo"));

			// Setting list of customers
			List<Customer> customers = customerDAO.getCustomers(companyId);
			request.setAttribute("customers", customers);

			// setting list of items
			List<Item> items = itemDAO.getItems(companyId);
			request.setAttribute("items", items);

			// Getting all details
			List<InvoiceItems> invoiceitems = invoiceDAO.getInvoiceItems(companyId, invoiceNo);
			Invoice invoice = invoiceDAO.getInvoiceById(companyId, invoiceNo);

			// Setting invoice No
			request.setAttribute("invoiceNo", invoiceNo);
			request.setAttribute("discount", invoice.getDiscount());

			// Setting date
			request.setAttribute("date", invoice.getDate());
			request.setAttribute("dueDate", invoice.getDueDate());

			// Setting customer name and id
			request.setAttribute("name", customerDAO.getNameById(invoice.getCustomerId()));
			request.setAttribute("customerId", invoice.getCustomerId());

			List<Item> itemlist = new ArrayList<>();
			for (int i = 0; i < invoiceitems.size(); i++) {
				String name = itemDAO.getItemNameById(invoiceitems.get(i).getItemId(), companyId);
				double quantity = invoiceitems.get(i).getQuantity();
				double price = invoiceitems.get(i).getPrice();
				itemlist.add(new Item(name, quantity, price));

			}

			// Seting item details
			request.setAttribute("countofitems", itemlist.size());
			request.setAttribute("itemlist", itemlist);
			request.setAttribute("totalAmount", invoice.getTotalAmount());
			request.setAttribute("Amount", invoice.getAmount());

			RequestDispatcher dispatcher = request.getRequestDispatcher("editinvoice.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();

			Utilities.ShowAlert("Something went wrong", "login.jsp", response);

		}

	}

	//Editing invoice
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int companyId = Utilities.CheckAuth(request, response);

			java.sql.Date date = Utilities.DateConverter(request.getParameter("date"));
			java.sql.Date dueDate = Utilities.DateConverter(request.getParameter("dueDate"));

			int customerId = Integer.parseInt(request.getParameter("customerId"));
			int invoiceNo =Integer.parseInt(request.getParameter("invoiceNo"));

			double discount = Double.parseDouble(request.getParameter("discount"));

			double Amount = Double.parseDouble(request.getParameter("amt"));

			double discountAmount = (discount * Amount) / 100;
			
            List<InvoiceItems> invoiceitems = new ArrayList<>(); 
			
			
			String[] ids = request.getParameterValues("id");
			for (String id : ids) {
				String name = request.getParameter("itemname"+id);
				int itemId = itemDAO.getIdByName(name,companyId);
				double quantity = Double.parseDouble(request.getParameter("quantity"+id)); 
				double price = Double.parseDouble(request.getParameter("price"+id));
				invoiceitems.add(new InvoiceItems(itemId,quantity,price));
			}
			invoiceDAO.UpdateInvoice(new Invoice(customerId,date,dueDate,null,companyId,invoiceNo,discount,discountAmount,Amount,Amount-discountAmount));
			invoiceDAO.UpdateInvoiceItems(invoiceitems,companyId,invoiceNo);
			Utilities.ShowAlert("Invoice has been updated","home", response);
			
		} catch (Exception e) {
			e.printStackTrace();
			Utilities.ShowAlert("Something went wrong", "login.jsp", response);
		}

	}

}
