package com.inn.restaurant.restImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.restaurant.constents.RestaurantConstents;
import com.inn.restaurant.pojo.Category;
import com.inn.restaurant.rest.CategoryRest;
import com.inn.restaurant.service.CategoryService;
import com.inn.restaurant.utils.RestaurantUtils;

@RestController
public class CategoryRestImpl implements CategoryRest {
	
	@Autowired
	CategoryService categoryservice;

	@Override
	public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
		try 
		{
			return CategoryService.AdminCategory(requestMap);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return RestaurantUtils.getResponseEntity(RestaurantConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
	try {
		
		return categoryservice.getAllCategory(filterValue);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> upadetcategory(Map<String, String> requestMap) {
		try {
			return categoryservice.updateCategory(requestMap);
			
		} catch (Exception e) {
			
		}
		return RestaurantUtils.getResponseEntity(RestaurantConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
