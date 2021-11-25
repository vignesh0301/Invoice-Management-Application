package com.item;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwt.Jwt;
import com.utilities.Utilities;

public class EditItemServlet extends HttpServlet {

    
	ItemDAO itemDAO;
	Jwt jwt;

	public EditItemServlet() {
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
    
	//Update item
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int companyId = Utilities.CheckAuth(request, response);
			
			int itemId = Integer.parseInt(request.getParameter("itemId"));
			String itemname = request.getParameter("itemname");
			int type =Integer.parseInt(request.getParameter("type"));
			double CP = Double.parseDouble(request.getParameter("costprice"));
			double SP = Double.parseDouble(request.getParameter("sellingprice"));
			String description = request.getParameter("description");

				Item item = new Item(itemname, type, CP, SP, description, companyId);
				//Update item
				itemDAO.editItem(itemId,item);
				Utilities.ShowAlert("Item details has been updated","items", response);

		} catch (Exception e) {
			e.printStackTrace();

			Utilities.ShowAlert("Something went wrong","login.jsp", response);

		}

	}

}
