package com.admin.inz.server.budgetrook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.admin.inz.server.budgetrook.dto.UserDto;
import com.admin.inz.server.budgetrook.model.User;
import com.admin.inz.server.budgetrook.response.MyResponseMessage;
import com.admin.inz.server.budgetrook.services.ConversionService;
import com.admin.inz.server.budgetrook.services.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private ConversionService convService;

	@RequestMapping(method = RequestMethod.POST, path = "/register")
	public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
		try {
			User u = userService.register(userDto);
			userDto = convService.userModelToDto(u);
			return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<UserDto>(userDto, HttpStatus.CONFLICT);
		}
	}

}
