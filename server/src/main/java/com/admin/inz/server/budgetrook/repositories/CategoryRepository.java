package com.admin.inz.server.budgetrook.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.admin.inz.server.budgetrook.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	Optional<Category> findByName(String name);
	public Category findById(Long id);
	
	@Override
	public void delete(Category category);

	@Transactional
	public List<Category> findAll();
	
}
