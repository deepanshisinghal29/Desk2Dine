package com.desk2dine.entity;

public abstract class User {
	private int id; 
	private String name;
	private String password;
	private String gender;
	
	public User() {
		super();
	}
	
	public User(int id, String name, String password, String gender) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.gender = gender;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
}
