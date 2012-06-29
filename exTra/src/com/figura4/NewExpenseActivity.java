package com.figura4;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class NewExpenseActivity extends FragmentActivity 
						implements OnClickListener, DatePickerDialog.OnDateSetListener {
	
	private Expense expense;   
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_expense);
        
        Calendar calendar = Calendar.getInstance();
        expense = new StandardExpense(-1, calendar.get(Calendar.YEAR), 
        								   calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), -1, "", new BigDecimal(0));

        //initialize type spinners
        initSpinners();
         
        updateDisplay();
    }

	@Override
	public void onClick(View v) {
    	switch (v.getId()) {
    	case R.id.new_expense_save_button:
    		// updates expense object
    		Spinner typeSpinner = (Spinner) findViewById(R.id.new_exp_type_spinner);
    		expense.setType(Integer.parseInt(typeSpinner.getSelectedItem().toString()));
    		EditText descriptionText = (EditText) findViewById(R.id.new_exp_description_text);
    		expense.setDescription(descriptionText.getText().toString());
    		EditText amountText = (EditText) findViewById(R.id.new_exp_amount_text);
    		BigDecimal amount = new BigDecimal(amountText.getText().toString());
    		expense.setAmount(amount);
    		
    		// creates log and passes it the expense object
    		ExpenseLogFactory factory = new SQLiteExpenseLogFactory();
    		ExpenseLog log = factory.createLog(this, 2000, 1, -1, -1);
    		log.newExpense(expense);
    		break;
    	}
	}
	
    /** initializes spinner */
    private void initSpinners() {
        //Initializes months spinner content
        Spinner typeSpinner = (Spinner) findViewById(R.id.new_exp_type_spinner);
        ArrayAdapter<CharSequence> typeArrayAdapter = ArrayAdapter.createFromResource(this, R.array.type_array, android.R.layout.simple_spinner_item);
        typeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeArrayAdapter);
    }
    
    // updates the views  
    private void updateDisplay() {  
    	Button pickDateButton = (Button) findViewById(R.id.new_exp_date_button);
    	SimpleDateFormat formattedDate = new SimpleDateFormat("dd MMMMM yyyy");
    	Calendar cal = Calendar.getInstance();
    	cal.set(expense.getYear(), expense.getMonth(), expense.getDay());
    	pickDateButton.setText(formattedDate.format(cal.getTime()));  
    }

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		
		expense.setYear(year);
		expense.setMonth(monthOfYear);
		expense.setDay(dayOfMonth);
		updateDisplay();
		
	}  
	
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "datePicker");
	}

}
