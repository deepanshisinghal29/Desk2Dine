package com.desk2dine.entity;

public class MenuItem {
	private int id;
	private String itemName;
	private double price;
	private int quantityAvailable;
	private String category;
	
	public MenuItem() {
		super();
	}

	public MenuItem(int id, String itemName, double price, int quantityAvailable, String category) {
		super();
		this.id = id;
		this.itemName = itemName;
		this.price = price;
		this.quantityAvailable = quantityAvailable;
		this.category = category;
	}

	@Override
	public String toString() {
		return "Item:" + itemName 
				+ ", Price:" + price 
				+ ", Quantity Available:" + quantityAvailable
				+ ", Category:" + category ;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String item) {
		this.itemName = item;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantityAvailable() {
		return quantityAvailable;
	}

	public void setQuantityAvailable(int quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
