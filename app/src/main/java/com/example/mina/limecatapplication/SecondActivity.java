package com.example.mina.limecatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;


public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get the user input message
        Intent intent = getIntent();
        String msg1 = intent.getStringExtra("DATE");
        String msg2 = intent.getStringExtra("Time");
        String msg3 = intent.getStringExtra("Model");
        //get the corresponding value in the dataset
        String result_temp = intent.getStringExtra("RESULT_TEMP");
        String result_preci = intent.getStringExtra("RESULT_PRECI");

        //formulize the whole string message
        String input_info = String.format("Input Information\nDate: %s\nTime Period: %s\nClimate Model: %s\n",msg1,msg2,msg3);
        String output_info = String.format("\nOutput Information\nMean temperature: %s\nPrecipitation: %s",result_temp,result_preci);

        //create a textview variable of current window
        TextView textView = new TextView(this);

        //set the size of the font
        textView.setTextSize(20);

        //send the message to view
        textView.setText(input_info+output_info);
        setContentView(textView);

        //setContentView(R.layout.second_layout);
    }

}
