package com.customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwt.Jwt;

public class NewCustomerServlet extends HttpServlet {

	CustomerDAO customerDAO;
	Jwt jwt;

	public NewCustomerServlet() {
	}

	public void init() throws ServletException {
		try {
			customerDAO = new CustomerDAO();
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

			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			String remarks = request.getParameter("remarks");
			String phone = request.getParameter("phone");

			Customer cust = new Customer(name, email, address, remarks, companyId, phone);
			if (customerDAO.isCustomerNameInUse(name,companyId)) {
				PrintWriter out = response.getWriter();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Customer name should be unique.');");
				out.println("location='addcustomer.jsp';");
				out.println("</script>");

			} else {
				customerDAO.insertCustomer(cust);
				PrintWriter out = response.getWriter();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Customer created successfully');");
				out.println("location='addcustomer.jsp';");
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
