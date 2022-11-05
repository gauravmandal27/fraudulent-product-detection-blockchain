package com.src.model;

public class Retail {
	private String name;
	private String email;
	private String location;
	private String password;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Retail(String name, String email, String location, String password) {
		super();
		this.name = name;
		this.email = email;
		this.location = location;
		this.password = password;
	}
	
}
