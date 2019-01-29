package com.admin.inz.server.budgetrook.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.admin.inz.server.budgetrook.dto.UserDto;
import com.admin.inz.server.budgetrook.model.User;
import com.admin.inz.server.budgetrook.repositories.RoleRepository;
import com.admin.inz.server.budgetrook.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepo;
	@Autowired
	RoleRepository roleRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public User register(UserDto user) throws RuntimeException {
		if (userExist(user.getEmail())) {
			throw new RuntimeException("User with email " + user.getEmail() + " exist!");
		}
		User userEnt = new User();
		userEnt.setEmail(user.getEmail());
		userEnt.setLastname(user.getLastName());
		userEnt.setName(user.getFirstName());
		userEnt.setPassword(passwordEncoder.encode(user.getPassword()));
		userEnt.setRoles(Arrays.asList(roleRepo.findByName("ROLE_USER")));
		userEnt.setEnabled(true);
		return userRepo.save(userEnt);

	}

	private boolean userExist(String email) {
		User user = userRepo.findByEmail(email);
		if (user == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public User login(String password, String login) {
		if (password == null || login == null) {
			return null;
		} else {
			User user = userRepo.findByEmail(login);
			if (user != null && passwordEncoder.matches(password, user.getPassword())) {
				return user;
			} else {
				return null;
			}
		}
	}
	
	@Override
	public User getUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}

}
