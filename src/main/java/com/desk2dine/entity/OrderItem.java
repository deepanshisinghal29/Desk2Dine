package com.desk2dine.entity;

public class OrderItem {
	private int id;
	private Order order;
	private MenuItem menuItem;
	private double price;
	private int quantity;
	
	public OrderItem() {
		super();
	}
	
	public OrderItem(int id, Order order, MenuItem menuItem, int quantity) {
		super();
		this.id = id;
		this.order = order;
		this.menuItem = menuItem;
		price = menuItem.getPrice();
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "[Order Item Id:" + id 
				+ "\n Order: " + order 
				+ "\n Item:" + menuItem 
				+ "\n Quantity:" + quantity 
				+ "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public double getAmount() {
		return price * quantity;
	}
}
