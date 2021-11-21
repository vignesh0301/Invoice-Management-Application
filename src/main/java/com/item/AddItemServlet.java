package com.item;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.customer.Customer;
import com.jwt.Jwt;

public class AddItemServlet extends HttpServlet {

	ItemDAO itemDAO;
	Jwt jwt;

	public AddItemServlet() {
		super();
	}

	public void init() throws ServletException {
		try {
			itemDAO = new ItemDAO();
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

			String itemname = request.getParameter("itemname");
			String type = request.getParameter("type");
			double CP = Double.parseDouble(request.getParameter("costprice"));
			double SP = Double.parseDouble(request.getParameter("sellingprice"));
			String description = request.getParameter("description");

			if (itemDAO.isItemNameInUse(itemname, companyId)) {
				PrintWriter out = response.getWriter();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Itemname should be unique');");
				out.println("location='additem.jsp';");
				out.println("</script>");
			}

			else {
				Item item = new Item(itemname, type, CP, SP, description, companyId);
				itemDAO.addItem(item);

				PrintWriter out = response.getWriter();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Item created Successfully');");
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
