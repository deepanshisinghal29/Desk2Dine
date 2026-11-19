package com.desk2dine.entity;

import java.time.LocalDateTime;

public class Order {
	private int id;
	private Faculty faculty;
	private LocalDateTime orderDate;
	private String location;
	private double orderBill;
	private OrderStatus orderStatus;
	private LocalDateTime estimatedDeliveryTime;
	
	public Order() {
		super();
	}

	public Order(int id, Faculty faculty, String location, double orderBill) {
		super();
		this.id = id;
		this.faculty = faculty;
		orderDate = LocalDateTime.now();
		this.orderBill = orderBill;
		this.orderStatus = OrderStatus.PLACED;
		this.estimatedDeliveryTime = LocalDateTime.now().plusMinutes(25);
		this.location = location;
	}

	@Override
	public String toString() {
		return "[Order id=:" + id
				+ ", Faculty:" + faculty
				+ ", Order Date:" + orderDate
				+ ", Location:"+ location 
				+ ", Bill:" + orderBill
				+ ", Status:" + orderStatus
				+ ", Estimated Delivery Time:" + estimatedDeliveryTime 
				+ "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate() {
		this.orderDate = LocalDateTime.now();
	}
	
	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getOrderBill() {
		return orderBill;
	}

	public void setOrderBill(double orderBill) {
		this.orderBill = orderBill;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public LocalDateTime getEstimatedDeliveryTime() {
		return estimatedDeliveryTime;
	}

	public void setEstimatedDeliveryTime() {
		this.estimatedDeliveryTime = LocalDateTime.now().plusMinutes(25);
	}
	
	public void setEstimatedDeliveryTime(LocalDateTime estimatedDeliveryTime) {
		this.estimatedDeliveryTime = estimatedDeliveryTime;
	}
}
