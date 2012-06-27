package com.figura4;

import java.util.*;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

public class MainActivity extends ListActivity implements OnClickListener {
	private ExpenseLog log;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        SQLiteExpenseLogFactory logFactory = new SQLiteExpenseLogFactory();
        log = logFactory.createLog(2012, 6, 0, 0);
        
        initList();
        
        initSpinners();
        
        View newExpenseButton = findViewById(R.id.new_expense_button);
        newExpenseButton.setOnClickListener(this);
    }
    
    private void initList() {
    	// initializes list view
        ArrayList<Map<String, Object>> data = log.getExpenses(); 
        
        String[] from = { "type", "subtype", "description", "date", "amount" };
        int[] views = { R.id.row_type, R.id.row_subtype, R.id.row_description, R.id.row_date, R.id.row_amount };
        
        ListAdapter adapter = new SimpleAdapter(this, data, R.layout.expenses_list_row, from, views);
        
        setListAdapter(adapter);
    }
    
    private void initSpinners() {
        //Initialize months spinner content
        Spinner monthsSpinner = (Spinner) findViewById(R.id.months_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> monthsArrayAdapter = ArrayAdapter.createFromResource(this, R.array.months_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        monthsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        monthsSpinner.setAdapter(monthsArrayAdapter);
        
        //Initialize years spinner content
        Spinner yearsSpinner = (Spinner) findViewById(R.id.years_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> yearsArrayAdapter = ArrayAdapter.createFromResource(this, R.array.years_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        yearsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        yearsSpinner.setAdapter(yearsArrayAdapter);
    }
    
    public void onClick(View v){
    	switch (v.getId()) {
    	case R.id.new_expense_button:
    		Intent i = new Intent(this, NewExpenseActivity.class);
    		startActivity(i);
    		break;
    	}
    }
}