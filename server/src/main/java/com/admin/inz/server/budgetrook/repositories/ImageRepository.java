package com.admin.inz.server.budgetrook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admin.inz.server.budgetrook.model.Expense;
import com.admin.inz.server.budgetrook.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{
	public Image findByName(String name);
	public Image findByExpense(Expense expense);
	public Image findById(Long id);
	
	@Override
	public void delete(Image image);
}
