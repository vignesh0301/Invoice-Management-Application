package com.invoice;

import java.sql.Date;

public class Invoice {

	private int id, customerId;
	private Date date, dueDate,paidDate;
	private int companyId;
	private int invoiceNo;
	private double discount; 
	

	
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

	
	public Invoice(int customerId, Date date, Date dueDate,Date paidDate, int companyId, int invoiceNo,double discount) {
		this.customerId = customerId;
		this.date = date;
		this.companyId = companyId;
		this.dueDate = dueDate;
		this.paidDate=paidDate;
		this.invoiceNo = invoiceNo;
		this.discount=discount; 
	}

}
