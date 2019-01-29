package com.admin.inz.server.budgetrook.services;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.inz.server.budgetrook.dto.ExpenseDto;
import com.admin.inz.server.budgetrook.model.Category;
import com.admin.inz.server.budgetrook.model.Expense;
import com.admin.inz.server.budgetrook.model.User;
import com.admin.inz.server.budgetrook.repositories.ExpenseRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;
	@Autowired
	private ConversionService convService;

	@Override
	public void saveExpense(ExpenseDto expenseDto) {
		expenseRepository.save(convService.expenseDtoToModel(expenseDto));
	}

	@Override
	public Expense getExpenseByName(String name) {
		try {
			return expenseRepository.findByName(name).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	@Override
	public List<Expense> getExpensesByOwner(User owner) {
		return owner.getExpenses();
	}

	@Override
	public void deleteExpense(Expense expense) {

	}

	@Override
	public void saveExpense(Expense expense) {
		expenseRepository.save(expense);

	}

	@Override
	public Collection<Expense> getExpensesBetweenDates(Date from, Date to) {
		try {
			return expenseRepository.findByDateAddedBetween(from, to).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	@Override
	public Collection<Expense> getExpensesByCategory(Category category) {
		return category.getExpenses();
	}

}
