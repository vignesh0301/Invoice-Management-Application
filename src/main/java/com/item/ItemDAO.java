package com.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import com.database.DatabaseConnection;

public class ItemDAO {
	private DatabaseConnection dbConnection;

	private static final String CREATE_ITEMS_TABLE = "CREATE TABLE IF NOT EXISTS items (id  int(10) NOT NULL AUTO_INCREMENT,	itemname varchar(120) NOT NULL ,type int(2) NOT NULL,description varchar(100),costPrice DOUBLE(40,10) NOT NULL,sellingPrice DOUBLE(40,10) NOT NULL, companyId int(10) NOT NULL, PRIMARY KEY (id), FOREIGN KEY(companyId) REFERENCES users(id) ON DELETE CASCADE);";
	
	private static final String ADD_ITEM = "Insert into items (itemname,type,description,costPrice,sellingPrice,companyId) values (?,?,?,?,?,?);";
	
	private static final String IS_ITEM_NAME_IN_USE = "select count(*) as ctr from items where itemname=? and companyId=?";
	
	private static final String GET_ITEMS_BY_COMPANY_ID = "select * from items where companyId=?";
	
	private static final String DELETE_ITEM = "delete from items where id=? and companyId=?";
	
	private static final String GET_ITEM_ID_BY_NAME = "select id from items where itemname=? and companyId=?";
	private static final String GET_ITEM_NAME_BY_ID = "select itemname from items where id=? and companyId=?";
	
	private static final String EDIT_ITEM = "update items set itemname=?, type=?, description=?, costPrice=?, sellingPrice=? where id=?";

	public ItemDAO() throws ServletException, ClassNotFoundException, SQLException {
		dbConnection = new DatabaseConnection();
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ITEMS_TABLE)) {
			preparedStatement.executeUpdate();
		}
	}

	//Adding an item
	public void addItem(Item item) throws SQLException, ClassNotFoundException {
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(ADD_ITEM)) {
			ps.setString(1, item.getName());
			ps.setInt(2, item.getType());
			ps.setString(3, item.getDescription());
			ps.setDouble(4, item.getCostPrice());
			ps.setDouble(5, item.getSellingPrice());
			ps.setInt(6, item.getCompanyId());
			ps.executeUpdate();
		}

	}

	//Unique item name
	public boolean isItemNameInUse(String itemname, int companyId) throws SQLException, ClassNotFoundException {

		try (Connection connection = dbConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(IS_ITEM_NAME_IN_USE)) {
			ps.setString(1, itemname);
			ps.setInt(2, companyId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getInt("ctr") > 0)
					return true;
			}
			return false;
		}
	}

	//Getting all items
	public List<Item> getItems(int companyId) throws ClassNotFoundException, SQLException {
		List<Item> items = new ArrayList<>();

		try (Connection conn = dbConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(GET_ITEMS_BY_COMPANY_ID);) {
			statement.setInt(1, companyId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String itemname = rs.getString("itemname"); 
				String description = rs.getString("description"); 
				double CP = rs.getDouble("costPrice"); 
				double SP = rs.getDouble("sellingPrice"); 
				int type = rs.getInt("type");
				items.add(new Item(id,itemname,type,CP,SP,description,companyId));
			}
		}
		return items;

	}

	//Deleting an item
	public void deleteItem(int id, int companyId) throws SQLException, ClassNotFoundException {
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(DELETE_ITEM)) {
			ps.setInt(1, id);
			ps.setInt(2, companyId);
			ps.executeUpdate();
		}

		
	}

	//Getting item id by its name
	public int getIdByName(String name,int companyId) throws SQLException, ClassNotFoundException {
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(GET_ITEM_ID_BY_NAME)) {
			ps.setString(1, name); 
			ps.setInt(2, companyId);
			ResultSet rs = ps.executeQuery(); 
			while(rs.next()) {
				return rs.getInt(1);
			}
			ps.executeUpdate();
		}
		return 0;
	}

	//Get item name by id
	public String getItemNameById(int itemId,int companyId) throws ClassNotFoundException, SQLException {
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(GET_ITEM_NAME_BY_ID)) {
			ps.setInt(1, itemId); 
			ps.setInt(2, companyId);

			ResultSet rs = ps.executeQuery(); 
			while(rs.next()) {
				return rs.getString(1);
			}
		}
		return "Unavailable";
	}

	//Editing item
	public void editItem(int itemId, Item item) throws ClassNotFoundException, SQLException {
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(EDIT_ITEM)) {
			ps.setString(1, item.getName());
			ps.setInt(2, item.getType());
			ps.setString(3, item.getDescription());
			ps.setDouble(4, item.getCostPrice());
			ps.setDouble(5, item.getSellingPrice());
			ps.setInt(6, itemId);
			ps.executeUpdate();
		}
		
	}
}
