package com.figura4;

import java.util.ArrayList;
import java.util.List;

import com.figura4.R;
import com.figura4.model.Expense;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * adapter for the expenses ListView in the main activity
 * 
 * @author figura4
 *
 */
final class ExpensesAdapter extends BaseAdapter {
	
	private List<Expense> expenseList = new ArrayList<Expense>();
	private LayoutInflater inflater; 

	public ExpensesAdapter(Context context, List<Expense> data) {
		//super();
		this.inflater = LayoutInflater.from(context);
		expenseList = data;
	}
	
	@Override
	public int getCount() {
		return expenseList.size();
	}

	@Override
	public Object getItem(int position) {
		return expenseList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    View view = convertView;
	    if(view == null) {
	        view = inflater.inflate(R.layout.expenses_list_row, null);
	    }

	    Expense expense = expenseList.get(position);
	    
	    // row customization
	    TextView type = (TextView)view.findViewById(R.id.row_type);
	    TextView amount = (TextView)view.findViewById(R.id.row_amount);
	    TextView date = (TextView)view.findViewById(R.id.row_date);
	    TextView description = (TextView)view.findViewById(R.id.row_description);
	    
	    type.setText(expense.getType().getDescription());
	    amount.setText(expense.getAmount().toString() + "€");
	    date.setText(expense.getDay() + "/" + (expense.getMonth()+1) + "/" + expense.getYear());
	    description.setText(expense.getDescription());

	    return view;
	}

}
