package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
	public DatabaseConnection(){
		
	}
	
	private String jdbcURL = "jdbc:mysql://localhost:3306/invoice?useSSL=false&createDatabaseIfNotExist=true";
	private String jdbcUsername = "vignesh";
	private String jdbcPassword = "password";

	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection connection = null;
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		return connection;
	}
}
