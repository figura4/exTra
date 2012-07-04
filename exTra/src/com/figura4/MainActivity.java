package com.figura4;

import java.util.*;

import com.figura4.SQLite.SQLiteExpenseLog;
import com.figura4.model.ExpenseLog;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;

/**
 * activity displayed on app launch
 * 
 * @author figura4
 *
 */
public class MainActivity extends ListActivity implements OnClickListener {
	private ExpenseLog log;	 // current expese log
	private Spinner monthsSpinner;
	private Spinner yearsSpinner;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // getting current month/year
    	Calendar calendar = Calendar.getInstance();
    	int currentMonth = calendar.get(Calendar.MONTH);
    	int currentYear = calendar.get(Calendar.YEAR);
        
    	// creating Expense log
        log = new SQLiteExpenseLog(this, currentYear, currentMonth, -1, -1);
        
        // initializing views
        initList();
        initSpinners(currentMonth, currentYear);
        
        // setting button click listener
        View newExpenseButton = findViewById(R.id.new_expense_button);
        newExpenseButton.setOnClickListener(this);
    }
    
    /** initializes listview */
    private void initList() {
    	// using custom adapter  
        ListAdapter adapter = new ExpensesAdapter(this, log.getExpenses());
        setListAdapter(adapter);
    }
    
    /** initializes spinners */
    private void initSpinners(int currentMonth, int currentYear) {
   	
        //Initializes months spinner content
        monthsSpinner = (Spinner) findViewById(R.id.months_spinner);
        monthsSpinner.setOnItemSelectedListener(new MonthSpinnerListener());
        ArrayAdapter<CharSequence> monthsArrayAdapter = ArrayAdapter.createFromResource(this, R.array.months_array, android.R.layout.simple_spinner_item);
        monthsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthsSpinner.setAdapter(monthsArrayAdapter);
        monthsSpinner.setSelection(currentMonth); //sets selection to current month
        
        //Initializes years spinner content
        yearsSpinner = (Spinner) findViewById(R.id.years_spinner);
        yearsSpinner.setOnItemSelectedListener(new YearSpinnerListener());
        ArrayAdapter<CharSequence> yearsArrayAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        //Fills adapter with last 3 years
        for (int i = currentYear-2; i<=currentYear; i++){
        	yearsArrayAdapter.add(Integer.toString(i));
        }
        yearsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearsSpinner.setAdapter(yearsArrayAdapter);
        yearsSpinner.setSelection(2); //Sets selection to current year
        
    }
    
    /** Click events handler */
    public void onClick(View v){
    	switch (v.getId()) {
    	case R.id.new_expense_button:
    		Intent i = new Intent(this, NewExpenseActivity.class);
    		startActivity(i);
    		break;
    	}
    }
    
    /** Spinner selection event handler */
    public class YearSpinnerListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			int year = Integer.parseInt(yearsSpinner.getAdapter().getItem(pos).toString());
    		int month =monthsSpinner.getSelectedItemPosition();
			log = new SQLiteExpenseLog(view.getContext(), year, month, -1, -1);
        	initList();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// do nothing
		} 
    	
    }
    
    /** Spinner selection event handler */
    public class MonthSpinnerListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			int year = Integer.parseInt(yearsSpinner.getSelectedItem().toString());
    		int month = pos;
    		log = new SQLiteExpenseLog(view.getContext(), year, month, -1, -1);
        	initList();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// do nothing
		} 
    	
    }

}