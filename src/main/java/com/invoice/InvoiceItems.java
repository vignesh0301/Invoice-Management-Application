package com.invoice;

public class InvoiceItems {


	int id,invoiceNo,itemId; 
	double quantity,price;

	public int getId() {
		return id;
	}

	public int getInvoiceNo() {
		return invoiceNo;
	}

	public int getItemId() {
		return itemId;
	}

	public double getQuantity() {
		return quantity;
	}

	public double getPrice() {
		return price;
	}

	public InvoiceItems(int invoiceNo, int itemId, double quantity, double price) {
		this.invoiceNo = invoiceNo;
		this.itemId = itemId;
		this.quantity = quantity;
		this.price = price;
	} 
	

	public InvoiceItems(int itemId, double quantity, double price) {
		this.itemId = itemId;
		this.quantity = quantity;
		this.price = price;
	} 

}
