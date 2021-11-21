package com.item;

public class Item {
	
	
	private int id; 
	private String name,type;
	private double costPrice,sellingPrice; 
	private String description;
	private int companyId;
	
	private double price,quantity;
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public double getCostPrice() {
		return costPrice;
	}
	public double getSellingPrice() {
		return sellingPrice;
	}
	public String getDescription() {
		return description;
	}
	public int  getCompanyId() {
		return companyId;
	}
	public Item(String name,  String type, double costPrice, double sellingPrice, String description,int companyId) {
		this.name = name;
		this.type = type;
		this.costPrice = costPrice;
		this.sellingPrice = sellingPrice;
		this.description = description;
		this.companyId=companyId;
	}
	public Item(int id, String name, String type,double costPrice, double sellingPrice, String description,
			int companyId) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.costPrice = costPrice;
		this.sellingPrice = sellingPrice;
		this.description = description;
		this.companyId = companyId;
	}
	
	public Item(String name,double quantity,double price) {
		this.name=name; 
		this.quantity=quantity; 
		this.price=price;
	}
	public double getPrice() {
		return price;
	}
	public double getQuantity() {
		return quantity;
	}
	
	
	

}
