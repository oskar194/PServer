package com.admin.inz.server.budgetrook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.admin.inz.server.budgetrook.image.processing.ImageProcessor;
import com.admin.inz.server.budgetrook.response.MyResponseMessage;


@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	@Autowired
	private ImageProcessor imageProcessor;

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json",
			"application/octet-stream", "multipart/form-data" }, path = "/uploadPhoto")
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
	}

//	@RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST, produces = "application/json", consumes = "application/x-www-form-urlencoded")
//	public @ResponseBody ResponseEntity<String> handleFileUpload() {
//		String name = "test11";
//		try {
//			return new ResponseEntity<String>("You successfully uploaded " + name + " into " + name + "-uploaded !", HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<String>( "You failed to upload " + name + " => " + e.getMessage(), HttpStatus.CONFLICT);
//		}
//	}

