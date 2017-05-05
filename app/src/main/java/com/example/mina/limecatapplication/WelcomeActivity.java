package com.example.mina.limecatapplication;

import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.*;


public class WelcomeActivity extends AppCompatActivity {
    TextView textView;
    EditText msg_text;
    public final static String MSG_KEY = "com.example.mina.limecatapplication.message_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        textView = (TextView) findViewById(R.id.hell_text_view);
    }

    public void showHell(View view){
        String button_text = ((Button) view).getText().toString();

        if (button_text.equals("Open Window...")){
            Intent intent = new Intent(this,ThirdActivity.class);
            startActivity(intent);
        }
        textView.setText(button_text);
    }

    public void sendMessage(View view){
        msg_text = (EditText)findViewById(R.id.msg_text);
        String msg = msg_text.getText().toString();
        Intent intent = new Intent(this,SecondActivity.class);
        intent.putExtra(MSG_KEY,msg);
        startActivity(intent);
    }

}
