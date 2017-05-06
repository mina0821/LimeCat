package com.example.mina.limecatapplication;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.*;


public class WelcomeActivity extends AppCompatActivity {
    TextView textView;
    EditText msg_text;
    public final static String MSG_KEY = "Date";

    private RadioGroup radioTimeGroup;
    private RadioGroup radioModelGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //locate the textview box we want to show the result
        textView = (TextView)findViewById(R.id.selectTime);

        //add a listener to the radio button group
        addListenerOnButton();
    }

    public void addListenerOnButton(){
        radioTimeGroup = (RadioGroup) findViewById(R.id.timeGroup);
        radioModelGroup = (RadioGroup) findViewById(R.id.modelGroup);

        //attach listener to radio group
        radioTimeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //get the right button which is clicked
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1){
                    Toast.makeText(WelcomeActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //attach listener to radio group
        radioModelGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //get the right button which is clicked
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1){
                    Toast.makeText(WelcomeActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //after we hit show button
    public void sendMessage(View view){
        //store the msg in the text box
        msg_text = (EditText)findViewById(R.id.msg_text);
        String msg = msg_text.getText().toString();

        //call second window
        Intent intent = new Intent(this,SecondActivity.class);
        //input the msg we get
        intent.putExtra(MSG_KEY,msg);

        //get the selection of time period
        RadioButton rb = (RadioButton) radioTimeGroup.findViewById(radioTimeGroup.getCheckedRadioButtonId());
        String time = (String) rb.getText();

        //get the selection of climate model
        RadioButton rb2 = (RadioButton) radioModelGroup.findViewById(radioModelGroup.getCheckedRadioButtonId());
        String model = (String) rb2.getText();

        //input the selection we get
        intent.putExtra("Time",time);
        intent.putExtra("Model",model);

        //start the activity
        startActivity(intent);

    }

}
