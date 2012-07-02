package com.figura4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ExpenseSQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_EXPENSES = "expenses";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TYPE_ID = "type_id";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_AMOUNT = "amount";

	private static final String DATABASE_NAME = "expenses.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_EXPENSES + "(" 
			+ COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_TYPE_ID + " integer, "
			+ COLUMN_DATE + " integer not null, "
			+ COLUMN_DESCRIPTION + " text, "
			+ COLUMN_AMOUNT + " real not null"
			+ ");";

	public ExpenseSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(ExpenseSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
		onCreate(db);
	}
	
}
