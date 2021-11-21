package com.invoice;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import com.database.DatabaseConnection;

public class InvoiceDAO {
	private DatabaseConnection dbConnection;

	private static final String CREATE_INVOICE_TABLE = "CREATE TABLE IF NOT EXISTS invoice (id  int(10) NOT NULL AUTO_INCREMENT, date DATE NOT NULL, dueDate DATE NOT NULL,paidDate DATE,customerId int(10),invoiceNo int(10) NOT NULL,companyId int(10) NOT NULL,discount DOUBLE(40,2) NOT NULL,	PRIMARY KEY (id), FOREIGN KEY(companyId) REFERENCES users(id) ON DELETE CASCADE);";
	private static final String CREATE_INVOICE_ITEMS_TABLE = "Create TABLE IF NOT EXISTS invoiceitems (id int(10) NOT NULL AUTO_INCREMENT,invoiceNo int(10) NOT NULL,itemId int(10) NOT NULL,quantity DOUBLE(40,10) NOT NULL,price DOUBLE(40,10) NOT NULL,companyId int(10) NOT NULL,PRIMARY KEY (id), FOREIGN KEY(companyId) REFERENCES users(id) ON DELETE CASCADE);";

	private static final String INSERT_INTO_INVOICE_TABLE = "insert into invoice (date,dueDate,customerId,companyId,invoiceNo,discount) values (?,?,?,?,?,?);";
	private static final String INSERT_INTO_INVOICE_ITEMS_TABLE = "insert into invoiceitems (invoiceNo,itemId,quantity,price,companyId) values (?,?,?,?,?);";

	private static final String GET_INVOICE_NUMBER = "select max(invoiceNo) as ctr from invoice where companyId=?";
	private static final String GET_INVOICES = "select * from invoice where companyId=? order by invoiceNo desc";
	private static final String GET_INVOICE_ITEMS = "select * from invoiceitems where companyId=? and invoiceNo=?";
	private static final String GET_INVOICE_BY_ID = "select * from invoice where companyId=? and invoiceNo=?";
	private static final String IS_ITEM_APPLIED_TO_INVOICE = "select count(*) as ctr from invoiceitems where itemId=? and companyId=?";

	private static final String MAKE_PAYMENT = "update invoice set paidDate=? where companyId=? and invoiceNo=?";

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

	public void createInvoice(Invoice invoice) throws ClassNotFoundException, SQLException {

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
			ps.executeUpdate();

		}
	}

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

	public void insertInvoiceItems(List<InvoiceItems> items, int companyId, int invoiceNo)
			throws ClassNotFoundException, SQLException {
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(INSERT_INTO_INVOICE_ITEMS_TABLE)) {
			for (int i = 0; i < items.size(); i++) {
				ps.setInt(1, invoiceNo);
				ps.setInt(2, items.get(i).getItemId());
				ps.setDouble(3, items.get(i).getQuantity());
				ps.setDouble(4, items.get(i).getPrice());
				ps.setInt(5, companyId);
				ps.executeUpdate();
			}

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
				invoices.add(new Invoice(customerId, date, dueDate, paidDate, companyId, invoiceNo, discount));
			}
			return invoices;

		}
	}

	public List<InvoiceItems> getInvoiceItems(int companyId, int invoiceId)
			throws SQLException, ClassNotFoundException {
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(GET_INVOICE_ITEMS)) {
			List<InvoiceItems> invoiceitems = new ArrayList<>();
			ps.setInt(1, companyId);
			ps.setInt(2, invoiceId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int itemId = rs.getInt("itemId");
				double quantity = rs.getDouble("quantity");
				double price = rs.getDouble("price");
				invoiceitems.add(new InvoiceItems(invoiceId, itemId, quantity, price));
			}
			return invoiceitems;

		}
	}

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

				return new Invoice(customerId, date, dueDate, paidDate, companyId, invoiceId, discount);

			}
			return null;

		}
	}

	public boolean isItemAppliedToInvoice(int id,int companyId) throws ClassNotFoundException, SQLException {
		try (Connection conn = dbConnection.getConnection();

				PreparedStatement ps = conn.prepareStatement(IS_ITEM_APPLIED_TO_INVOICE)) {
			ps.setInt(1, id);
			ps.setInt(2, companyId);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt("ctr") > 0;
			}
		}

		return false;
	}

	public void pay(int companyId, int invoiceNo, Date paidDate) throws ClassNotFoundException, SQLException {
		try (Connection conn = dbConnection.getConnection();

				PreparedStatement ps = conn.prepareStatement(MAKE_PAYMENT)) {
			ps.setDate(1,paidDate);
			ps.setInt(2,companyId); 
			ps.setInt(3, invoiceNo);
			ps.executeUpdate();
		}

	}
}
