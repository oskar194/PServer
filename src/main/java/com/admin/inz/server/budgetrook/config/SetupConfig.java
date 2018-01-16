package com.admin.inz.server.budgetrook.config;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.admin.inz.server.budgetrook.model.Privilege;
import com.admin.inz.server.budgetrook.model.Role;
import com.admin.inz.server.budgetrook.model.User;
import com.admin.inz.server.budgetrook.repositories.PrivilegeRepository;
import com.admin.inz.server.budgetrook.repositories.RoleRepository;
import com.admin.inz.server.budgetrook.repositories.UserRepository;
import com.admin.inz.server.budgetrook.services.UserService;

@Component
public class SetupConfig {

	private boolean alreadySetup = false;

	@Autowired
	public UserService userService;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private PrivilegeRepository privRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@EventListener
	public void init(ApplicationReadyEvent event) {
		if (alreadySetup) {
			return;
		}
		initUser();
		alreadySetup = true;
	}

	@Transactional
	private void initUser() {
		Privilege readPriv = createPrivilegeIfNotFound("READ_PRIV");
		List<Privilege> adminPrivs = Arrays.asList(readPriv);
		List<Privilege> userPrivs = Arrays.asList(readPriv);
		createRoleIfNotFound("ROLE_ADMIN", adminPrivs);
		createRoleIfNotFound("ROLE_USER", userPrivs);

		Role adminRole = roleRepo.findByName("ROLE_ADMIN");
		final User user = new User();
		user.setName("adm");
		user.setLastname("adm");
		user.setPassword(passwordEncoder.encode("admin"));
		user.setSecret(passwordEncoder.encode("admin"));
		user.setEmail("admin@admin.com");
		user.setRoles(Arrays.asList(adminRole));
		user.setEnabled(true);
		userRepo.save(user);
	}

	@Transactional
	private Role createRoleIfNotFound(String name, List<Privilege> userPrivs) {
		Role role = roleRepo.findByName(name);
		if (role == null) {
			role = new Role(name);
			role.setPrivileges(userPrivs);
			roleRepo.save(role);
		}
		return role;
	}

	@Transactional
	private Privilege createPrivilegeIfNotFound(String name) {
		Privilege priv = privRepo.findByName(name);
		if (priv == null) {
			priv = new Privilege();
			priv.setName(name);
			privRepo.save(priv);
		}
		return priv;
	}


}
