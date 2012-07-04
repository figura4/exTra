package com.figura4;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * handles the app configuration parameters
 * 
 * @author figura4
 *
 */
public class PreferencesManager {
	// xml file
	private static final String APP_SHARED_PREFS = "com.figura4.general_preferences";
	
	// data source
	private static final String DATA_SOURCE = "data_source";
    public static final int SQLITE = 1;
    public static final int WEB_SERVICE = 2;
    
	private SharedPreferences sharedPrefs;
    private Editor prefsEditor;
	
	public PreferencesManager(Context context){
		sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        prefsEditor = sharedPrefs.edit();
	}
	
	public int getDataSource() {
		if (!sharedPrefs.contains(DATA_SOURCE)) {
			setDataSource(SQLITE);	
		}
		return sharedPrefs.getInt(DATA_SOURCE, WEB_SERVICE); 
    }
	
	public void setDataSource(int dataSource) {
        prefsEditor.putInt(DATA_SOURCE, SQLITE);
        prefsEditor.commit();
    }
}
