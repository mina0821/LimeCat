package com.example.mina.limecatapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;


public class SecondActivity extends Activity {
    //define textview and msg variables
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //locate the textview box we want to show the result
        textView = (TextView)findViewById(R.id.info);

        setContentView(R.layout.second_layout);
        //get the user input message
        Intent intent = getIntent();
        String[] list_total = intent.getStringArrayExtra("RESULT_LIST");
        String[] list_future_total = intent.getStringArrayExtra("RESULT_FUTURE_LIST");

        //parse the whole list into double list
        double[] list_val = new double[7];
        double[] list_future = new double[7];
        for(int i = 0;i<7;i++){
            list_val[i]= Double.parseDouble(list_total[i]);
            list_future[i]=Double.parseDouble(list_future_total[i]);
        }

        draw2Chart(list_future,list_val);
    }
    private void drawChart(double[] lst) {
        int[] x_values = {1, 2, 3, 4, 5, 6, 7};
        double[] y_values = lst;

        //create a line
        XYSeries tempSeries = new XYSeries("Value");
        for (int i = 0; i < x_values.length; i++) {
            tempSeries.add(x_values[i], y_values[i]);
        }

        XYMultipleSeriesDataset Dataset = new XYMultipleSeriesDataset();
        Dataset.addSeries(tempSeries);

        //define the first line features
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setColor(Color.RED);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setFillPoints(true);
        renderer.setLineWidth(3);
        renderer.setDisplayChartValues(true);
        renderer.setChartValuesTextSize(30);

        //combine two liens and set feature for the whole
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(10);
        multiRenderer.setLabelsTextSize(36);
        multiRenderer.setChartTitle("Following seven days");
        multiRenderer.setChartTitleTextSize(72);
        multiRenderer.setAxisTitleTextSize(48);
        multiRenderer.setXTitle("7 days period");
        multiRenderer.setYTitle("Value");
        multiRenderer.setZoomButtonsVisible(true);
        multiRenderer.addSeriesRenderer(renderer);

        //graph number one lauyout defined
        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart_container);
        View chart = ChartFactory.getLineChartView(getBaseContext(), Dataset, multiRenderer);
        chartContainer.addView(chart);

    }

    private void draw2Chart(double[] lst, double[] lst2) {
        int[] x_values = {1, 2, 3, 4, 5, 6, 7};
        double[] y_values = lst;
        double[] y2_values = lst2;

        //create a line
        XYSeries tempSeries = new XYSeries("Value");
        for (int i = 0; i < x_values.length; i++) {
            tempSeries.add(x_values[i], y_values[i]);
        }

        //create a line
        XYSeries tempSeries2 = new XYSeries("Value2");
        for (int i = 0; i < x_values.length; i++) {
            tempSeries2.add(x_values[i], y2_values[i]);
        }

        XYMultipleSeriesDataset Dataset = new XYMultipleSeriesDataset();
        Dataset.addSeries(tempSeries);
        Dataset.addSeries(tempSeries2);

        //define the first line features
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setColor(Color.RED);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setFillPoints(true);
        renderer.setLineWidth(3);
        renderer.setDisplayChartValues(true);
        renderer.setChartValuesTextSize(30);

        //define the first line features
        XYSeriesRenderer renderer2 = new XYSeriesRenderer();
        renderer2.setColor(Color.BLUE);
        renderer2.setPointStyle(PointStyle.CIRCLE);
        renderer2.setFillPoints(true);
        renderer2.setLineWidth(3);
        renderer2.setDisplayChartValues(true);
        renderer2.setChartValuesTextSize(30);

        //combine two liens and set feature for the whole
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(10);
        multiRenderer.setLabelsTextSize(36);
        multiRenderer.setChartTitle("Following seven days");
        multiRenderer.setChartTitleTextSize(72);
        multiRenderer.setAxisTitleTextSize(48);
        multiRenderer.setXTitle("7 days period");
        multiRenderer.setYTitle("Value");
        multiRenderer.setZoomButtonsVisible(true);
        multiRenderer.addSeriesRenderer(renderer);
        multiRenderer.addSeriesRenderer(renderer2);

        //graph number one lauyout defined
        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart_container);
        View chart = ChartFactory.getLineChartView(getBaseContext(), Dataset, multiRenderer);
        chartContainer.addView(chart);

    }



    }


