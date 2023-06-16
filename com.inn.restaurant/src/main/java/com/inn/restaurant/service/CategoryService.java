package com.inn.restaurant.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.inn.restaurant.pojo.Category;

public interface CategoryService {
	
	ResponseEntity<String> addNewCategory(Map<String, String> requestMap);

	static ResponseEntity<String> AdminCategory(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		return null;
	}

	ResponseEntity<List<Category>> getAllCategory(String filterValue);
	
	ResponseEntity<String> updateCategory(Map<String, String> requestMap);
}
