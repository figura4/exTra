package com.figura4;

public abstract class ExpenseLogFactory {
	abstract ExpenseLog createLog(int year, int month, long type, long subtype);
}
