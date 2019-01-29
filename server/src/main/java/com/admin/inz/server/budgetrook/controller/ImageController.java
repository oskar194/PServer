package com.admin.inz.server.budgetrook.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.admin.inz.server.budgetrook.dto.ImageDto;
import com.admin.inz.server.budgetrook.dto.ParseDto;
import com.admin.inz.server.budgetrook.image.processing.ImageProcessor;
import com.admin.inz.server.budgetrook.model.Image;
import com.admin.inz.server.budgetrook.repositories.ExpenseRepository;
import com.admin.inz.server.budgetrook.repositories.ImageRepository;
import com.admin.inz.server.budgetrook.response.MyResponseMessage;
import com.admin.inz.server.budgetrook.services.ConversionService;

@RestController
public class ImageController {

	@Autowired
	private ImageRepository imageRepo;
	@Autowired
	private ConversionService conversionService;
	@Autowired
	private ImageProcessor imageProcessor;
	@Autowired
	private ExpenseRepository expenseRepo;

	@RequestMapping(method = RequestMethod.GET, path = "/images")
	public ResponseEntity<List<ImageDto>> getImages() {
		List<ImageDto> imageDtos = new ArrayList<ImageDto>();
		for (Image image : imageRepo.findAll()) {
			imageDtos.add(conversionService.imageModelToDto(image));
		}
		return new ResponseEntity<List<ImageDto>>(imageDtos, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/image")
	public ResponseEntity<ImageDto> postImage(@RequestBody ImageDto imageDto) {
		Image image = conversionService.imageDtoToModel(imageDto);
		image = imageRepo.save(image);
		image.setExpense(expenseRepo.findById(imageDto.getExpenseId()));
		ImageDto response = conversionService.imageModelToDto(image);
		return new ResponseEntity<ImageDto>(response, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json", "application/octet-stream",
			"multipart/form-data" }, path = "/image/process/{id}")
	public ResponseEntity<ParseDto> postAndProcess(@RequestPart MultipartFile file, @PathVariable Long id) {
		try {
			ParseDto result = imageProcessor.saveAndProcess(file.getBytes(), id);
			return new ResponseEntity<ParseDto>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ParseDto>(new ParseDto(), HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(method = RequestMethod.PATCH, params = "/image")
	public ResponseEntity<MyResponseMessage> updateImage(@RequestBody ImageDto imageDto) {
		Long id = imageDto.getExternalId();
		if (imageRepo.findById(id) != null) {
			Image image = conversionService.imageDtoToModel(imageDto);
			imageRepo.save(image);
			return new ResponseEntity<MyResponseMessage>(new MyResponseMessage("OK"), HttpStatus.OK);
		} else {
			return new ResponseEntity<MyResponseMessage>(new MyResponseMessage("Error"), HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, params = "/image")
	public ResponseEntity<MyResponseMessage> deleteImage(@RequestBody ImageDto imageDto) {
		Long id = imageDto.getExternalId();
		if (imageRepo.findById(id) != null) {
			Image image = conversionService.imageDtoToModel(imageDto);
			imageRepo.delete(image);
			return new ResponseEntity<MyResponseMessage>(new MyResponseMessage("OK"), HttpStatus.OK);
		} else {
			return new ResponseEntity<MyResponseMessage>(new MyResponseMessage("Error"), HttpStatus.CONFLICT);
		}
	}
}
