package com.admin.inz.server.budgetrook.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.admin.inz.server.budgetrook.model.Category;
import com.admin.inz.server.budgetrook.model.Expense;
import com.admin.inz.server.budgetrook.model.Image;
import com.admin.inz.server.budgetrook.model.Privilege;
import com.admin.inz.server.budgetrook.model.Role;
import com.admin.inz.server.budgetrook.model.User;
import com.admin.inz.server.budgetrook.repositories.CategoryRepository;
import com.admin.inz.server.budgetrook.repositories.ExpenseRepository;
import com.admin.inz.server.budgetrook.repositories.ImageRepository;
import com.admin.inz.server.budgetrook.repositories.PrivilegeRepository;
import com.admin.inz.server.budgetrook.repositories.RoleRepository;
import com.admin.inz.server.budgetrook.repositories.UserRepository;
import com.admin.inz.server.budgetrook.services.UserService;

@Component
public class SetupConfig {

	private static final String FILE_PATH = "sampleData/testReceipt.jpg";

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
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ExpenseRepository expenseRepository;
	@Autowired
	private ImageRepository imageRepository;

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
		List<Expense> expenses = setupExpenses();
		List<Category> categories = setupCategories();
		userRepo.save(user);
//		Image image = setupImage();
//		image.setOwner(user);
//		image.setExpense(expenses.get(0));
		for (int i = 0; i < expenses.size(); i++) {
			categories.get(i).setOwner(user);
			expenses.get(i).setOwner(user);
			expenses.get(i).setCategory(categories.get(i));
			categoryRepository.save(categories.get(i));
			expenseRepository.save(expenses.get(i));
		}
//		imageRepository.save(image);
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

	private List<Expense> setupExpenses() {
		Expense expense = new Expense();
		expense.setAmount(200.0);
		expense.setDateAdded(new Date());
		expense.setName("Pierogi");
		expense.setImages(Arrays.asList(setupImage()));
		Expense otherExpense = new Expense();
		otherExpense.setAmount(150.0);
		otherExpense.setDateAdded(new Date());
		otherExpense.setName("Paliwo");
		return Arrays.asList(expense, otherExpense);
	}

	private List<Category> setupCategories() {
		List<Expense> expenses = setupExpenses();
		Category category = new Category();
		category.setExpenses(Arrays.asList(expenses.get(0)));
		category.setName("Jedzenie");
		Category otherCategory = new Category();
		otherCategory.setExpenses(Arrays.asList(expenses.get(1)));
		otherCategory.setName("Samochod");
		return Arrays.asList(category, otherCategory);
	}

	private Image setupImage() {
		Image image = new Image();
		image.setBytesBase64(readTestImage());
		image.setName("Paragon za pierogi");
		return image;
	}

	private String readTestImage() {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(FILE_PATH).getFile());
		try {
			Path path = Paths.get(file.getAbsolutePath());
			byte[] data = Files.readAllBytes(path);
			return Base64.getEncoder().encodeToString(data);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
