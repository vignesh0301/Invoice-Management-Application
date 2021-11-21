package com.invoice;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.customer.CustomerDAO;
import com.item.Item;
import com.item.ItemDAO;
import com.jwt.Jwt;

public class ViewInvoiceServlet extends HttpServlet {

	public ViewInvoiceServlet() {
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			int companyId = jwt.getCustomerId((String) request.getSession().getAttribute("token"));
			if (companyId <= 0)
				response.sendRedirect(request.getContextPath() + "/login");

			int invoiceNo = Integer.parseInt(request.getParameter("invoiceNo"));

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
			request.setAttribute("name", customerDAO.getNameById(invoice.getCustomerId(),companyId));
			request.setAttribute("customerId", invoice.getCustomerId());

			if (invoice.getPaidDate() == null) {
				request.setAttribute("paid", false);
			} else {
				request.setAttribute("paid", true);
				request.setAttribute("paidDate", invoice.getPaidDate());
			}

			double totalAmount = 0;
			List<Item> items = new ArrayList<>();
			for (int i = 0; i < invoiceitems.size(); i++) {
				String name = itemDAO.getItemNameById(invoiceitems.get(i).getItemId(),companyId);
				double quantity = invoiceitems.get(i).getPrice();
				double price = invoiceitems.get(i).getQuantity();
				totalAmount += (quantity * price);
				items.add(new Item(name, quantity, price));

			}

			// Seting item details
			request.setAttribute("items", items);
			request.setAttribute("totalAmount", totalAmount);

			RequestDispatcher dispatcher = request.getRequestDispatcher("view.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Something went wrong');");
			out.println("location='login.jsp';");
			out.println("</script>");
		}
	}

}
