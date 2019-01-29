package com.admin.inz.server.budgetrook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.admin.inz.server.budgetrook.dto.ExpenseDto;
import com.admin.inz.server.budgetrook.model.Expense;
import com.admin.inz.server.budgetrook.repositories.ExpenseRepository;
import com.admin.inz.server.budgetrook.response.MyResponseMessage;
import com.admin.inz.server.budgetrook.services.ConversionService;

@RestController
public class ExpenseController {

	@Autowired
	private ConversionService conversionService;
	@Autowired
	private ExpenseRepository expenseRepository;

	@RequestMapping(method = RequestMethod.GET, path = "/expenses")
	public ResponseEntity<List<ExpenseDto>> getExpenses() {
		List<Expense> expenses = expenseRepository.findAll();
		return new ResponseEntity<List<ExpenseDto>>(conversionService.expenseModelToDto(expenses), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/expense")
	public ResponseEntity<ExpenseDto> postExpense(@RequestBody ExpenseDto expenseDto) {
		Expense expense = conversionService.expenseDtoToModel(expenseDto);
		expense = expenseRepository.save(expense);
		return new ResponseEntity<ExpenseDto>(conversionService.expenseModelToDto(expense), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, path = "/expense")
	public ResponseEntity<MyResponseMessage> updateExpense(@RequestBody ExpenseDto expenseDto) {
		Expense expense = expenseRepository.findById(expenseDto.getExternalId());
		if (expense != null) {
			expenseRepository.save(conversionService.expenseDtoToModel(expenseDto));
			return new ResponseEntity<MyResponseMessage>(new MyResponseMessage("OK"), HttpStatus.OK);
		} else {
			return new ResponseEntity<MyResponseMessage>(new MyResponseMessage("Error"), HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/expense")
	public ResponseEntity<MyResponseMessage> deleteExpense(@RequestBody ExpenseDto expenseDto) {
		Expense expense = expenseRepository.findById(expenseDto.getExternalId());
		if (expense != null) {
			expenseRepository.delete(expense);
			return new ResponseEntity<MyResponseMessage>(new MyResponseMessage("OK"), HttpStatus.OK);
		} else {
			return new ResponseEntity<MyResponseMessage>(new MyResponseMessage("Error"), HttpStatus.CONFLICT);
		}
	}
}
