package com.admin.inz.server.budgetrook.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.admin.inz.server.budgetrook.dto.CategoryDto;
import com.admin.inz.server.budgetrook.model.Category;
import com.admin.inz.server.budgetrook.repositories.CategoryRepository;
import com.admin.inz.server.budgetrook.response.MyResponseMessage;
import com.admin.inz.server.budgetrook.services.ConversionService;

@RestController
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private ConversionService conversionService;

	@RequestMapping(method = RequestMethod.GET, path = "/admin/categories")
	public ResponseEntity<List<CategoryDto>> getAllCategories() {
		List<CategoryDto> categoryDtos = new ArrayList<CategoryDto>();
		List<Category> categories = categoryRepo.findAll();
		for (Category category : categories) {
			categoryDtos.add(conversionService.categoryModelToDto(category));
		}
		return new ResponseEntity<List<CategoryDto>>(categoryDtos, HttpStatus.OK);	
	}

	@RequestMapping(method = RequestMethod.POST, path = "/category")
	public ResponseEntity<CategoryDto> postCategory(@RequestBody CategoryDto categoryDto) {
		Category category = conversionService.categoryDtoToModel(categoryDto);
		category = categoryRepo.save(category);
		categoryDto.setExternalId(category.getId());
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/category")
	private ResponseEntity<MyResponseMessage> deleteCategory(@RequestBody CategoryDto categoryDto) {
		Category category = categoryRepo.findById(categoryDto.getExternalId());
		if(category != null) {
			categoryRepo.delete(category);
			return new ResponseEntity<MyResponseMessage>(new MyResponseMessage("OK"), HttpStatus.OK);
		} else {
			return new ResponseEntity<MyResponseMessage>(new MyResponseMessage("ERROR"), HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(method = RequestMethod.PATCH, path = "/categroy")
	private ResponseEntity<MyResponseMessage> updateCategory(@RequestBody CategoryDto categoryDto){
		Category category = categoryRepo.findById(categoryDto.getExternalId());
		if(category != null) {
			category.setName(categoryDto.getName());
			categoryRepo.save(category);
			return new ResponseEntity<MyResponseMessage>(new MyResponseMessage("OK"), HttpStatus.OK);
		} else {
			return new ResponseEntity<MyResponseMessage>(new MyResponseMessage("ERROR"), HttpStatus.CONFLICT);	
		}
	}

}
