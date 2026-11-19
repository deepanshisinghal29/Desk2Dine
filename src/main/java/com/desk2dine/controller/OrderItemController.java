package com.desk2dine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.desk2dine.service.OrderItemService;

@Controller
@RequestMapping("/cart")
public class OrderItemController {
	
	@Autowired
	OrderItemService service;
	
	@PostMapping("/add/item")
	public String addItem() {
		return "cart";
	}
	
	@PostMapping()
	public String updateOrder() {
		return "cart";
	}
	
	@PostMapping("/remove/item")
	public String removeItem(int id,int orderId) {
		return "cart";
	}
	
	@PostMapping("/remove/order/items")
	public String removeItemsOfOrder(int orderId) {
		return "faultyOrders";
	}
	
	@GetMapping("/order/items")
	public String SingleOrderItems() {
		return "cart";
	}
	
	@GetMapping("/all/items")
	public String allOrderItems() {
		return "";
	}
	
}
