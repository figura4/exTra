package com.figura4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ExpenseSQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_EXPENSES = "expenses";
	public static final String TABLE_EXPENSES_COLUMN_ID = "_id";
	public static final String TABLE_EXPENSES_COLUMN_TYPE_ID = "type_id";
	public static final String TABLE_EXPENSES_COLUMN_DATE = "date";
	public static final String TABLE_EXPENSES_COLUMN_DESCRIPTION = "description";
	public static final String TABLE_EXPENSES_COLUMN_AMOUNT = "amount";
	
	public static final String TABLE_TYPES = "types";
	public static final String TABLE_TYPES_COLUMN_ID = "_id";
	public static final String TABLE_TYPES_COLUMN_DESCRIPTION = "description";
	

	private static final String DATABASE_NAME = "expenses.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE_EXPENSES = "create table "
			+ TABLE_EXPENSES + "(" 
			+ TABLE_EXPENSES_COLUMN_ID + " integer primary key autoincrement, " 
			+ TABLE_EXPENSES_COLUMN_TYPE_ID + " integer, "
			+ TABLE_EXPENSES_COLUMN_DATE + " integer not null, "
			+ TABLE_EXPENSES_COLUMN_DESCRIPTION + " text, "
			+ TABLE_EXPENSES_COLUMN_AMOUNT + " real not null"
			+ ");";
	
	private static final String DATABASE_CREATE_TYPES = "create table "  
			+ TABLE_TYPES + "("
			+ TABLE_TYPES_COLUMN_ID + " integer primary key autoincrement, "
			+ TABLE_TYPES_COLUMN_DESCRIPTION + " text not null"
			+ ");";	
	

	public ExpenseSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_EXPENSES);
		
		database.execSQL(DATABASE_CREATE_TYPES);
		String value = "bollette";
		String insertQueryPrefix ="insert into " + TABLE_TYPES 
							+ " (description) "
							+ "values (\"";
		String insertQuerySuffix = "\"); ";
		
		database.execSQL(insertQueryPrefix + value + insertQuerySuffix);
		
		value = "elettronica di consumo";
		database.execSQL(insertQueryPrefix + value + insertQuerySuffix);
		
		value = "cellulare";
		database.execSQL(insertQueryPrefix + value + insertQuerySuffix);
		
		value = "libri";
		database.execSQL(insertQueryPrefix + value + insertQuerySuffix);
		
		value = "benzina";
		database.execSQL(insertQueryPrefix + value + insertQuerySuffix);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(ExpenseSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPES);
		onCreate(db);
	}
	
}
