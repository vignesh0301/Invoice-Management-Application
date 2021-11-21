package com.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import com.database.DatabaseConnection;

public class CustomerDAO {
	
	private DatabaseConnection dbConnection;
	
	private static final String CREATE_CUSTOMERS_TABLE= "CREATE TABLE IF NOT EXISTS customers (id  int(10) NOT NULL AUTO_INCREMENT,	name varchar(120) NOT NULL,	email varchar(220) NOT NULL,remarks varchar(100),address VARCHAR(100), companyId int(10) NOT NULL, phone VARCHAR(100),	PRIMARY KEY (id), FOREIGN KEY(companyId) REFERENCES users(id) ON DELETE CASCADE);";
	
	private static final String INSERT_CUSTOMER= "Insert into customers (name,email,remarks,address,companyId,phone) values (?,?,?,?,?,?)";
	
	private static final String GET_CUSTOMER_BY_NAME = "select count(*) as ctr from customers where name=? and companyId=?";
	private static final String GET_CUSTOMERS_BY_COMPANY_ID = "select * from customers where companyId=?";
	private static final String GET_NAME_BY_ID = "select name as name from customers where id=? and companyId=? limit 1";
	
	public CustomerDAO() throws ServletException, ClassNotFoundException,SQLException{
		dbConnection = new DatabaseConnection();
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CUSTOMERS_TABLE)) {
			preparedStatement.executeUpdate();
		}
	}
	
	public void insertCustomer(Customer cust)  throws SQLException, ClassNotFoundException, SQLException {
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(INSERT_CUSTOMER)) {
			ps.setString(1, cust.getName()); 
			ps.setString(2,cust.getEmail());
			ps.setString(3, cust.getRemarks());
			ps.setString(4, cust.getAddress()); 
			ps.setInt(5, cust.getCompanyId());
			ps.setString(6, cust.getPhone());
			ps.executeUpdate();
		}
	}
	
	public boolean isCustomerNameInUse(String name,int companyId) throws SQLException, ClassNotFoundException,SQLException{
			try (Connection connection = dbConnection.getConnection();
					PreparedStatement ps = connection.prepareStatement(GET_CUSTOMER_BY_NAME)) {
				ps.setString(1,name); 
				ps.setInt(2, companyId);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					if(rs.getInt("ctr")>0)
					return true;
				}
				return false;
			}
		
	}

	public List<Customer> getCustomers(int companyId) throws SQLException, ClassNotFoundException {
		try(Connection conn = dbConnection.getConnection();
				PreparedStatement ps=conn.prepareStatement(GET_CUSTOMERS_BY_COMPANY_ID)){
			List<Customer> customers = new ArrayList<>();
			ps.setInt(1, companyId);
			ResultSet rs = ps.executeQuery(); 
			while(rs.next()) {
				customers.add(new Customer(rs.getInt("id"),rs.getString("name"),rs.getInt("companyId")));
				
			}
			return customers;
			
		}
	}

	public String getNameById(int customerId,int companyId) throws ClassNotFoundException, SQLException {
		try(Connection conn = dbConnection.getConnection();
				PreparedStatement ps=conn.prepareStatement(GET_NAME_BY_ID)){
			ps.setInt(1, customerId);
			ps.setInt(2, companyId);
			ResultSet rs = ps.executeQuery(); 
			while(rs.next()) {
				return rs.getString(1);
				
			}
			return "Not Found";
			
		}
	}
}
