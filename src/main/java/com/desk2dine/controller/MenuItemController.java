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

import com.desk2dine.entity.MenuItem;
import com.desk2dine.service.MenuItemService;

@Controller
@RequestMapping("/menu")
public class MenuItemController {
	
	@Autowired
	private MenuItemService service;
	
	@GetMapping("/add")
	public String addItem(Model model) {
		model.addAttribute("mode", "/menu/add");
		model.addAttribute("menuItem", new MenuItem());
		return "item";
	}
	
	@PostMapping("/add")
	public String addItem(@ModelAttribute MenuItem item, Model model) {
		boolean added = service.addItem(item);
		if(added) {
			model.addAttribute("menuItem", item);
			return "itemDetails";
		}
		model.addAttribute("message", "Item cannot be added. Try again.");
		model.addAttribute("mode", "/menu/add");
		return "item";
	}
	
	@GetMapping("/update")
	public String updateItem(@RequestParam int id, Model model) {
		MenuItem item = service.searchItemById(id);
		model.addAttribute("mode", "/menu/update");
		if(item==null) {
			model.addAttribute("message","Item not found : Id may not be correct");
			return "item";
		}
		model.addAttribute("menuItem", item);
		return "item";
	}
	
	@PostMapping("/update")
	public String updateItem(@ModelAttribute MenuItem item, Model model) {
		boolean updated = service.updateItem(item);
		if(updated) {
			System.out.println(item);
			model.addAttribute("menuItem", item);
			return "itemDetails";
		}
		model.addAttribute("message", "Item cannot be updated. Try again.");
		model.addAttribute("mode", "/menu/update");
		return "item";
	}
	
	@PostMapping("/delete")
	public String removeItem(@RequestParam int id, Model model) {
		boolean deleted = service.removeItem(id);
		if(deleted) {
			model.addAttribute("message", "Deletion of this item is successful.");
			return "itemDetails";
		}
		model.addAttribute("message", "Deletion of this item is unsuccessful.");
		return "itemDetails";
	}
	
	@GetMapping("/item")
	public String searchItemById(@RequestParam int id, Model model) {
		MenuItem item = service.searchItemById(id);
		if(item!=null) {
			model.addAttribute("menuItem", item);
			return "itemDetails";
		}
		model.addAttribute("message", "Item not found");
		return "itemDetails";
	}
	
	@GetMapping("/item/name")
	public String searchItemByName(@RequestParam String name, Model model) {
		MenuItem item = service.searchItemByName(name);
		if(item!=null) {
			model.addAttribute("menuItem", item);
			return "itemDetails";
		}
		model.addAttribute("message", "Item not found");
		return "itemDetails";
	}
	
	@GetMapping("/all")
	public String searchAllItem(Model model) {
		List<MenuItem> list = service.searchAllItem();
		if(list==null || list.isEmpty()) {
			model.addAttribute("message", "Items are not found");
			return "menu";
		}
		model.addAttribute("list", list);
		return "menu";
	}
	
}
