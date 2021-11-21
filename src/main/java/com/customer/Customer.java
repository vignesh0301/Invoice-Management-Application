package com.customer;

public class Customer {
	
	private int id;
	private String name,email,address,remarks,phone;
	private int companyId;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getAddress() {
		return address;
	}
	public String getRemarks() {
		return remarks;
	}
	public int getCompanyId() {
		return companyId;
	}
	
	
	public Customer(String name, String email, String address, String remarks, int companyId,String phone) {
		this.name = name;
		this.email = email;
		this.address = address;
		this.remarks = remarks;
		this.phone=phone;
		this.companyId = companyId;
	}
	
	
	public Customer(int id,String name, int companyId) {
		this.id=id;
		this.name = name;
		this.companyId = companyId;
	}
	
	

	
	

}
