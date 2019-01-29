package com.admin.inz.server.budgetrook.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.inz.server.budgetrook.dto.CategoryDto;
import com.admin.inz.server.budgetrook.model.Category;
import com.admin.inz.server.budgetrook.model.User;
import com.admin.inz.server.budgetrook.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ConversionService convService;

	@Override
	public void saveCategory(Category category) {
		categoryRepository.save(category);
	}

	@Override
	public void saveCategory(CategoryDto categoryDto) {
		categoryRepository.save(convService.categoryDtoToModel(categoryDto));
	}

	@Override
	public List<Category> getCategoriesByUser(User user) {
		return user.getCategories();
	}

	@Override
	public Category getCategoryByName(String name) {
		try {
			return categoryRepository.findByName(name).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

}
