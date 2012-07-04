package com.figura4.model;


public class StandardExpenseType implements ExpenseType {
	private long id;
	private String description;
	
	public StandardExpenseType(long id, String description) {
		this.id = id;
		setDescription(description);
	}
	
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setDescription(String description) {
		this.description =  description;
	}
}
