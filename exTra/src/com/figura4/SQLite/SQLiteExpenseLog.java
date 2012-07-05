package com.figura4.SQLite;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import com.figura4.model.Expense;
import com.figura4.model.ExpenseLog;
import com.figura4.model.ExpenseType;
import com.figura4.model.ExpenseTypeList;
import com.figura4.model.StandardExpense;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/**
 * expense log based on 
 * SQLite data source
 * 
 * @author figura4
 *
 */
public class SQLiteExpenseLog implements ExpenseLog {

	// Database fields
	private SQLiteDatabase database;
	private ExpenseSQLiteHelper dbHelper;
	private String[] allColumns = { ExpenseSQLiteHelper.TABLE_EXPENSES_COLUMN_ID,
									 ExpenseSQLiteHelper.TABLE_EXPENSES_COLUMN_TYPE_ID,
									 ExpenseSQLiteHelper.TABLE_EXPENSES_COLUMN_DATE,
									 ExpenseSQLiteHelper.TABLE_EXPENSES_COLUMN_DESCRIPTION,
									 ExpenseSQLiteHelper.TABLE_EXPENSES_COLUMN_AMOUNT };
	
	private List<Expense> expenses = new ArrayList<Expense>();
	private ExpenseTypeList typeList;

	public SQLiteExpenseLog(Context context, int year, int month, ExpenseType type) {
		dbHelper = new ExpenseSQLiteHelper(context);
		open();
		
		typeList = new SQLiteExpenseTypeList(context);

		// query for requested expenses
		Calendar calendar_from = Calendar.getInstance();
		calendar_from.set(year, month, 1, 0, 0, 1);
		Calendar calendar_to = Calendar.getInstance();
		int maxDay = calendar_from.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar_to.set(year, month, maxDay, 23, 59, 59);
		String where = String.format("date>=%s and date<=%s", calendar_from.getTimeInMillis(), calendar_to.getTimeInMillis());

		Cursor cursor = database.query(ExpenseSQLiteHelper.TABLE_EXPENSES, allColumns, where, null, null, null, "date desc");

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

	/**
	 * creates a new expense entry
	 */
	public Expense newExpense(Expense expense) {
		
		ContentValues values = new ContentValues();
		values.put(ExpenseSQLiteHelper.TABLE_EXPENSES_COLUMN_TYPE_ID, expense.getType().getId());
		values.put(ExpenseSQLiteHelper.TABLE_EXPENSES_COLUMN_DATE, expense.getTimeStamp());
		values.put(ExpenseSQLiteHelper.TABLE_EXPENSES_COLUMN_AMOUNT, expense.getAmount().toString());
		values.put(ExpenseSQLiteHelper.TABLE_EXPENSES_COLUMN_DESCRIPTION, expense.getDescription());
		
		Expense newExpense = null;
		open();
		
		try {
			long insertId = database.insert(ExpenseSQLiteHelper.TABLE_EXPENSES, null, values);
			Cursor cursor = database.query(ExpenseSQLiteHelper.TABLE_EXPENSES, allColumns, ExpenseSQLiteHelper.TABLE_EXPENSES_COLUMN_ID + " = " + insertId, 
					null, null, null, null);
			cursor.moveToFirst();
			newExpense = cursorToExpense(cursor);
			cursor.close();
		} catch(SQLiteException exception) {
			Log.i("errore insert", "errore nell'inserimento spesa");
		    exception.printStackTrace();
		} finally {
			close();
		}
		
		return newExpense;
	}

	public void deleteExpense(Expense expense) {
		long id = expense.getId();
		open();
		try {
			database.delete(ExpenseSQLiteHelper.TABLE_EXPENSES, 
							ExpenseSQLiteHelper.TABLE_EXPENSES_COLUMN_ID + " = " + id, null);
		} catch(SQLiteException exception) {
			Log.i("errore delete", "errore nella cancellazione spesa");
		    exception.printStackTrace();
		} finally {
			close();
		}
	}

	public List<Expense> getExpenses() {
		return expenses;
	}
	
	/*public Expense getExpense(long ExpenseId) {
		Iterator<Expense> iterator = expenses.iterator();
		Expense result = null;
		while(iterator.hasNext()) {
			result = iterator.next();
			if (result.getId() != ExpenseId) {
				result = null;
			} else {
				break;
			}	
		}
		return result;
	}*/
	
	public LinkedHashMap<String, Integer> getAmountsByType() {
		LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>(); 
		
		result.put("Bollette", 40);
		result.put("Cellulare", 30);
		result.put("Libri", 20);
		result.put("Benzina", 15);
		result.put("Altro", 5);

		return result;
	}

	/**
	 * converts a cursor current row into a StandardExpense object
	 * 
	 * @param cursor: data source
	 * @return converted Expense object
	 */
	private Expense cursorToExpense(Cursor cursor) {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(cursor.getLong(2));
		
		Expense expense = new StandardExpense(
							cursor.getLong(0),
							date.get(Calendar.YEAR),
							date.get(Calendar.MONTH),
							date.get(Calendar.DAY_OF_MONTH),
							typeList.getType(cursor.getInt(1)),
							cursor.getString(3),
							new BigDecimal(cursor.getString(4)));
				
		return expense;
	}
	
	/**
	 * returns the sum of the amount fields
	 * in the current log entries
	 */
	public BigDecimal getTotalamount() {
		BigDecimal totalAmount = new BigDecimal("0");
		Iterator<Expense> iterator = expenses.iterator();
		
		while(iterator.hasNext()) {
			totalAmount = totalAmount.add(iterator.next().getAmount());
		}

		return totalAmount;
	}

}
