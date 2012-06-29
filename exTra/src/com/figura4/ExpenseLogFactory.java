package com.figura4;

import android.content.Context;

public abstract class ExpenseLogFactory {
	abstract ExpenseLog createLog(Context context, int year, int month, long type, long subtype);
}
