package com.figura4;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

public interface ExpenseLog {
	public Expense newExpense(Expense expense);
	public void deleteExpense(Expense expense);
	public BigDecimal getTotalamount();
	public ArrayList<Map<String, Object>> getExpenses();
}
