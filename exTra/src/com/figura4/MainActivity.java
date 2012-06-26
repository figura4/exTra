package com.figura4;

import java.util.*;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class MainActivity extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        
        Map<String, Object> map = new HashMap<String, Object>();
    	map.put("type", "bolletta");
    	map.put("description", "elettrica");
        data.add(map);
    	
        String[] from = { "type", "description" };
        int[] views = { android.R.id.text1, android.R.id.text2 };
        
        ListAdapter adapter = new SimpleAdapter(this, data, android.R.layout.two_line_list_item, from, views);
        
        setListAdapter(adapter);
    }
}