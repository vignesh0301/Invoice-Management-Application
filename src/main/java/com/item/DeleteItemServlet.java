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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int companyId = jwt.getCustomerId((String) request.getSession().getAttribute("token"));
			if (companyId <= 0)
				response.sendRedirect(request.getContextPath() + "/login");

			int id = Integer.parseInt(request.getParameter("id"));
			if (invoiceDAO.isItemAppliedToInvoice(id,companyId)) {
				PrintWriter out = response.getWriter();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Item applied to invoice cant be deleted.');");
				out.println("location='items';");
				out.println("</script>");
			} 
			else {
				itemDAO.deleteItem(id, companyId);

				PrintWriter out = response.getWriter();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Item deleted Successfully');");
				out.println("location='items';");
				out.println("</script>");
			}
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
