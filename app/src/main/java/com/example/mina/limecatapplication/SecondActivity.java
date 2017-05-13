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

import java.io.FileReader;

import au.com.bytecode.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SecondActivity extends Activity {
    //define textview and msg variables
    TextView textView;
    double[] list_val;
    double[] list_future;
    String type;
    String avg;
    String avg_future;
    List<String[]> list_avg = new ArrayList<String[]>();
    LinearLayout layout1;
    LinearLayout layout2;
    LinearLayout layout3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);

        layout1 = (LinearLayout) findViewById(R.id.chart_future);
        layout2 = (LinearLayout) findViewById(R.id.chart_compare);
        layout3 = (LinearLayout) findViewById(R.id.chart_present);

        //get the user input message
        Intent intent = getIntent();
        String[] list_total = intent.getStringArrayExtra("RESULT_LIST");
        String[] list_future_total = intent.getStringArrayExtra("RESULT_FUTURE_LIST");
        avg = intent.getStringExtra("AVG");
        avg_future = intent.getStringExtra("AVG_FUTURE");
        type = intent.getStringExtra("TYPE");

        //parse the whole list into double list
        list_val = new double[7];
        list_future = new double[7];
        for(int i = 0;i<7;i++){
            String val = list_total[i];
            String future = list_future_total[i];

            list_val[i]= Double.parseDouble(val);
            list_future[i]=Double.parseDouble(future);
        }

        //default setting: draw the comparision line
        //draw2Chart(list_val,list_future,layout2);
    }



    public void showPresent(View view){
        //let another two layout disappear
        layout1.setVisibility(View.INVISIBLE);
        layout2.setVisibility(View.INVISIBLE);
        //draw chart
        layout3.setVisibility(View.VISIBLE);
        drawChart(list_val,layout3,Color.RED);


        //locate the textview box we want to show the result
        textView = (TextView)findViewById(R.id.info);

        //create a string to display
        String msg_type="Description:\n This is the graph of "+type;
        String msg_graph=" in current time frame";
        String msg_avg="\n The average of "+type+" this month in current is "+avg;
        textView.setText(msg_type+msg_graph+msg_avg);
    }

    public void showFuture(View view){
        //let another two layout disappear
        layout2.setVisibility(View.INVISIBLE);
        layout3.setVisibility(View.INVISIBLE);
        //draw chart
        layout1.setVisibility(View.VISIBLE);
        drawChart(list_future,layout1,Color.BLUE);

        //create a string to display
        String msg_type="Description:\n This is the graph of "+type;
        String msg_graph=" in future time frame";
        String msg_avg="\n The average of "+type+" this month in future is "+avg_future;
        textView.setText(msg_type+msg_graph+msg_avg);
    }

    public void showCompare(View view){
        //let another two layout disappear
        layout1.setVisibility(View.INVISIBLE);
        layout3.setVisibility(View.INVISIBLE);
        //draw chart
        layout2.setVisibility(View.VISIBLE);
        draw2Chart(list_val,list_future,layout2);

        //create a string to display
        String msg_type="Description:\n This is the graph of "+type;
        String msg_graph=" in both time frame";
        String msg_avg="\n The average of "+type+" this month in cuerrent is "+avg;
        String msg_2 = "\n The average of "+type+" this month in future is "+avg_future;
        textView.setText(msg_type+msg_graph+msg_avg+msg_2);
    }


    private void drawChart(double[] lst, LinearLayout lay,int cl) {
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
        renderer.setColor(cl);
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
        multiRenderer.setShowGrid(true);

        //graph number one lauyout defined
        View chart = ChartFactory.getLineChartView(getBaseContext(), Dataset, multiRenderer);
        lay.addView(chart);

    }

    private void draw2Chart(double[] lst, double[] lst2, LinearLayout lay) {
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
        multiRenderer.setShowGrid(true);

        //graph number one lauyout defined
        View chart = ChartFactory.getLineChartView(getBaseContext(), Dataset, multiRenderer);
        lay.addView(chart);

    }



    }


