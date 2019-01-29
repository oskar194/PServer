package com.admin.inz.server.budgetrook.services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.admin.inz.server.budgetrook.dto.ExpenseDto;
import com.admin.inz.server.budgetrook.model.Category;
import com.admin.inz.server.budgetrook.model.Expense;
import com.admin.inz.server.budgetrook.model.User;

public interface ExpenseService {
	public void saveExpense(ExpenseDto expense);
	public Expense getExpenseByName(String name);
	public List<Expense> getExpensesByOwner(User owner);
	public void deleteExpense(Expense expense);
	public void saveExpense(Expense expense);
	public Collection<Expense> getExpensesBetweenDates(Date from, Date to);
	public Collection<Expense> getExpensesByCategory(Category category);
}
