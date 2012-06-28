package com.figura4;

import java.math.BigDecimal;
import java.util.Calendar;

public class StandardExpense implements Expense {
	private long id;
	private Calendar date = Calendar.getInstance();
	private int type;
	private String description;
	private BigDecimal amount;
	
	public StandardExpense(long id, int year, int month, int day, int type, String description, BigDecimal amount) {
		this.id = id;
		this.date.set(year, month, day);
		this.type = type;
		this.description = description;
		this.amount = amount;
	}
	
	public void setYear(int year){
		date.set(Calendar.YEAR, year);
	}
	
	public void setMonth(int month){
		date.set(Calendar.MONTH, month);
	}
	
	public void setDay(int day){
		date.set(Calendar.DAY_OF_MONTH, day);
	}
	
	public void setType(int type){
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
		return date.get(Calendar.MONTH);
	}
	
	public int getDay(){
		return date.get(Calendar.DAY_OF_MONTH);
	}
	
	public int getType(){
		return type;
	}
	
	public String getDescription(){
		return description;
	}
	
	public BigDecimal getAmount(){
		return amount;
	}
	
}
