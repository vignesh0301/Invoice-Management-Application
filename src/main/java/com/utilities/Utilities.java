package com.utilities;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwt.Jwt;

public class Utilities {
	//Date conversion
	public static java.sql.Date DateConverter(String date) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date1 = df.parse(date);
		return new java.sql.Date(date1.getTime()); 
	}
	
	//authentication checking
	public static int CheckAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Jwt jwt = new Jwt();
		if(request.getSession().getAttribute("token")==null) {
			response.sendRedirect(request.getContextPath()+"/login.jsp");
		}
		int companyId = jwt.getCustomerId((String) request.getSession().getAttribute("token")) ;
		if(companyId <=0 ) {
			response.sendRedirect(request.getContextPath()+"/login.jsp");
		}
		return companyId;
		
	}
	
	//alert
	public static void ShowAlert(String message,String target,HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<script type=\"text/javascript\">");
		out.println("alert('"+message+"');");
		out.println("location='"+target+"';");
		out.println("</script>");
	}
}
