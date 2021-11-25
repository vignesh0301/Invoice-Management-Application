package com.customer;

//Bean class for customer
public class Customer {
	
	private int id;
	private String name,email,address,phone;
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
	public int getCompanyId() {
		return companyId;
	}
	
	
	public Customer(String name, String email, String address, int companyId,String phone) {
		this.name = name;
		this.email = email;
		this.address = address;
		this.phone=phone;
		this.companyId = companyId;
	}
	public Customer(int id, String name, String email, String address,  int companyId,String phone) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.companyId = companyId;
	}
	public Customer(int id,String name, int companyId) {
		this.id=id;
		this.name = name;
		this.companyId = companyId;
	}
	
	

	
	

}
