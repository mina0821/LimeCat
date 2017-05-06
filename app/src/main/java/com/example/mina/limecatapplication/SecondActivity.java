package com.example.mina.limecatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;


public class SecondActivity extends Activity {
    public final static String MSG_KEY = "Date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get the message
        Intent intent = getIntent();
        String msg = intent.getStringExtra(MSG_KEY);
        String msg2 = intent.getStringExtra("Time");
        String msg3 = intent.getStringExtra("Model");

        //create a textview variable of current window
        TextView textView = new TextView(this);

        //set the size of the font
        textView.setTextSize(45);

        //send the message to view
        textView.setText(msg+"\n"+msg2+"\n"+msg3);
        setContentView(textView);

        //setContentView(R.layout.second_layout);
    }

}
