package com.desk2dine.entity;

public class Faculty extends User {

	public Faculty() {
		super();
	}

	public Faculty(int id, String name,String password,  String gender) {
		super(id, name, password, gender);
	}

	@Override
	public String toString() {
		return "Name:" + getName() 
		+ ", Id:" + getId() 
		+ ", Gender:" + getGender();
	}
	
}
