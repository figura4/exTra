package com.figura4;

public class SQLiteExpenseLogFactory extends ExpenseLogFactory {
	ExpenseLog createLog(int year, int month, long type, long subtype) {
		return new SQLiteExpenseLog();
	}
}
