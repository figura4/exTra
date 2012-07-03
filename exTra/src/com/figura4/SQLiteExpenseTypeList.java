package com.figura4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteExpenseTypeList implements ExpenseTypeList {
	// Database fields
	private SQLiteDatabase database;
	private ExpenseSQLiteHelper dbHelper;
	private String[] allColumns = { ExpenseSQLiteHelper.TABLE_TYPES_COLUMN_ID, 
									 ExpenseSQLiteHelper.TABLE_TYPES_COLUMN_DESCRIPTION};
	private List<ExpenseType> types = new ArrayList<ExpenseType>();

	public SQLiteExpenseTypeList(Context context) {
		dbHelper = new ExpenseSQLiteHelper(context);
		open();
		// query for types list
		Cursor cursor = database.query(ExpenseSQLiteHelper.TABLE_TYPES,
				allColumns, null, null, null, null, null);

		// generates type list
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ExpenseType type = new  StandardExpenseType(cursor.getLong(0), cursor.getString(1));
			types.add(type);
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

	public ExpenseType newType(String typeDescription) {
		ContentValues values = new ContentValues();
		values.put(ExpenseSQLiteHelper.TABLE_TYPES_COLUMN_DESCRIPTION, typeDescription);
		
		long insertId = database.insert(ExpenseSQLiteHelper.TABLE_TYPES, null, values);
		ExpenseType type = new StandardExpenseType(insertId, typeDescription);
		return type;
	}

	public void deleteType(ExpenseType type) {
		database.delete(ExpenseSQLiteHelper.TABLE_TYPES, 
						ExpenseSQLiteHelper.TABLE_TYPES_COLUMN_ID + " = " + type.getId(), null);
	}

	public List<ExpenseType> getTypes() {
		return types;
	}

	@Override
	public ExpenseType getType(long typeId) {
		ExpenseType returnValue = null;
		Iterator<ExpenseType> iterator = types.iterator();
		while(iterator.hasNext()) {
			ExpenseType type = (ExpenseType) iterator.next();
			if (type.getId() == typeId) {
				returnValue = type;
				break;
			}
		}
		return returnValue;
	}

}
