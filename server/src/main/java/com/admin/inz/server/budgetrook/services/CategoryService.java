package com.admin.inz.server.budgetrook.services;

import java.util.List;

import com.admin.inz.server.budgetrook.dto.CategoryDto;
import com.admin.inz.server.budgetrook.model.Category;
import com.admin.inz.server.budgetrook.model.User;

public interface CategoryService {
	public void saveCategory(Category category);
	public void saveCategory(CategoryDto categoryDto);
	public List<Category> getCategoriesByUser(User user);
	public Category getCategoryByName(String name);
}
