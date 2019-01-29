package com.admin.inz.server.budgetrook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private User owner;
	@Column(name = "DATA", unique = false, length = 10000000)
	private String bytesBase64;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expense_id")
	private Expense expense;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getBytesBase64() {
		return bytesBase64;
	}

	public void setBytesBase64(String bytesBase64) {
		this.bytesBase64 = bytesBase64;
	}

	public Expense getExpense() {
		return expense;
	}

	public void setExpense(Expense expense) {
		this.expense = expense;
	}

	@Override
	public String toString() {
		return "Image [id=" + id + ", name=" + name + ", owner=" + owner + ", expense=" + expense + "]";
	}
	
}
