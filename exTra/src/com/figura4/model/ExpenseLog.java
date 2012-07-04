package com.figura4.model;
import java.math.BigDecimal;
import java.util.List;


/**
 * represents an expenses log,
 * containing all expense entries 
 * in a given time interval
 * @author figura4
 *
 */
public interface ExpenseLog {
	public Expense newExpense(Expense expense);
	public void deleteExpense(Expense expense);
	public BigDecimal getTotalamount();
	public List<Expense> getExpenses(); // returns all entries in the log as an ArrayList of Expenses
}
