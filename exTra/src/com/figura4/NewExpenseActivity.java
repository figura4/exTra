package com.figura4;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.figura4.SQLite.SQLiteExpenseLog;
import com.figura4.SQLite.SQLiteExpenseTypeList;
import com.figura4.model.Expense;
import com.figura4.model.ExpenseLog;
import com.figura4.model.ExpenseType;
import com.figura4.model.ExpenseTypeList;
import com.figura4.model.StandardExpense;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * activity used to create a new expense entry
 * 
 * @author figura4
 *
 */
public class NewExpenseActivity extends FragmentActivity 
						         implements OnClickListener, DatePickerDialog.OnDateSetListener {
	
	private Expense expense;   
	private ExpenseTypeList typeList;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_expense);
        
        // initializing expense and expense type list
        Calendar calendar = Calendar.getInstance();
        typeList = new SQLiteExpenseTypeList(this);
        expense = new StandardExpense(-1, calendar.get(Calendar.YEAR), 
        								   calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), typeList.getType(1), "", new BigDecimal(0));

        //initialize type spinners
        initSpinners();
        
        //Sets button click listener
        View newExpenseButton = findViewById(R.id.new_expense_save_button);
        newExpenseButton.setOnClickListener(this);
     
        updateDisplay();
    }

	@Override
	public void onClick(View v) {
    	switch (v.getId()) {
    	case R.id.new_expense_save_button:
    		// updates expense object
    		Spinner typeSpinner = (Spinner) findViewById(R.id.new_exp_type_spinner);
    		expense.setType((ExpenseType)typeSpinner.getSelectedItem());
    		EditText descriptionText = (EditText) findViewById(R.id.new_exp_description_text);
    		expense.setDescription(descriptionText.getText().toString());
    		EditText amountText = (EditText) findViewById(R.id.new_exp_amount_text);
    		String amountString = amountText.getText().toString();
    		amountString = amountString.replace(",", ".");
    		amountString = amountString.replaceAll("[^.\\d]", "");
    		BigDecimal amount = new BigDecimal(amountString);
    		expense.setAmount(amount);
    		
    		// creates log and passes it the expense object
    		ExpenseLog log = new SQLiteExpenseLog(this, 2000, 1, -1, -1);
    		log.newExpense(expense);
    		this.finish();
    		break;
    	}
	}
	
    /** initializes spinners */
    private void initSpinners() {
        //Initializes months spinner content
        Spinner typeSpinner = (Spinner) findViewById(R.id.new_exp_type_spinner);
        //ArrayAdapter<CharSequence> typeArrayAdapter = ArrayAdapter.createFromResource(this, R.array.type_array, android.R.layout.simple_spinner_item);
        TypeSpinnerAdapter typeSpinnerAdapter = new TypeSpinnerAdapter(this, typeList.getTypes());
        //TypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeSpinnerAdapter);
    }
    
    // updates the views  
    private void updateDisplay() { 
    	// date button
    	Button pickDateButton = (Button) findViewById(R.id.new_exp_date_button);
    	SimpleDateFormat formattedDate = new SimpleDateFormat("dd MMMMM yyyy");
    	Calendar cal = Calendar.getInstance();
    	cal.set(expense.getYear(), expense.getMonth(), expense.getDay());
    	pickDateButton.setText(formattedDate.format(cal.getTime()));  
    }

    /**
     * when a new date is set with DatePicker,
     * update the expense values and
     * the text in date button
     */
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
