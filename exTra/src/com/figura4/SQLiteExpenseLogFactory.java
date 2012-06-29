package com.figura4;

import android.content.Context;

public class SQLiteExpenseLogFactory extends ExpenseLogFactory {
	
	ExpenseLog createLog(Context context, int year, int month, long type, long subtype) {
		return new SQLiteExpenseLog(context, year, month, type, subtype);
	}

}
