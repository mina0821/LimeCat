package com.example.mina.limecatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;


public class SecondActivity extends Activity {
    public final static String MSG_KEY = "com.example.mina.limecatapplication.message_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String msg = intent.getStringExtra(MSG_KEY);
        TextView textView = new TextView(this);
        textView.setTextSize(45);
        textView.setText(msg);
        setContentView(textView);

        //setContentView(R.layout.second_layout);
    }

}
