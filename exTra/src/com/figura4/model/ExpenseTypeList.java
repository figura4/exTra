package com.figura4.model;

import java.util.List;


/**
 * represents the list of all possible
 * expense types
 * 
 * @author figura4
 *
 */
public interface ExpenseTypeList {
	public List<ExpenseType> getTypes();
	public ExpenseType getType(long typeId);
	public ExpenseType newType(String typeDescription);
	public void deleteType(ExpenseType type);
}
