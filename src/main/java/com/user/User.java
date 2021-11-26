package com.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//Bean class for user
public class User {
	private int id; 
	private String username,password,email,company;
	
	
	public User() {
	}
	public User(int id, String username,  String email,String password,String company) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.company=company;
	}
	public int getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getEmail() {
		return email;
	}
	public String getCompany() {
		return company;
	}
	public User(String username,String email,String company, String password) {
		this.username = username;
		this.password = encrypt(password);
		this.email = email;
		this.company=company;
	} 
	public static String encrypt(String password) {
		try   
        {  
            MessageDigest m = MessageDigest.getInstance("MD5");  
              
            m.update(password.getBytes());   
            byte[] bytes = m.digest();  
            StringBuilder s = new StringBuilder();  
            for(int i=0; i< bytes.length ;i++)  
            {  
            	s.append(Integer.toString(bytes[i]).substring(1)); 
            }  
            return s.toString();  
        }   
        catch (NoSuchAlgorithmException e)   
        {  
            e.printStackTrace();  
            
        }  
		return password;
		
	}
}
