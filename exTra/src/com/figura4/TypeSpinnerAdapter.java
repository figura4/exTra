package com.figura4;

import java.util.List;

import com.figura4.model.ExpenseType;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

/**
 * custom adapter for expense type spinner
 * 
 * @author figura4
 *
 */
public class TypeSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
	private List<ExpenseType> typeList;
	private LayoutInflater inflater; 
	
	public TypeSpinnerAdapter(Context context, List<ExpenseType> typeList){
		this.typeList = typeList;
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return typeList.size();
	}

	@Override
	public Object getItem(int position) {
		return typeList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    View view = convertView;
	    if(view == null) {
	        view = inflater.inflate(R.layout.type_spinner_layout, null);
	    }
	    
	    ExpenseType expenseType = typeList.get(position);
	    
	    TextView type = (TextView)view.findViewById(R.id.spinner_type_description);
	    type.setText(expenseType.getDescription());
	    
		return view;
	}

	

}
