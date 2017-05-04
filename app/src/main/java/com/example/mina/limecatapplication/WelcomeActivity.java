package com.example.mina.limecatapplication;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.*;


public class WelcomeActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        textView = (TextView) findViewById(R.id.hell_text_view);
    }

    public void showHell(View view){
        String msg = "This is hell";
        textView.setText(msg);
    }

}
