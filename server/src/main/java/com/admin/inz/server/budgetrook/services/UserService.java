package com.admin.inz.server.budgetrook.services;

import com.admin.inz.server.budgetrook.dto.UserDto;
import com.admin.inz.server.budgetrook.model.User;

public interface UserService {
	public User register(UserDto user) throws RuntimeException;
	public User login(String password, String login);
	public User getUserByEmail(String email);
}
