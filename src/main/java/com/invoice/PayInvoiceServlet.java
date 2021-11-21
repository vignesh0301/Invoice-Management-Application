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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int companyId = jwt.getCustomerId((String) request.getSession().getAttribute("token")) ;
			if(companyId <=0 ) response.sendRedirect(request.getContextPath()+"/login");
			
			int invoiceNo = Integer.parseInt(request.getParameter("invoiceNo"));
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date paidDate1 = df.parse(request.getParameter("paidDate"));
			java.sql.Date paidDate= new java.sql.Date(paidDate1.getTime()); 
			
			invoiceDAO.pay(companyId,invoiceNo,paidDate); 
			
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Payment made successfully');");
			out.println("location='home'");
			out.println("</script>");
			
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
