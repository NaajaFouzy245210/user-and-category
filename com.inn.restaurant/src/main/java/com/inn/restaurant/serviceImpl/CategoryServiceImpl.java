package com.inn.restaurant.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.inn.restaurant.JWT.JwtFilter;
import com.inn.restaurant.constents.RestaurantConstents;
import com.inn.restaurant.dao.CategoryDao;
import com.inn.restaurant.pojo.Category;
import com.inn.restaurant.service.CategoryService;
import com.inn.restaurant.utils.RestaurantUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	CategoryDao categorydao;
	
	@Autowired
	JwtFilter jwtfilter;
	
	@Override
	public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
		try {
			
			if(jwtfilter.isAdmin()) {
				
				if(validateCategoryMap(requestMap,false)) {
					categorydao.save(getCategoryFromMap(requestMap, false));
					
					return RestaurantUtils.getResponseEntity("category added succesfully", HttpStatus.OK);
					
				}
				
			}
			else {
				return RestaurantUtils.getResponseEntity(RestaurantConstents.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RestaurantUtils.getResponseEntity(RestaurantConstents.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

	private boolean validateCategoryMap (Map<String, String> requestMap, boolean validateId) {
		
		if(requestMap.containsKey("name")) {
			if(requestMap.containsKey("id") && validateId) {
				return true;
			}
			else if (!validateId){
				
				return true;
			}
			
		}
		return false;
	}
	
	private Category getCategoryFromMap( Map<String, String> requestMap, Boolean isAdd) {
		
		Category category = new Category();
		if(isAdd) {
			category.setId(Integer.parseInt(requestMap.get("id")));
		}
		category.setName(requestMap.get("name"));
		return category;
		
		
	}

	@Override
	public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
		try {
			if(!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")) {
				log.info("inside if");
				
				return new ResponseEntity<List<Category>>(categorydao.getAllCategory(),HttpStatus.OK);
			}
			return new ResponseEntity<List<Category>>(categorydao.findAll(),HttpStatus.OK);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
		try {
			if(jwtfilter.isAdmin()) 
			{
				if(validateCategoryMap(requestMap, true)) 
				{
					Optional optional=categorydao.findById(Integer.parseInt(requestMap.get("id")));
					if(!optional.isEmpty()) 
					{
						categorydao.save(getCategoryFromMap(requestMap, true));
						return RestaurantUtils.getResponseEntity("category updates succesfully", HttpStatus.OK);
					
					}
					else 
					{
						return  RestaurantUtils.getResponseEntity("category id does not exits",HttpStatus.OK);
					}
				}
				return RestaurantUtils.getResponseEntity(RestaurantConstents.INVALID_DATA,HttpStatus.BAD_REQUEST);
			}
			else{
				
				return RestaurantUtils.getResponseEntity(RestaurantConstents.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RestaurantUtils.getResponseEntity(RestaurantConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
