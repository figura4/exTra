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
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initList();
        
        initSpinners();
        
        View newExpenseButton = findViewById(R.id.new_expense_button);
        newExpenseButton.setOnClickListener(this);
    }
    
    private void initList() {
    	// initializes list view
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