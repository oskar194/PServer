package com.admin.inz.server.budgetrook.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = false)
public class CategoryDto {
	@JsonProperty
	private String name;
	@JsonProperty
	private List<ExpenseDto> expenses;
	@JsonProperty
	private String userEmail;
	@JsonProperty
	private Long externalId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ExpenseDto> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<ExpenseDto> expenses) {
		this.expenses = expenses;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Long getExternalId() {
		return externalId;
	}

	public void setExternalId(Long externalId) {
		this.externalId = externalId;
	}

}
