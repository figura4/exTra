package com.figura4;
import java.math.BigDecimal;
import java.util.List;

public interface ExpenseLog {
	public Expense newExpense(Expense expense);
	public void deleteExpense(Expense expense);
	public BigDecimal getTotalamount();
	public List<Expense> getExpenses();
}
