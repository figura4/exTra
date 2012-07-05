package com.figura4;

import java.text.NumberFormat;
import java.util.*;

import com.figura4.graph.PieChartManager;
import com.figura4.model.Expense;
import com.figura4.model.ExpenseLog;
import com.figura4.model.ResourceFactory;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * activity displayed on app launch
 * 
 * @author figura4
 *
 */
public class MainActivity extends ListActivity implements OnClickListener {
	private ResourceFactory factory;
	private ExpenseLog log;	 // current expense log
	private Spinner monthsSpinner;
	private Spinner yearsSpinner;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        factory = new ResourceFactory(this);
        
        // getting current month/year
    	Calendar calendar = Calendar.getInstance();
    	int currentMonth = calendar.get(Calendar.MONTH);
    	int currentYear = calendar.get(Calendar.YEAR);
        
    	// creating Expense log
        log = factory.getLog(this, currentYear, currentMonth, null);
        
        // initializing views
        //initList();
        initSpinners(currentMonth, currentYear);
        
        // setting button click listener
        View newExpenseButton = findViewById(R.id.new_expense_button);
        newExpenseButton.setOnClickListener(this);
        
        View pieChartButton = findViewById(R.id.pie_chart_button);
        pieChartButton.setOnClickListener(this);
        
        registerForContextMenu(this.getListView());
    }
    
    protected void onResume() {
    	super.onResume();
    	updateView();
    }
    
    private void updateView() {
		int year = Integer.parseInt(yearsSpinner.getSelectedItem().toString());
		int month =monthsSpinner.getSelectedItemPosition();
		log = factory.getLog(this, year, month, null);
    	initList();
    	
    	TextView total = (TextView)this.findViewById(R.id.month_total);
        total.setText("Totale mensile: " + NumberFormat.getCurrencyInstance().format(log.getTotalamount()));
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
    		
    	case R.id.pie_chart_button:
    		Intent achartIntent = new PieChartManager().execute(this, log.getAmountsByType());
    		startActivity(achartIntent);
    	}
    }
    
    /** Spinner selection event handler */
    public class YearSpinnerListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			int year = Integer.parseInt(yearsSpinner.getAdapter().getItem(pos).toString());
    		int month =monthsSpinner.getSelectedItemPosition();
			log = factory.getLog(view.getContext(), year, month, null);
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
    		log = factory.getLog(view.getContext(), year, month, null);
        	initList();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// do nothing
		} 
    	
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
      if (v.getId()==this.getListView().getId()) {
        menu.setHeaderTitle("Gestisci spesa");
        String[] menuItems = getResources().getStringArray(R.array.expense_context_menu_elements);
        for (int i = 0; i<menuItems.length; i++) {
          menu.add(Menu.NONE, i, i, menuItems[i]);
        }
      }
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {  
    	AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
    	Expense expToDelete = (Expense)this.getListView().getItemAtPosition(info.position);
    	log.deleteExpense(expToDelete);
    	updateView();
    	return true;
    }

}