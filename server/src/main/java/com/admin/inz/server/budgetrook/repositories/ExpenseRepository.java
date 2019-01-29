package com.admin.inz.server.budgetrook.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admin.inz.server.budgetrook.model.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	Optional<Expense> findByName(String name);
	Optional<List<Expense>> findByDateAddedBetween(Date from, Date to); 
	Expense findById(Long id);
	
	@Override
	public void delete(Expense expense);
}
