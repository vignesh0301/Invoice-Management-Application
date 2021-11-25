package com.invoice;

import java.sql.Date;

//Bean class for invoice
public class Invoice {

	private int id, customerId;
	private Date date, dueDate,paidDate;
	private int companyId;
	private int invoiceNo;
	private double discount,discountAmount,Amount,TotalAmount;
	private String customerName;

	
	public String getCustomerName() {
		return customerName;
	}
	public double getDiscountAmount() {
		return discountAmount;
	}
	public double getAmount() {
		return Amount;
	}
	public double getTotalAmount() {
		return TotalAmount;
	}
	public int getId() {
		return id;
	}
	public int getCustomerId() {
		return customerId;
	}
	public Date getDate() {
		return date;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public int getCompanyId() {
		return companyId;
	}
	public int getInvoiceNo() {
		return invoiceNo;
	}
	public double getDiscount() {
		return discount;
	}
	public Date getPaidDate() {
		return paidDate;
	}
	public Invoice(int customerId, Date date, Date dueDate, Date paidDate, int companyId, int invoiceNo,
			double discount, double discountAmount, double amount, double totalAmount,String customerName) {
		this.customerId = customerId;
		this.date = date;
		this.dueDate = dueDate;
		this.paidDate = paidDate;
		this.companyId = companyId;
		this.invoiceNo = invoiceNo;
		this.discount = discount;
		this.discountAmount = discountAmount;
		this.Amount = amount;
		this.TotalAmount = totalAmount;
		this.customerName=customerName;
	}
	public Invoice(int customerId, Date date, Date dueDate, Date paidDate, int companyId, int invoiceNo,
			double discount, double discountAmount, double amount, double totalAmount) {
		this.customerId = customerId;
		this.date = date;
		this.dueDate = dueDate;
		this.paidDate = paidDate;
		this.companyId = companyId;
		this.invoiceNo = invoiceNo;
		this.discount = discount;
		this.discountAmount = discountAmount;
		this.Amount = amount;
		this.TotalAmount = totalAmount;
	}
	

}
