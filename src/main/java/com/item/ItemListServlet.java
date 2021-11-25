package com.item;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwt.Jwt;
import com.utilities.Utilities;

public class ItemListServlet extends HttpServlet {

	ItemDAO itemDAO;
	Jwt jwt;
	
	public ItemListServlet() {
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

	//Getting list of items
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int companyId = Utilities.CheckAuth(request, response);
			
			//Getting list of items
			List<Item> items = itemDAO.getItems(companyId);
			request.setAttribute("items",items);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("items.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();

			Utilities.ShowAlert("Something went wrong","login.jsp", response);

		}

	}

}
