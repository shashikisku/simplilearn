package com.simplilearn.phase2.dto;

public class User {
	private String firstname;
	private String lastname;
	private String address;
	private String username;
	private String password;

	public User() {
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public User(String firstname, String lastname, String address, String username, String password) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.address = address;
		this.username = username;
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
