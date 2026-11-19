package com.desk2dine.entity;

public class CanteenStaff extends User {
	public CanteenStaff() {
		super();
	}

	public CanteenStaff(int id, String name, String password, String gender) {
		super(id, name, password, gender);
	}

	@Override
	public String toString() {
		return "Id: " + getId() 
		+ ", Name:" + getName() 
		+ ", Gender:" + getGender();
	}
}
