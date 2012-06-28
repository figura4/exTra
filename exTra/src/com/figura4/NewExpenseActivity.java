package com.figura4;

import java.math.BigDecimal;
import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

public class NewExpenseActivity extends Activity implements OnClickListener {
	private Expense expense; 
    static final int DATE_DIALOG_ID = 0;  
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_expense);
        
        Calendar calendar = Calendar.getInstance();
        expense = new StandardExpense(-1, calendar.get(Calendar.YEAR), 
        								   calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), -1, "", new BigDecimal(0));

        
        //initialize type spinners
        initSpinners();
         
        Button pickDateButton = (Button) findViewById(R.id.new_exp_date_button);
        pickDateButton.setOnClickListener(this);  
        updateDisplay();
    }

	@Override
	public void onClick(View v) {
    	switch (v.getId()) {
    	case R.id.new_expense_save_button:
    		ExpenseLogFactory factory = new SQLiteExpenseLogFactory();
    		ExpenseLog log = factory.createLog(2000, 1, -1, -1);
    		log.newExpense(expense);
    		break;
    		
    	case R.id.new_exp_date_button:
    		showDialog(DATE_DIALOG_ID);
    	}
		
	}
	
	// the callback received when the user "sets" the date in the dialog  
    private DatePickerDialog.OnDateSetListener mDateSetListener =  
            new DatePickerDialog.OnDateSetListener() {  
  
                public void onDateSet(DatePicker view, int year,   
                                      int monthOfYear, int dayOfMonth) {  
                    expense.setYear(year);  
                    expense.setMonth(monthOfYear);  
                    expense.setDay(dayOfMonth);  
                    updateDisplay();  
                }  
            };  
              
    @Override  
    protected Dialog onCreateDialog(int id) {  
        switch (id) {  
        case DATE_DIALOG_ID:  
            return new DatePickerDialog(this, mDateSetListener, expense.getYear(), expense.getMonth(),  
                    expense.getDay());  
        }  
        return null;  
    }  
	
    /** initializes spinner */
    private void initSpinners() {
        //Initializes months spinner content
        Spinner typeSpinner = (Spinner) findViewById(R.id.new_exp_type_spinner);
        ArrayAdapter<CharSequence> typeArrayAdapter = ArrayAdapter.createFromResource(this, R.array.type_array, android.R.layout.simple_spinner_item);
        typeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeArrayAdapter);
    }
    
    // updates the date in the TextView  
    private void updateDisplay() {  
    	Button pickDateButton = (Button) findViewById(R.id.new_exp_date_button);
    	StringBuilder date = new StringBuilder().append(expense.getDay())
    			                         .append("/")  
                						 .append(expense.getMonth()) 
                						 .append("/")  
                                         .append(expense.getYear()); 
    	
    	pickDateButton.setText(date.toString());  
    }  

}
