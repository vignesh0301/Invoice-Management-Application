package com.invoice;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwt.Jwt;


public class HomeServlet extends HttpServlet {
	
	InvoiceDAO invoiceDAO; 
	Jwt jwt;
	
    public HomeServlet() {
        super();
    }
    
    public void init() throws ServletException {
		try {
			invoiceDAO = new InvoiceDAO();
			jwt = new Jwt();
		} catch (ClassNotFoundException | ServletException | SQLException e) {
			e.printStackTrace();
		}
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			int companyId = jwt.getCustomerId((String) request.getSession().getAttribute("token")) ;
			if(companyId <=0 ) response.sendRedirect(request.getContextPath()+"/login");
			
			List<Invoice> invoices = invoiceDAO.getInvoices(companyId);
			request.setAttribute("invoices",invoices);

			request.setAttribute("today", new Date());
			RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
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
