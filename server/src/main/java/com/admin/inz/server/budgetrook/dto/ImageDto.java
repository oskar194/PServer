package com.admin.inz.server.budgetrook.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = false)
public class ImageDto {
	@JsonProperty
	private String name;
	@JsonProperty
	private String bytesBase64;
	@JsonProperty
	private String username;
	@JsonProperty
	private Long externalId;
	@JsonProperty
	private Long expenseId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBytesBase64() {
		return bytesBase64;
	}

	public void setBytesBase64(String bytesBase64) {
		this.bytesBase64 = bytesBase64;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getExternalId() {
		return externalId;
	}

	public void setExternalId(Long externalId) {
		this.externalId = externalId;
	}

	public Long getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Long expenseId) {
		this.expenseId = expenseId;
	}
}
