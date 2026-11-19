package com.desk2dine.service;

import com.desk2dine.entity.Faculty;
import com.desk2dine.repository.FacultyRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacultyService {
	
	@Autowired 
	private FacultyRepository repository;
	
	private boolean validateFaculty(Faculty faculty) {
		if(faculty == null) {
			return false;
		}
		else if(faculty.getId()<=0) {
			return false;
		}
		else if(faculty.getName() == null || faculty.getName().isBlank()) {
			return false;
		}
		else if(faculty.getPassword() == null && faculty.getPassword().length() < 8) {
			return false;
		}
		else if(faculty.getGender() == null && faculty.getGender().isBlank()) {
			return false;
		}
		return true;
	}
	
	public boolean registerFaculty(Faculty faculty) {
		Faculty existing = repository.findFacultyById(faculty.getId());
		if(existing != null) {
			return false;
		}
		else if(validateFaculty(faculty)){
			return repository.insertFaculty(faculty) == 1;
			
		}
		return false;

	}
	
	public boolean updateFaculty(Faculty faculty) {
		Faculty existing = repository.findFacultyById(faculty.getId());
		if(existing != null) {
			if(validateFaculty(faculty)) {
				return repository.updateFaculty(faculty) == 1;
			}
		}
		return false;
	}
	
	public boolean removeFaculty(int id) {
		Faculty faculty = repository.findFacultyById(id);
		if(faculty!= null) {
			return repository.deleteFaculty(id) == 1;
		}
		return false;
	}
	
	public Faculty getFacultyProfile(int id) {
		return repository.findFacultyById(id);
	}
	
	public List<Faculty> getAllFaculty() {
		return repository.findAllFaculty() ;
	}
	
	public Faculty loginFaculty(int id , String password) {
		return repository.validateFaculty(id, password);
		
	}
	
}
