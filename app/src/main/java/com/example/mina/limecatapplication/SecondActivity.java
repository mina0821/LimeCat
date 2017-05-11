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
        //drawChart();
        //get the user input message
        Intent intent = getIntent();
        String msg1 = intent.getStringExtra("DATE");
        String msg3 = intent.getStringExtra("Model");
        //get the corresponding value in the dataset
        String result_temp = intent.getStringExtra("RESULT_TEMP");
        String result_preci = intent.getStringExtra("RESULT_PRECI");

        String[] temp_total = intent.getStringArrayExtra("RESULT_TEMP_LIST");
        String[] preci_total = intent.getStringArrayExtra("RESULT_PRECI_LIST");
        String[] temp_history = intent.getStringArrayExtra("RESULT_TEMP_History");
        String[] preci_history = intent.getStringArrayExtra("RESULT_PRECI_History");
        double[] temp_val = new double[20];
        double[] preci_val = new double[20];
        double[] temp_historyval = new double[20];
        double[] preci_historyval = new double[20];
        for(int i = 0;i<20;i++){
            temp_val[i]= Double.parseDouble(temp_total[i]);
            preci_val[i]= Double.parseDouble(preci_total[i]);
            temp_historyval[i]= Double.parseDouble(temp_history[i]);
            preci_historyval[i]= Double.parseDouble(preci_history[i]);
        }
        String temptotal = "";
        String precitotal ="";
        for(int i = 0;i<20;i++){
            temptotal = temptotal+temp_total[i]+"\n";
            precitotal = precitotal+preci_total[i]+"\n";
        }
        drawChart(temp_val,preci_val,temp_historyval,preci_historyval);
        //formulize the whole string message
        //String input_info = String.format("Input Information\nDate: %s\nTime Period: %s\nClimate Model: %s\n", msg1, msg2, msg3);
        //String output_info = String.format("\nOutput Information\nMean temperature: %s\nPrecipitation: %s", result_temp, result_preci);
        //String total_info = String.format("\n%s\n%s",temp_total,preci_total);
        //create a textview variable of current window
        //TextView textView = new TextView(this);

        //set the size of the font
        //textView.setTextSize(20);

        //send the message to view
        //textView.setText(temptotal+precitotal);
        //setContentView(textView);

        //setContentView(R.layout.second_layout);

        //Error waiting to be fixed
        //textView.setText("Description: This is the graph of data for ten years.");
    }
    private void drawChart(double[] temperature, double[] precipitation, double[] temperaturehistory, double[] precipitationhistory) {
        int[] x_values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,11,12,13,14,15,16,17,18,19,20};
        double[] y_values = temperature;
        double[] y2_values = precipitation;
        double[] y3_values = temperaturehistory;
        double[] y4_values = precipitationhistory;

        //create two lines
        XYSeries tempSeries = new XYSeries("Temp");
        for (int i = 0; i < x_values.length; i++) {
            tempSeries.add(x_values[i], y_values[i]);
        }
        XYSeries preciSeries = new XYSeries("Preci");
        for (int i = 0; i < x_values.length; i++) {
            preciSeries.add(x_values[i], y2_values[i]);
        }

        XYSeries temphistorySeries = new XYSeries("Temphistory");
        for (int i = 0; i < x_values.length; i++) {
            temphistorySeries.add(x_values[i], y3_values[i]);
        }
        XYSeries precihistorySeries = new XYSeries("Precihistory");
        for (int i = 0; i < x_values.length; i++) {
            precihistorySeries.add(x_values[i], y4_values[i]);
        }

        XYMultipleSeriesDataset Dataset = new XYMultipleSeriesDataset();
        Dataset.addSeries(tempSeries);
        Dataset.addSeries(preciSeries);
        Dataset.addSeries(temphistorySeries);
        Dataset.addSeries(precihistorySeries);

        //define the first line features
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setColor(Color.RED);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setFillPoints(true);
        renderer.setLineWidth(3);
        renderer.setDisplayChartValues(true);
        renderer.setChartValuesTextSize(30);

        //define the second line features
        XYSeriesRenderer renderer2 = new XYSeriesRenderer();
        renderer2.setColor(Color.BLUE);
        renderer2.setPointStyle(PointStyle.CIRCLE);
        renderer2.setFillPoints(true);
        renderer2.setLineWidth(3);
        renderer2.setDisplayChartValues(true);
        renderer2.setChartValuesTextSize(30);

        XYSeriesRenderer renderer3 = new XYSeriesRenderer();
        renderer3.setColor(Color.YELLOW);
        renderer3.setPointStyle(PointStyle.CIRCLE);
        renderer3.setFillPoints(true);
        renderer3.setLineWidth(3);
        renderer3.setDisplayChartValues(true);
        renderer3.setChartValuesTextSize(30);

        XYSeriesRenderer renderer4 = new XYSeriesRenderer();
        renderer4.setColor(Color.WHITE);
        renderer4.setPointStyle(PointStyle.CIRCLE);
        renderer4.setFillPoints(true);
        renderer4.setLineWidth(3);
        renderer4.setDisplayChartValues(true);
        renderer4.setChartValuesTextSize(30);

        //combine two liens and set feature for the whole
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(20);
        multiRenderer.setLabelsTextSize(36);
        multiRenderer.setChartTitle("Temp/Precipitation Chart");
        multiRenderer.setChartTitleTextSize(72);
        multiRenderer.setAxisTitleTextSize(48);
        multiRenderer.setXTitle("20 year period");
        multiRenderer.setYTitle("Temp/Precipitation");
        multiRenderer.setZoomButtonsVisible(true);
        multiRenderer.addSeriesRenderer(renderer);
        multiRenderer.addSeriesRenderer(renderer2);
        multiRenderer.addSeriesRenderer(renderer3);
        multiRenderer.addSeriesRenderer(renderer4);

        //graph number one lauyout defined
        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart_container);
        View chart = ChartFactory.getLineChartView(getBaseContext(), Dataset, multiRenderer);
        chartContainer.addView(chart);

    }



    }


