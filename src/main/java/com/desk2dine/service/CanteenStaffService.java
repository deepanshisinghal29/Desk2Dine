package com.desk2dine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desk2dine.entity.CanteenStaff;
import com.desk2dine.repository.CanteenStaffRepository;

@Service
public class CanteenStaffService {
	
	@Autowired 
	private CanteenStaffRepository repository;
	
	public boolean registerCanteenStaff(CanteenStaff staff) {
		boolean inserted = false;
		CanteenStaff existed = repository.findById(staff.getId());
		if(staff.getName()==null || staff.getName().isBlank()) {
			return inserted;
		}
		else if(staff.getPassword()==null ||staff.getPassword().length()<8) {
			return inserted;
		}
		else if(staff.getGender()==null || staff.getGender().isBlank()) {
			return inserted;
		}	
		else if(staff.getId()<=0) {
			return inserted;
		}
		else if(existed!=null) {
			return inserted;
		}
		else {
			return repository.insert(staff) == 1;
		}
	}
	
	public boolean updateCanteenStaff(CanteenStaff staff) {
		boolean updated = false;
		CanteenStaff existed = repository.findById(staff.getId());
		if(staff.getName()==null || staff.getName().isBlank()) {
			return updated;
		}
		else if(staff.getPassword()==null || staff.getPassword().length()<8) {
			return updated;
		}
		else if(staff.getGender()==null || staff.getGender().isBlank()) {
			return updated;
		}	
		else if(existed==null) {
			return updated;
		}
		else {
			return repository.update(staff) == 1;
		}
	}
	
	public boolean removeCanteenStaff(int id) {
		boolean deleted = false;
		if(id>0) {
			CanteenStaff existed = repository.findById(id);
			if(existed!=null) {		
				return repository.delete(id) == 1;
			}
		}
		return deleted;
	}
	
	public CanteenStaff searchById(int id) {
		return repository.findById(id);
	}
	
	public List<CanteenStaff> searchAll(){
		return repository.findAll();
	}
	
	public CanteenStaff login(int id, String password) {
		return repository.validate(id, password);
	}
}
