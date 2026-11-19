package com.desk2dine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desk2dine.entity.MenuItem;
import com.desk2dine.repository.MenuItemRepository;

@Service
public class MenuItemService {
	
	@Autowired
	private MenuItemRepository repository;
	
	public boolean validateMenuItem(MenuItem item){
		if(item.getItemName() == null || item.getItemName().isBlank()) {
			return false;
		}
		else if(item.getCategory() == null || item.getCategory().isBlank()) {
			return false;
		}
		else if(item.getQuantityAvailable() <= 0) {
			return false;
		}
		else if(item.getPrice() <= 0.0) {
			return false;
		}
		return true;
	}
	
	public boolean addItem(MenuItem item) {
		MenuItem existed = repository.findById(item.getId());
		MenuItem exists = repository.findByName(item.getItemName());
		
		if(existed != null) {
			return false;
		}
		else if(exists != null) {
			return false;
		}
		else if (!validateMenuItem(item)) {
			return false;
		}
		return repository.insert(item) == 1;
	}
	
	public boolean updateItem(MenuItem item) {
		MenuItem existed = repository.findById(item.getId());
		MenuItem exists = repository.findByName(item.getItemName());
		if(existed == null) {
			return false;
		}
		else if(exists == null) {
			return false;
		}
		else if (!validateMenuItem(item)) {
			return false;
		}
		return repository.update(item) == 1;
	}
	
	public boolean updateQuantity(int id, int quantityDifference) {
		MenuItem existed = repository.findById(id);
		if(existed == null) {
			return false;
		}
		int quantityAvailable = existed.getQuantityAvailable() - quantityDifference;
		if(quantityAvailable<0) {
			return false;
		}
		return repository.updateQuantity(id,quantityAvailable) == 1;
	}
	
	public boolean removeItem(int id) {
		MenuItem existed = repository.findById(id);
		if(id<=0) {
			return false;
		}
		else if(existed == null) {
			return false;
		}
		return repository.delete(id) == 1;
	}
	
	public MenuItem searchItemById(int id) {
		return repository.findById(id);
	}
	
	public MenuItem searchItemByName(String name) {
		return repository.findByName(name);
	}
	
	public List<MenuItem> searchAllItem() {
		return repository.findAll();
	}
	
}
