package com.figura4;

import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class PieChartActivity extends Activity {
	  private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN };
	  private CategorySeries mSeries = new CategorySeries("");
	  private DefaultRenderer mRenderer = new DefaultRenderer();
	  private String mDateFormat;
	  private Button mAdd;
	  private EditText mX;
	  private GraphicalView mChartView;
	  
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  //setContentView(R.layout.xy_chart);
		  //mX = (EditText) findViewById(R.id.xValue);
		  mRenderer.setApplyBackgroundColor(true);
		  mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
		  mRenderer.setChartTitleTextSize(20);
		  mRenderer.setLabelsTextSize(15);
		  mRenderer.setLegendTextSize(15);
		  mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
		  mRenderer.setZoomButtonsVisible(true);
		  mRenderer.setStartAngle(90);
	  }
}
