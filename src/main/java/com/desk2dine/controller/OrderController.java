package com.desk2dine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.desk2dine.entity.Order;
import com.desk2dine.entity.OrderStatus;
import com.desk2dine.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService service;
	
	@PostMapping("/place")
	public String placeOrder(@ModelAttribute Order order, Model model) {
		boolean placed = service.placeOrder(order);
		if(placed) {
			return "redirect:/order/details?orderId=" + order.getId();
		}
		model.addAttribute("message", "Order not placed due to some issues please try again");
		return "order";
	}
	
	@GetMapping("/update")
	public String updateOrder(@RequestParam int orderId, Model model) {
		Order order = service.getOrderDetails(orderId);
		model.addAttribute("order", order);
		return "updateOrder";
	}
	
	@PostMapping("/update")
	public String updateOrder(@ModelAttribute Order order, Model model) {
		boolean update = service.updateOrder(order);
		if(!update) {
			model.addAttribute("message", "Order is not updated please try again");
			return "updateOrder";
		}
		return "redirect:/order/details?orderId=" + order.getId();
	}
	
	@GetMapping("/update/status")
	public String updateStatus(@RequestParam int orderId , Model model) {
		Order order = service.getOrderDetails(orderId);
		if(order==null) {
			model.addAttribute("message", "Order not found");
			return "updateStatus";
		}
		model.addAttribute("order", order);
		return "updateStatus";
	}
	
	@PostMapping("/update/status")
	public String updateStatus(@RequestParam int orderId, @RequestParam OrderStatus status, Model model) {
		boolean updated = service.updateOrderStatus(orderId, status) ;
		if(!updated) {
			model.addAttribute("message", "Status of order does not changed");
			return "updateStatus";
		}
		return "redirect:/order/details?orderId=" + orderId;
	}
	
	@GetMapping("/details")
	public String findOrder(@RequestParam int orderId, Model model) {
		Order order = service.getOrderDetails(orderId);
		if(order != null) {
			model.addAttribute("order", order);
			return "orderDetails";
		}
		model.addAttribute("message", "Order not found");
		return "orderDetails";
	}
	
	@GetMapping("/faculty")
	public String findFacultyOrders(@RequestParam int facultyId, Model model) {
		List<Order> list = service.getFacultyOrders(facultyId);
		if(list==null || list.isEmpty()) {
			model.addAttribute("message", "Orders not found");
			return "facultyOrders";
		}
		model.addAttribute("OrderList", list);
		return "facultyOrders";
	}
	
	@GetMapping("/all")
	public String findAllOrders(Model model) {
		List<Order> list = service.getAllOrders();
		if(list==null || list.isEmpty()) {
			model.addAttribute("message", "Orders not found");
			return "allOrders";
		}
		model.addAttribute("OrderList", list);
		return "allOrders";
	}
}
