package com.figura4;

import java.math.BigDecimal;
import java.util.Calendar;

public class StandardExpense implements Expense {
	private long id;
	private Calendar date = Calendar.getInstance();
	private ExpenseType type;
	private String description;
	private BigDecimal amount;
	
	public StandardExpense(long id, int year, int month, int day, ExpenseType expenseType, String description, BigDecimal amount) {
		this.id = id;
		this.date.set(year, month-1, day);
		this.type = expenseType;
		this.description = description;
		this.amount = amount;
	}
	
	/*public void setId(long id){
		this.id = id;
	}*/
	
	public void setYear(int year){
		date.set(Calendar.YEAR, year);
	}
	
	public void setMonth(int month){
		date.set(Calendar.MONTH, month-1);
	}
	
	public void setDay(int day){
		date.set(Calendar.DAY_OF_MONTH, day);
	}
	
	public void setType(ExpenseType type) {
		this.type = type;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public void setAmount(BigDecimal amount){
		this.amount = amount;
	}
	
	public long getId(){
		return id;
	}
	
	public int getYear(){
		return date.get(Calendar.YEAR);
	}
	
	public int getMonth(){
		return date.get(Calendar.MONTH) + 1;
	}
	
	public int getDay(){
		return date.get(Calendar.DAY_OF_MONTH);
	}
	
	public ExpenseType getType(){
		return type;
	}
	
	public String getDescription(){
		return description;
	}
	
	public BigDecimal getAmount(){
		return amount;
	}
	
	public long getTimeStamp(){
		return date.getTimeInMillis();
	}
	
}
