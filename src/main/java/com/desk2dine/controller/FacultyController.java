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

import com.desk2dine.entity.Faculty;
import com.desk2dine.service.FacultyService;

@Controller
@RequestMapping("/faculty")
public class FacultyController {
	
	@Autowired
	private FacultyService service; 
	
	@GetMapping("/register")
	public String showRegisterForm(Model model){
		model.addAttribute("faculty" , new Faculty());
		return "register";
	}
	
	@PostMapping("/register")
	public String registerFaculty(@ModelAttribute Faculty faculty, Model model) {
		boolean registered = service.registerFaculty(faculty);
		if(registered) {
			return "redirect:/faculty/profile?id=" + faculty.getId();
		} 
		model.addAttribute("message", "Faculty registration failed\nTry again.");
		return "register";
	}
	
	@GetMapping("/update")
	public String showUpdateFacultyPage(@RequestParam int id, Model model){
		Faculty faculty = service.getFacultyProfile(id);
		if(faculty == null) {
			model.addAttribute("message", "Faculty not found");
			return "update";
		}
		model.addAttribute("faculty", faculty);
		return "update";
	}
	@PostMapping("/update")
	public String updateFaculty(@ModelAttribute Faculty faculty , Model model) {
		boolean updated =  service.updateFaculty(faculty);
		if(updated){
			return "redirect:/faculty/profile?id=" + faculty.getId();
		}
		model.addAttribute("message", "Faculty updation failed\nTry again.");
		return "update";
	}
	
	@GetMapping("/delete")
	public String showDeleteFacultyPage(@RequestParam int id, Model model) {
		Faculty faculty = service.getFacultyProfile(id);
		if(faculty == null) {
			model.addAttribute("message", "Faculty not found");
			return "delete";
		}
		return "delete";
	}
	
	@PostMapping("/delete")
	public String removeFaculty(@RequestParam int id, Model model) {
		boolean removed = service.removeFaculty(id);
		if(removed) {
			return "redirect:/faculty/list";
		}
		model.addAttribute("message", "Faculty cannot be deleted");
		return "delete";
	}
	
	@GetMapping("/profile")
	public String showFindFacultyPage(@RequestParam int id,Model model) {
		Faculty faculty = service.getFacultyProfile(id);
		if(faculty!=null) {
			model.addAttribute("faculty", faculty);
			return "facultyProfile";
		}
		model.addAttribute("message" ,"Faculty not found please register first ->");
		return "facultyProfile";
	}
	
	@GetMapping("/list")
	public String findAllFaculty(Model model) {
		List<Faculty> list = service.getAllFaculty();
		if(list == null || list.isEmpty()) {
			model.addAttribute("message", "Faculties not found\n Try again");
			return "facultyList";
		}
		model.addAttribute("facultyList", list);
		return "facultyList";
	}
	
	@GetMapping("/login")
	public String showLoginPage(Model model) {
		return "login";
	}
	
	@PostMapping("/login")
	public String loginFaculty(@RequestParam int id,@RequestParam String password, Model model) {
		Faculty faculty = service.loginFaculty(id, password);
		if(faculty!=null) {
			model.addAttribute("faculty", faculty);
			return "redirect:/faculty/profile?id=" + id;
		}
		model.addAttribute("message","Faculty not found");
		return "login";
	}
	
}
