package com.figura4;

import java.util.List;

public interface ExpenseTypeList {
	public List<ExpenseType> getTypes();
	public ExpenseType getType(long typeId);
	public ExpenseType newType(String typeDescription);
	public void deleteType(ExpenseType type);
}
