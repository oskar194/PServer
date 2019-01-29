package com.admin.inz.server.budgetrook.dto;

import java.util.Date;
import java.util.List;

import com.admin.inz.server.budgetrook.helpers.CustomerDateAndTimeDeserialize;
import com.admin.inz.server.budgetrook.helpers.CustomerDateAndTimeSerialize;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseDto {
	@JsonProperty
	private Double amount;
	@JsonProperty
	private String categoryName;
	@JsonProperty
	private String name;
	@JsonProperty
	@JsonDeserialize(using=CustomerDateAndTimeDeserialize.class)
	@JsonSerialize(using=CustomerDateAndTimeSerialize.class)
	private Date dateAdded;
	@JsonProperty
	private String userEmail;
	@JsonProperty
	private List<ImageDto> images;
	@JsonProperty
	private Long externalId;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public List<ImageDto> getImages() {
		return images;
	}

	public void setImages(List<ImageDto> images) {
		this.images = images;
	}

	public Long getExternalId() {
		return externalId;
	}

	public void setExternalId(Long externalId) {
		this.externalId = externalId;
	}
	
}
