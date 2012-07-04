package com.figura4.model;

import java.math.BigDecimal;

import android.content.Context;

import com.figura4.PreferencesManager;
import com.figura4.SQLite.SQLiteExpenseLog;
import com.figura4.SQLite.SQLiteExpenseTypeList;

/**
 * returns the appropriate resource
 * according to datasource shared preference
 * 
 * @author figura4
 *
 */
public class ResourceFactory {
	PreferencesManager prefManager;
	
	public ResourceFactory(Context context) {
		prefManager = new PreferencesManager(context);
	}
	
	public ExpenseLog getLog(Context context, int year, int month, ExpenseType type) {
		ExpenseLog log;
		
		switch (prefManager.getDataSource()) {
		case PreferencesManager.SQLITE:
			log = new SQLiteExpenseLog(context, year, month, type);
			break;
			
		default:
			log = new SQLiteExpenseLog(context, year, month, type);
		}
		
		return log;
	}
	
	public ExpenseTypeList getTypeList(Context context) {
		ExpenseTypeList log;
		
		switch (prefManager.getDataSource()) {
		case PreferencesManager.SQLITE:
			log = new SQLiteExpenseTypeList(context);
			break;
			
		default:
			log = new SQLiteExpenseTypeList(context);
		}
		
		return log;
	}
	
	public Expense getExpense(long id, int year, int month, int day, ExpenseType expenseType, String description, BigDecimal amount) {
		return new StandardExpense(id, year, month, day, expenseType, description, amount);
	}
}
