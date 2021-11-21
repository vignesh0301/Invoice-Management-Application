package com.invoice;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int companyId = jwt.getCustomerId((String) request.getSession().getAttribute("token")) ;
			if(companyId <=0 ) response.sendRedirect(request.getContextPath()+"/login");
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date1 = df.parse(request.getParameter("date"));
			java.sql.Date date = new java.sql.Date(date1.getTime()); 
			
			java.util.Date duedate1 = df.parse(request.getParameter("dueDate"));
			java.sql.Date dueDate = new java.sql.Date(duedate1.getTime()); 
			
			int customerId = Integer.parseInt( request.getParameter("customerId")); 
			int invoiceNo = invoiceDAO.getInvoiceNumber(companyId);
			
			double discount = Double.parseDouble(request.getParameter("discount"));
			
			invoiceDAO.createInvoice(new Invoice(customerId,date,dueDate,null,companyId,invoiceNo,discount));
			
			
			List<InvoiceItems> invoiceitems = new ArrayList<>(); 
			
			
			String[] ids = request.getParameterValues("id");
			for (String id : ids) {
				String name = request.getParameter("itemname"+id);
				int itemId = itemDAO.getIdByName(name,companyId);
				double quantity = Double.parseDouble(request.getParameter("quantity"+id)); 
				double price = Double.parseDouble(request.getParameter("price"+id));
				invoiceitems.add(new InvoiceItems(itemId,quantity,price));
				PrintWriter out = response.getWriter();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Invoice has been created');");
				out.println("location='newinvoice';");
				out.println("</script>");
			}
			
			invoiceDAO.insertInvoiceItems(invoiceitems,companyId,invoiceNo);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Something went wrong');");
			out.println("location='newinvoice';");
			out.println("</script>");
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			int companyId = jwt.getCustomerId((String) request.getSession().getAttribute("token")) ;
			if(companyId <=0 ) response.sendRedirect(request.getContextPath()+"/login");
			
			List<Customer> customers = customerDAO.getCustomers(companyId);
			request.setAttribute("customers",customers);
			
			List<Item> items = itemDAO.getItems(companyId); 
			request.setAttribute("items", items);
			
			request.setAttribute("invoiceNo", invoiceDAO.getInvoiceNumber(companyId));
			RequestDispatcher dispatcher = request.getRequestDispatcher("newinvoice.jsp");
			dispatcher.forward(request, response);
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Something went wrong');");
			out.println("location='login.jsp';");
			out.println("</script>");			
		}
		
	}
	

}
