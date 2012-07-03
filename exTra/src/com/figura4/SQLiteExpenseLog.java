package com.figura4;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteExpenseLog implements ExpenseLog {

	// Database fields
	private SQLiteDatabase database;
	private ExpenseSQLiteHelper dbHelper;
	private String[] allColumns = { ExpenseSQLiteHelper.TABLE_EXPENSES_COLUMN_ID,
									 ExpenseSQLiteHelper.TABLE_EXPENSES_COLUMN_TYPE_ID,
									 ExpenseSQLiteHelper.TABLE_EXPENSES_COLUMN_DATE,
									 ExpenseSQLiteHelper.TABLE_EXPENSES_COLUMN_DESCRIPTION,
									 ExpenseSQLiteHelper.TABLE_EXPENSES_COLUMN_DESCRIPTION};
	private List<Expense> expenses = new ArrayList<Expense>();
	//private List<ExpenseType> typeList;

	public SQLiteExpenseLog(Context context, int year, int month, long type, long subtype) {
		dbHelper = new ExpenseSQLiteHelper(context);
		open();
		
		//typeList = new SQLiteExpenseTypeList(context).getTypes();
		
		// query for requested expenses
		Calendar calendar_from = Calendar.getInstance();
		calendar_from.set(year, month, 1);
		Calendar calendar_to = Calendar.getInstance();
		int maxDay = calendar_from.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar_to.set(year, month, maxDay);
		String from = String.format("date>=%s and date<=%s", calendar_from.getTimeInMillis(), calendar_to.getTimeInMillis());
		Cursor cursor = database.query(ExpenseSQLiteHelper.TABLE_EXPENSES,
				allColumns, from, null, null, null, null);

		// generates expense list
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Expense expense = cursorToExpense(cursor);
			expenses.add(expense);
			cursor.moveToNext();
		}
		// closes the cursor
		cursor.close();
		close();
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Expense newExpense(Expense expense) {
		ContentValues values = new ContentValues();
		values.put(ExpenseSQLiteHelper.TABLE_EXPENSES_COLUMN_TYPE_ID, expense.getType().getId());
		values.put(ExpenseSQLiteHelper.TABLE_EXPENSES_COLUMN_DATE, expense.getTimeStamp());
		values.put(ExpenseSQLiteHelper.TABLE_EXPENSES_COLUMN_AMOUNT, expense.getAmount().toString());
		values.put(ExpenseSQLiteHelper.TABLE_EXPENSES_COLUMN_DESCRIPTION, expense.getDescription());
		
		long insertId = database.insert(ExpenseSQLiteHelper.TABLE_EXPENSES, null, values);
		Cursor cursor = database.query(ExpenseSQLiteHelper.TABLE_EXPENSES, allColumns, ExpenseSQLiteHelper.TABLE_EXPENSES_COLUMN_ID + " = " + insertId, 
										null, null, null, null);
		cursor.moveToFirst();
		Expense newExpense = cursorToExpense(cursor);
		cursor.close();
		return newExpense;
	}

	public void deleteExpense(Expense expense) {
		long id = expense.getId();
		database.delete(ExpenseSQLiteHelper.TABLE_EXPENSES, 
						ExpenseSQLiteHelper.TABLE_EXPENSES_COLUMN_ID + " = " + id, null);
	}

	public List<Expense> getExpenses() {
		return expenses;
	}

	private Expense cursorToExpense(Cursor cursor) {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(cursor.getLong(1));
		
		ExpenseType expenseType = new StandardExpenseType(cursor.getInt(2), cursor.getString(3));
		
		Expense expense = new StandardExpense(
							cursor.getLong(0),
							date.get(Calendar.YEAR),
							date.get(Calendar.MONTH),
							date.get(Calendar.DAY_OF_MONTH),
							expenseType,
							cursor.getString(3),
							new BigDecimal(cursor.getLong(4)));
				
		return expense;
	}
	
	public BigDecimal getTotalamount() {
		return new BigDecimal("1");
	}
	
	/*public ArrayList<Map<String, Object>> getExpenses() {
		ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        
        Map<String, Object> map = new HashMap<String, Object>();
    	map.put("type", "bolletta");
    	map.put("subtype", "elettrica");
    	map.put("description", "periodo maggio giugno 2012");
    	map.put("date", "12/12/2012");
    	map.put("amount", "127.32€");
        data.add(map);
        
        map = new HashMap<String, Object>();
    	map.put("type", "libri");
    	map.put("subtype", "informatica");
    	map.put("description", "java, servlet e jsp");
    	map.put("date", "31/05/2012");
    	map.put("amount", "47.32€");
        data.add(map);
        
        map = new HashMap<String, Object>();
    	map.put("type", "bolletta");
    	map.put("subtype", "gas");
    	map.put("description", "periodo maggio giugno 2012");
    	map.put("date", "06/06/2012");
    	map.put("amount", "65.38€");
        data.add(map);
        
        return data;
	}*/

}
