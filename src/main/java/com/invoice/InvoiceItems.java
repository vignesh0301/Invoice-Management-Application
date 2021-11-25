package com.invoice;

//Bean class for invoice items
public class InvoiceItems {


	int id,invoiceId,itemId; 
	double quantity,price;

	public int getId() {
		return id;
	}

	public int getInvoiceId() {
		return invoiceId;
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

	public InvoiceItems(int invoiceId, int itemId, double quantity, double price) {
		this.invoiceId = invoiceId;
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