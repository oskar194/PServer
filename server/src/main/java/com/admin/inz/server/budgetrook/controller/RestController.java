package com.admin.inz.server.budgetrook.controller;

import java.util.Collection;
import java.util.List;

import javax.xml.ws.RequestWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.admin.inz.server.budgetrook.dto.CategoryDto;
import com.admin.inz.server.budgetrook.dto.ExpenseDto;
import com.admin.inz.server.budgetrook.image.processing.ImageProcessor;
import com.admin.inz.server.budgetrook.model.Category;
import com.admin.inz.server.budgetrook.model.Image;
import com.admin.inz.server.budgetrook.response.MyResponseMessage;
import com.admin.inz.server.budgetrook.services.CategoryService;
import com.admin.inz.server.budgetrook.services.ConversionService;
import com.admin.inz.server.budgetrook.services.ExpenseService;
import com.admin.inz.server.budgetrook.services.ImageService;
import com.admin.inz.server.budgetrook.services.UserService;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	@Autowired
	private ImageProcessor imageProcessor;
	@Autowired
	private ImageService imageService;
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ExpenseService expenseService;
	@Autowired
	private ConversionService conversionService;
/*
	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json", "application/octet-stream",
			"multipart/form-data" }, path = "/uploadPhoto")
	public MyResponseMessage getAllTheSyncData(@RequestPart("pic") MultipartFile file) {
		System.out.println("Przyszly dane!");
		try {
			imageProcessor.saveImage(file.getBytes());
			System.out.println("Dane zapisane");
		} catch (Exception e) {
			return new MyResponseMessage(e.getMessage());
		}
		return new MyResponseMessage("SUCCESS");
	}

//	@RequestMapping(method = RequestMethod.GET, path = "/doProcessing")
//	public MyResponseMessage doProcessing() {
//		return new MyResponseMessage(imageProcessor.process());
//	}

	@RequestMapping(method = RequestMethod.GET, path = "/getAllImageNames")
	public MyResponseMessage getAllImageNames() {
		String message = null;
		Collection<Image> images = imageService.getImagesByOwner(userService.getUserByEmail("admin@admin.com"));
		for (Image image : images) {
			message += image.getName() + ' ';
		}
		MyResponseMessage result = new MyResponseMessage(message);
		return result;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getCategories")
	public ResponseEntity<List<CategoryDto>> getCategories() {
		List<CategoryDto> categories = conversionService.categoryModelToDto(
				categoryService.getCategoriesByUser((userService.getUserByEmail("admin@admin.com"))));
		ResponseEntity<List<CategoryDto>> result = new ResponseEntity<List<CategoryDto>>(categories, HttpStatus.OK);
		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/getCategoryByName/{name}")
	public ResponseEntity<CategoryDto> getCategoryByName(@PathVariable("name") String name) {
		Category category = categoryService.getCategoryByName(name);
		if(category == null) {
			ResponseEntity<CategoryDto> result = new ResponseEntity<CategoryDto>(new CategoryDto(), HttpStatus.CONFLICT);
			return result;
		}
		CategoryDto categoryDto = conversionService.categoryModelToDto(category);
		ResponseEntity<CategoryDto> result = new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
		return result;
	}


	@RequestMapping(method = RequestMethod.GET, path = "/getExpenses")
	public ResponseEntity<List<ExpenseDto>> getExpenses() {
		List<ExpenseDto> expenses = conversionService
				.expenseModelToDto(expenseService.getExpensesByOwner((userService.getUserByEmail("admin@admin.com"))));
		ResponseEntity<List<ExpenseDto>> result = new ResponseEntity<List<ExpenseDto>>(expenses, HttpStatus.OK);
		return result;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/category")
	public ResponseEntity<String> postCategory(@RequestBody CategoryDto categoryDto){
		System.out.println(categoryDto);
		try {
			Category category = conversionService.categoryDtoToModel(categoryDto);
			categoryService.saveCategory(category);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
//	@RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST, produces
// = "application/json", consumes = "application/x-www-form-urlencoded")
// public @ResponseBody ResponseEntity<String> handleFileUpload() {
// String name = "test11";
// try {
// return new ResponseEntity<String>("You successfully uploaded " + name + "
// into " + name + "-uploaded !", HttpStatus.OK);
// } catch (Exception e) {
// return new ResponseEntity<String>( "You failed to upload " + name + " => " +
// e.getMessage(), HttpStatus.CONFLICT);
// }
 * */
 }
