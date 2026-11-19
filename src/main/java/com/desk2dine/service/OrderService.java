package com.desk2dine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desk2dine.entity.Faculty;
import com.desk2dine.entity.Order;
import com.desk2dine.entity.OrderItem;
import com.desk2dine.entity.OrderStatus;
import com.desk2dine.repository.FacultyRepository;
import com.desk2dine.repository.OrderItemRepository;
import com.desk2dine.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private FacultyRepository facultyRepository;
	
	private boolean validateOrder(Order order) {
		if(order.getId()<=0) {
			return false;
		}
		else if(order.getFaculty()==null) {
			return false;
		}
		else if(order.getLocation()==null || order.getLocation().isBlank()) {
			return false;
		}
		else if(order.getOrderBill()<0.0) {
			return false;
		}
		else if(order.getOrderDate()==null) {
			return false;
		}else if(order.getEstimatedDeliveryTime()==null || order.getEstimatedDeliveryTime().isBefore(order.getOrderDate())) {
			return false;
		}
		else if(order.getOrderStatus()==null) {
			return false;
		}
		return true;
	}
	
	private boolean validateStatus(OrderStatus prev, OrderStatus next) {
		if(prev==OrderStatus.CART && next==OrderStatus.PLACED) {
			return true;
		}
		else if(prev==OrderStatus.PLACED && next == OrderStatus.DELIVERED) {
			return true;
		}
		else if(prev==OrderStatus.DELIVERED && next == OrderStatus.RECEIVED) {
			return true;
		}
		else if(prev==OrderStatus.RECEIVED && next == OrderStatus.BILLED) {
			return true;
		}
		else if(prev==OrderStatus.BILLED && next == OrderStatus.PAID) {
			return true;
		}
		else if(prev==OrderStatus.PAID && next == OrderStatus.COMPLETED) {
			return true;
		}
		return false;
	}
	
	public Order findCart(int facultyId) {
		return repository.findCart(facultyId);
	}
	
	public Order createCart(int facultyId,OrderItem item) {
		Faculty faculty = facultyRepository.findFacultyById(facultyId);
		Order order = repository.findCart(facultyId);
		if(order != null) {
			item.setOrder(order);
			return order;
		}
		order = new Order();
		order.setOrderDate();
		order.setEstimatedDeliveryTime();
		order.setFaculty(faculty);
		order.setOrderStatus(OrderStatus.CART);
		item.setOrder(order);
		if(repository.insert(order) == 1) {
			return order;
		}
		return null;
	}
	
	public double calculateBill(int id) {
		List<OrderItem> list = orderItemRepository.findByOrder(id);
		double totalBill = 0;
		for(OrderItem item: list) {
			totalBill = totalBill + item.getAmount();
		}
		return totalBill;
	}
	
	public boolean placeOrder(Order order) {
		Order exists = repository.find(order.getId());
		if(exists != null) {
			order.setOrderDate();
			order.setEstimatedDeliveryTime();
			if(validateOrder(order)) {
				if(exists.getOrderStatus().equals(OrderStatus.CART) && order.getOrderStatus().equals(OrderStatus.PLACED)) {
					return repository.update(order) == 1;
				}
			}
		}
		return false;
	}
	
	public boolean updateBill(int id, double bill) {
		Order exists = repository.find(id);
		if(bill <= 0) {
			return false;
		}
		else if(id<0) {
			return false;
		}
		else if(exists == null) {
			return false;
		}
		return repository.updateBill(id, bill) == 1;
	}

	public boolean updateOrder(Order order) {
		Order exists = repository.find(order.getId());
		if(exists != null) {
			if(validateOrder(order)) {
				return repository.update(order) == 1;
			}
		}
		return false;
	}
	
	public boolean updateOrderStatus(int id , OrderStatus status) {
		if(id>0) {
			Order exists = repository.find(id);
			if(exists != null) {
				if(status != null) {
					if(validateStatus(exists.getOrderStatus(),status)) {
						return repository.updateStatus(id, status) == 1;
					}
				}
			}
		}
		return false;
	}
	
	public boolean removeOrder(int id) {
		if(id>0) {
			Order exists = repository.find(id);
			if(exists != null) {
				if(orderItemRepository.deleteOrderItems(id)>=1) {
					return repository.delete(id) == 1;
				}
			}
		}
	return false;
	}
	
	public Order getOrderDetails(int id) {
		return repository.find(id);
	}
	
	public List<Order> getFacultyOrders(int facultyId){
		return repository.findByFaculty(facultyId);
	}
	
	public List<Order> getAllOrders(){
		return repository.findAll();
	}
}
