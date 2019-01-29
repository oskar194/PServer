package com.admin.inz.server.budgetrook.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.inz.server.budgetrook.dto.CategoryDto;
import com.admin.inz.server.budgetrook.dto.ExpenseDto;
import com.admin.inz.server.budgetrook.dto.ImageDto;
import com.admin.inz.server.budgetrook.dto.UserDto;
import com.admin.inz.server.budgetrook.model.Category;
import com.admin.inz.server.budgetrook.model.Expense;
import com.admin.inz.server.budgetrook.model.Image;
import com.admin.inz.server.budgetrook.model.User;
import com.admin.inz.server.budgetrook.repositories.CategoryRepository;
import com.admin.inz.server.budgetrook.repositories.ExpenseRepository;
import com.admin.inz.server.budgetrook.repositories.UserRepository;

@Service(value = "convService")
public class ConversionService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ExpenseRepository expenseRepository;

	public Category categoryDtoToModel(CategoryDto categoryDto) {
		Category category = new Category();
		category.setName(categoryDto.getName());
		category.setOwner(userRepository.findByEmail(categoryDto.getUserEmail()));
		return category;
	}

	public CategoryDto categoryModelToDto(Category category) {
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName(category.getName());
		categoryDto.setExternalId(category.getId());
		if (category.getOwner() != null) {
			categoryDto.setUserEmail(category.getOwner().getEmail());
		}
		return categoryDto;
	}

	public Expense expenseDtoToModel(ExpenseDto expenseDto) {
		Expense expense = new Expense();
		expense.setAmount(expenseDto.getAmount());
		expense.setCategory(categoryRepository.findByName(expenseDto.getCategoryName()).get());
		expense.setDateAdded(expenseDto.getDateAdded());
		expense.setName(expenseDto.getName());
		expense.setOwner(userRepository.findByEmail(expenseDto.getUserEmail()));
		return expense;
	}

	public ExpenseDto expenseModelToDto(Expense expense) {
		ExpenseDto expenseDto = new ExpenseDto();
		expenseDto.setExternalId(expense.getId());
		expenseDto.setAmount(expense.getAmount());
		expenseDto.setCategoryName(expense.getCategory().getName());
		expenseDto.setDateAdded(expense.getDateAdded());
		expenseDto.setName(expense.getName());
		expenseDto.setUserEmail(expense.getOwner().getEmail());
		return expenseDto;
	}

	public List<ExpenseDto> expenseModelToDto(List<Expense> expenses) {
		List<ExpenseDto> expenseDtoList = new ArrayList<ExpenseDto>();
		for (Expense expense : expenses) {
			expenseDtoList.add(expenseModelToDto(expense));
		}
		return expenseDtoList;
	}

	public List<CategoryDto> categoryModelToDto(List<Category> categories) {
		List<CategoryDto> categoryDtoList = new ArrayList<CategoryDto>();
		for (Category category : categories) {
			categoryDtoList.add(categoryModelToDto(category));
		}
		return categoryDtoList;
	}

	public ImageDto imageModelToDto(Image image) {
		if (image == null) {
			return new ImageDto();
		}
		ImageDto imageDto = new ImageDto();
		imageDto.setExternalId(image.getId());
		imageDto.setName(image.getName());
		imageDto.setUsername(image.getOwner().getEmail());
		imageDto.setExpenseId(image.getExpense().getId());
		return imageDto;
	}

	public Image imageDtoToModel(ImageDto imageDto) {
		Image image = new Image();
		image.setBytesBase64(imageDto.getBytesBase64());
		image.setName(imageDto.getName());
		image.setExpense(expenseRepository.findById(imageDto.getExpenseId()));
		image.setOwner(userRepository.findByEmail(imageDto.getUsername()));
		return image;
	}
	
	public UserDto userModelToDto(User user) {
		UserDto result = new UserDto();
		result.setEmail(user.getEmail());
		result.setExternalId(user.getId());
		return result;
	}
}
