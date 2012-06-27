package com.figura4;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SQLiteExpenseLog implements ExpenseLog {

	public int newExpense(Expense expense){
		return 1;
	}
	
	public void deleteExpense(Expense expense) {
		
	}
	
	public BigDecimal getTotalamount() {
		return new BigDecimal("1");
	}
	
	public ArrayList<Map<String, Object>> getExpenses() {
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
        
        return data;
	}

}
