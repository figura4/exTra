package com.figura4.graph;

import java.util.Iterator;
import java.util.LinkedHashMap;
import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class PieChartManager {

	public Intent execute(Context context, LinkedHashMap<String, Integer> data) {
		int[] colors = new int[] { Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN, Color.CYAN };
		DefaultRenderer renderer = buildCategoryRenderer(colors);
		CategorySeries categorySeries = new CategorySeries("Expenses chart");
		String key; 
		Iterator<String> iterator = data.keySet().iterator();
		while (iterator.hasNext()) {
			key = iterator.next();
			categorySeries.add(key, data.get(key));
		}
		 
		return ChartFactory.getPieChartIntent(context, categorySeries, renderer, "Expenses pie chart");
	}
		 
	protected DefaultRenderer buildCategoryRenderer(int[] colors) {
		DefaultRenderer renderer = new DefaultRenderer();
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			r.setDisplayChartValues(true); // <-- not working!
			renderer.addSeriesRenderer(r);
		}
		return renderer;
	}
}
