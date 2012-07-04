package com.figura4.model;

import java.math.BigDecimal;


/** 
 * represents a single entry 
 * in the expenses log
 * 
 * @author figura4
 *
 */

public interface Expense {
	public void setYear(int year);
	public void setMonth(int month);
	public void setDay(int day);
	public void setType(ExpenseType type);
	public void setDescription(String description);
	public void setAmount(BigDecimal amount);
	
	public long getId();
	public int getYear();
	public int getMonth();
	public int getDay();
	public ExpenseType getType();
	public String getDescription();
	public BigDecimal getAmount();
	public long getTimeStamp();
}
