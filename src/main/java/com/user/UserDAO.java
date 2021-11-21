package com.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import com.database.DatabaseConnection;


public class UserDAO {
	
	private DatabaseConnection dbConnection;

	private static final String CREATE_USERS_TABLE="CREATE TABLE IF NOT EXISTS users (id  int(10) NOT NULL AUTO_INCREMENT,	username varchar(120) NOT NULL,	email varchar(220) NOT NULL, company varchar(100) not null, password varchar(100),	PRIMARY KEY (id));";
	
	private static final String INSERT_USER = "INSERT INTO users  (username, email,company, password) VALUES  (?,?,?,?);";
	private static final String GET_USER_BY_USERNAME = "select * from users where username=?";
	private static final String GET_ALL_USERS = "select * from users";
	private static final String DELETE_USER_BY_ID = "delete from users where id = ?;";
	
	
	public UserDAO() throws ServletException, ClassNotFoundException,SQLException{
		dbConnection = new DatabaseConnection();
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USERS_TABLE)) {
			preparedStatement.executeUpdate();
		}
	}
	
	
	public void insertUser(User user) throws SQLException, ClassNotFoundException, SQLException {
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCompany());
			preparedStatement.setString(4,user.getPassword());
			preparedStatement.executeUpdate();
		}
	}
	
	public boolean isUsernameInUse(String username) throws ClassNotFoundException, SQLException {
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(GET_USER_BY_USERNAME);) {
			preparedStatement.setString(1, username);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				return true;
			}
		} 
		return false;

	}
	
	public List<User> getAllUsers() throws ClassNotFoundException, SQLException {

		List<User> users = new ArrayList<>();
		try (Connection connection = dbConnection.getConnection();

				PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS);) {
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("username");
				String email = rs.getString("email");
				String country = rs.getString("password");
				String company = rs.getString("company");
				users.add(new User(id, name, email,company, country));
			}
		} 
		return users;
	}
	

	

	public boolean checkCredentials(String username, String password) throws SQLException, ClassNotFoundException {
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(GET_USER_BY_USERNAME);) {
			statement.setString(1,username);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				String pass = rs.getString("password");
				return pass.equals(password);
				
				
			}
		}
		return false;
	}


	public int getCompany(String username) throws ClassNotFoundException, SQLException {
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(GET_USER_BY_USERNAME);) {
			statement.setString(1,username);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				return rs.getInt("id");
			}
		}

		return -1;
	}





}
