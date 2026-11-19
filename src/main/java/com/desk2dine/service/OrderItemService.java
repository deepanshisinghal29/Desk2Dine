package com.desk2dine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desk2dine.entity.OrderItem;
import com.desk2dine.entity.OrderStatus;
import com.desk2dine.entity.MenuItem;
import com.desk2dine.entity.Order;
import com.desk2dine.repository.MenuItemRepository;
import com.desk2dine.repository.OrderItemRepository;
import com.desk2dine.repository.OrderRepository;

@Service
public class OrderItemService {

	@Autowired
	private OrderItemRepository repository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderService orderService;
	@Autowired
	private MenuItemService menuItemService;
	
	private boolean validateOrderItem(OrderItem item) {
		if(item == null) {
			return false;
		}
		else if(item.getMenuItem()==null) {
			return false;
		}
		else if(item.getPrice()<=0) {
			return false;
		}
		else if(item.getQuantity()<=0) {
			return false;
		}
		else if(item.getOrder()==null) {
			return false;
		}
		return true;
	}
	
	public boolean insert(OrderItem item) {
		if(validateOrderItem(item)) {
			if(item.getMenuItem().getQuantityAvailable() >= item.getQuantity()) {
				if(repository.insert(item) == 1) {
					double bill = orderService.calculateBill(item.getOrder().getId());
					orderService.updateBill(item.getOrder().getId(), bill);
					//menuItemService.updateQuantity(item.getMenuItem().getId(), item.getQuantity());
					return true;
				}
			}
		}
		return false;
	}
	public boolean addToCart(OrderItem item, int facultyId) {
		boolean cartCreated;
		Order order= orderService.createCart(facultyId,item);
		if(order == null) {
			return false;
		}
		else {
			cartCreated = true;
		}
		OrderItem exists = new OrderItem();
		if(cartCreated) {
			exists = repository.findOrderAndMenu(order.getId(), item.getMenuItem().getId());
		}
		 
		if(exists!=null) {
			return updateOrderItem(item);
		}
		return insert(item);
	}
	
	public boolean updateOrderItem(OrderItem item) {
		OrderItem exists = repository.findOrderAndMenu(item.getOrder().getId(), item.getMenuItem().getId());
		if(exists!=null) {
			if(validateOrderItem(item)) {
				int quantityDiffrence = 0;
				if(item.getOrder().getOrderStatus()==OrderStatus.PLACED) {
					quantityDiffrence = item.getQuantity() - exists.getQuantity();
				}
				if(item.getQuantity()==0) {
					return removeItem(item.getId(),item.getOrder().getId());
				}
				if(exists.getQuantity()>0 && item.getMenuItem().getQuantityAvailable() >= quantityDiffrence) {
					return updateQuantity(item.getOrder().getId(), item.getMenuItem().getId(), item.getQuantity(), quantityDiffrence );
				}
			}		
		}
		
		return false;
	}
	
	public boolean updateQuantity(int orderId, int menuId, int quantity, int quantityDifference) {
			if(findOrderAndMenu(orderId, menuId) != null) {
				if(repository.updateQuantity(orderId, menuId, quantity) == 1) {
					menuItemService.updateQuantity(menuId, quantityDifference);
					double bill = orderService.calculateBill(orderId);
					orderService.updateBill(orderId, bill);
					return true;
				}
			}
		return false;
	}
	
	public boolean removeItem(int id,int orderId) {
		OrderItem exists = repository.find(id);
		if(exists!=null) {
			if(repository.delete(id) == 1) {
				menuItemService.updateQuantity(exists.getMenuItem().getId(), -exists.getQuantity());
				double bill = orderService.calculateBill(orderId);
				orderService.updateBill(orderId, bill);
				return true;
			}
		}
		return false;
	}
	
	public boolean removeOrderItems(int orderId) {
		Order order = orderRepository.find(orderId);
		if(order!=null) {
			return repository.deleteOrderItems(orderId) >= 1;
		}
		return false;
	}
	
	public OrderItem find(int id) {
		return repository.find(id);
	}
	
	
	public OrderItem findOrderAndMenu(int orderId , int menuId) {
		return repository.findOrderAndMenu(orderId, menuId);
	}
	
	public List<OrderItem> findByOrder(int orderId) {
		return repository.findByOrder(orderId);
	}
	
	public List<OrderItem> findAll() {
		return repository.findAll();
	}
}
