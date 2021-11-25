package com.invoice;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import com.customer.CustomerDAO;
import com.database.DatabaseConnection;

public class InvoiceDAO {
	private DatabaseConnection dbConnection;
	
	private static final String CREATE_INVOICE_TABLE = "CREATE TABLE IF NOT EXISTS invoice (id  int(10) NOT NULL AUTO_INCREMENT, date DATE NOT NULL, dueDate DATE NOT NULL,paidDate DATE,customerId int(10),invoiceNo int(10) NOT NULL,companyId int(10) NOT NULL,discount DOUBLE(40,2) NOT NULL,discountAmount DOUBLE(40,2) NOT NULL,Amount DOUBLE(40,2) NOT NULL,TotalAmount DOUBLE(40,2) NOT NULL,	PRIMARY KEY (id), FOREIGN KEY(companyId) REFERENCES users(id) ON DELETE CASCADE);";
	private static final String CREATE_INVOICE_ITEMS_TABLE = "Create TABLE IF NOT EXISTS invoiceitems (id int(10) NOT NULL AUTO_INCREMENT,invoiceId int(10) NOT NULL,itemId int(10) NOT NULL,quantity DOUBLE(40,10) NOT NULL,price DOUBLE(40,10) NOT NULL,PRIMARY  KEY(id) , FOREIGN KEY(invoiceId) REFERENCES invoice(id) ON DELETE CASCADE);";
	
	private static final String INSERT_INTO_INVOICE_TABLE = "insert into invoice (date,dueDate,customerId,companyId,invoiceNo,discount,discountAmount,Amount,totalAmount) values (?,?,?,?,?,?,?,?,?);";
	private static final String INSERT_INTO_INVOICE_ITEMS_TABLE = "insert into invoiceitems (invoiceId,itemId,quantity,price) values (?,?,?,?);";
	
	private static final String GET_INVOICE_NUMBER = "select max(invoiceNo) as ctr from invoice where companyId=?";
	private static final String GET_INVOICE_ID = "select id as id from invoice where companyId=? and invoiceNo=?";
	private static final String GET_INVOICES = "select * from invoice where companyId=? order by invoiceNo desc";
	private static final String GET_INVOICE_BY_ID = "select * from invoice where companyId=? and invoiceNo=?";
	private static final String GET_INVOICE_ITEMS = "select * from invoiceitems where invoiceId=?";
	
	private static final String DELETE_INVOICE ="delete from invoice where invoiceNo=? and companyId=?";
	
	private static final String UPDATE_INVOICE = "update invoice set date=?,dueDate=?,customerId=?,discount=?,discountAmount=?,Amount=?,totalAmount=? where invoiceNo=? and companyId=?";
	
	private static final String IS_ITEM_APPLIED_TO_INVOICE = "select count(*) as ctr from invoiceitems where itemId=?";
	
	private static final String MAKE_PAYMENT = "update invoice set paidDate=? where companyId=? and invoiceNo=?";

	private static final String IS_CUSTOMER_APPLIED_TO_INVOICE = "select count(*) as ctr from invoice where customerId=?";

	private static final String DELETE_INVOICE_ITEMS = "delete from invoiceitems where invoiceId=?";

	CustomerDAO customerDAO = new CustomerDAO();
	
	public InvoiceDAO() throws ServletException, ClassNotFoundException, SQLException {
		dbConnection = new DatabaseConnection();
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(CREATE_INVOICE_TABLE)) {
			preparedStatement.executeUpdate();
		}
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(CREATE_INVOICE_ITEMS_TABLE)) {
			preparedStatement.executeUpdate();
		}
	}

	//Creating an invoice
	public void CreateInvoice(Invoice invoice) throws ClassNotFoundException, SQLException {
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(INSERT_INTO_INVOICE_TABLE)) {
			int customerId = invoice.getCustomerId();
			int companyId = invoice.getCompanyId();
			Date date = invoice.getDate();
			Date dueDate = invoice.getDueDate();
			ps.setDate(1, date);
			ps.setDate(2, dueDate);
			ps.setInt(3, customerId);
			ps.setInt(4, companyId);
			ps.setInt(5, invoice.getInvoiceNo());
			ps.setDouble(6, invoice.getDiscount());
			ps.setDouble(7, invoice.getDiscountAmount());
			ps.setDouble(8, invoice.getAmount());
			ps.setDouble(9, invoice.getTotalAmount());
			ps.executeUpdate();

		}
	}

	//Getting auto invoice number
	public int getInvoiceNumber(int companyId) throws ClassNotFoundException, SQLException {
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(GET_INVOICE_NUMBER)) {
			ps.setInt(1, companyId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt("ctr") + 1;
			}
			return 1;
		}
	}

	//inserting invoice items to db
	public void insertInvoiceItems(List<InvoiceItems> items, int companyId, int invoiceNo)
			throws ClassNotFoundException, SQLException {
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(INSERT_INTO_INVOICE_ITEMS_TABLE)) {

			int invoiceId = getInvoiceId(companyId,invoiceNo);
			for (int i = 0; i < items.size(); i++) {
				ps.setInt(1, invoiceId);
				ps.setInt(2, items.get(i).getItemId());
				ps.setDouble(3, items.get(i).getQuantity());
				ps.setDouble(4, items.get(i).getPrice());
				ps.executeUpdate();
			}

		}

	}

	private int getInvoiceId(int companyId, int invoiceNo) throws ClassNotFoundException, SQLException {
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(GET_INVOICE_ID)) {
			ps.setInt(1, companyId);
            ps.setInt(2, invoiceNo);
            ResultSet rs= ps.executeQuery(); 
            while(rs.next()) {
            	return rs.getInt("id");
            }
            return 0;
		}
	}
	
	public List<Invoice> getInvoices(int companyId) throws SQLException, ClassNotFoundException {
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(GET_INVOICES)) {
			List<Invoice> invoices = new ArrayList<>();
			ps.setInt(1, companyId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int customerId = rs.getInt("customerId");
				Date date = rs.getDate("date");
				Date dueDate = rs.getDate("dueDate");
				int invoiceNo = rs.getInt("invoiceNo");
				double discount = rs.getDouble("discount");
				Date paidDate = rs.getDate("paidDate");
				double discountAmount = rs.getDouble("discountAmount");
				double Amount = rs.getDouble("Amount");

				double totalAmount = rs.getDouble("totalAmount");

				invoices.add(new Invoice(customerId, date, dueDate, paidDate, companyId, invoiceNo, discount,discountAmount,Amount,totalAmount,customerDAO.getNameById(customerId)));
			}
			return invoices;

		}
	}
	//Getting invoice by it's id
	public Invoice getInvoiceById(int companyId, int invoiceId) throws ClassNotFoundException, SQLException {
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(GET_INVOICE_BY_ID)) {
			ps.setInt(1, companyId);
			ps.setInt(2, invoiceId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Date date = rs.getDate("date");
				Date dueDate = rs.getDate("dueDate");
				Date paidDate = rs.getDate("paidDate");
				int customerId = rs.getInt("customerId");
				double discount = rs.getDouble("discount");
				double discountAmount = rs.getDouble("discountAmount");
				double Amount = rs.getDouble("Amount");

				double totalAmount = rs.getDouble("totalAmount");

				return new Invoice(customerId, date, dueDate, paidDate, companyId, invoiceId, discount,discountAmount,Amount,totalAmount);

			}
			return null;

		}
	}
	//getting invoice items for display 
	public List<InvoiceItems> getInvoiceItems(int companyId, int invoiceNo)
			throws SQLException, ClassNotFoundException {
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(GET_INVOICE_ITEMS)) {
			List<InvoiceItems> invoiceitems = new ArrayList<>();
			int f = getInvoiceId(companyId,invoiceNo);
			ps.setInt(1, f);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int itemId = rs.getInt("itemId");
				double quantity = rs.getDouble("quantity");
				double price = rs.getDouble("price");
				invoiceitems.add(new InvoiceItems(f, itemId, quantity, price));
			}
			return invoiceitems;

		}
	}
	
	//Payment 
	public void pay(int companyId, int invoiceNo, Date paidDate) throws ClassNotFoundException, SQLException {
		try (Connection conn = dbConnection.getConnection();

				PreparedStatement ps = conn.prepareStatement(MAKE_PAYMENT)) {
			ps.setDate(1,paidDate);
			ps.setInt(2,companyId); 
			ps.setInt(3, invoiceNo);
			ps.executeUpdate();
		}

	}

	//Deleting an invoice
	public void deleteInvoice(int invoiceNo, int companyId) throws ClassNotFoundException, SQLException {
		try (Connection conn = dbConnection.getConnection();

				PreparedStatement ps = conn.prepareStatement(DELETE_INVOICE)) {
			ps.setInt(2,companyId); 
			ps.setInt(1, invoiceNo);
			ps.executeUpdate();
		}
		
	}
	
	//checking for an item applied to invoice
	public boolean isItemAppliedToInvoice(int id) throws ClassNotFoundException, SQLException {
		try (Connection conn = dbConnection.getConnection();

				PreparedStatement ps = conn.prepareStatement(IS_ITEM_APPLIED_TO_INVOICE)) {
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt("ctr") > 0;
			}
		}

		return false;
	}
	//checking for an customer applied to invoice
	public boolean isCustomerAppliedToInvoice(int id) throws ClassNotFoundException, SQLException {
		try(Connection conn = dbConnection.getConnection();
				PreparedStatement ps=conn.prepareStatement(IS_CUSTOMER_APPLIED_TO_INVOICE)){
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery(); 
			while(rs.next()) {
				return rs.getInt("ctr")>0;
			}
			return false;
		}
		
	}

	//Updating invoice table
	public void UpdateInvoice(Invoice invoice) throws ClassNotFoundException, SQLException {
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(UPDATE_INVOICE)) {
			System.out.println(invoice.getCompanyId()+" "+invoice.getInvoiceNo());
			int customerId = invoice.getCustomerId();
			int companyId = invoice.getCompanyId();
			Date date = invoice.getDate();
			Date dueDate = invoice.getDueDate();
			ps.setDate(1, date);
			ps.setDate(2, dueDate);
			ps.setInt(3, customerId);
			ps.setDouble(4, invoice.getDiscount());
			ps.setDouble(5, invoice.getDiscountAmount());
			ps.setDouble(6, invoice.getAmount());
			ps.setDouble(7, invoice.getTotalAmount());
			ps.setInt(8,invoice.getInvoiceNo());
			ps.setInt(9, companyId);
			ps.executeUpdate();

		}
		
		
	}

	//updating invoice
	public void UpdateInvoiceItems(List<InvoiceItems> invoiceitems, int companyId, int invoiceNo) throws ClassNotFoundException, SQLException {
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(DELETE_INVOICE_ITEMS)) {
			int invoiceId = getInvoiceId(companyId,invoiceNo);
			ps.setInt(1, invoiceId);
			ps.executeUpdate();
			insertInvoiceItems(invoiceitems, companyId,invoiceNo);
		}
		
	}

}
