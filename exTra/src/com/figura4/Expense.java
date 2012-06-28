package com.figura4;

import java.math.BigDecimal;

public interface Expense {
	public void setYear(int year);
	public void setMonth(int month);
	public void setDay(int day);
	public void setType(int type);
	public void setDescription(String description);
	public void setAmount(BigDecimal amount);
	
	public long getId();
	public int getYear();
	public int getMonth();
	public int getDay();
	public int getType();
	public String getDescription();
	public BigDecimal getAmount();
}
