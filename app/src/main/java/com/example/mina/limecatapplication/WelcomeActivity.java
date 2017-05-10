package com.example.mina.limecatapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.*;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;


public class WelcomeActivity extends AppCompatActivity {
    //define textview and msg variables
    TextView textView;
    EditText msg_text_date;
    EditText msg_text_month;
    EditText msg_text_year;

    //define radio button variables
    private RadioGroup radioTimeGroup;
    private RadioGroup radioModelGroup;

    //define firebase variables
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://limecatapplication.appspot.com/").child("meanTemp.csv");
    StorageReference storageRef2 = storage.getReferenceFromUrl("gs://limecatapplication.appspot.com/").child("precipitation.csv");

    //define variables to store the cloud dataset
    String next[] = {};
    List<String[]> list_temp = new ArrayList<String[]>();
    List<String[]> list_preci = new ArrayList<String[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //locate the textview box we want to show the result
        textView = (TextView)findViewById(R.id.selectTime);

        //add a listener to the radio button group
        addListenerOnButton();

        //read the file meanTemp.csv store in filebase
        readfile();

        //read the file precipication.csv store in the firebase
        readAnotherFile();
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

    public void readfile(){
        try {
            final File localFile = File.createTempFile("temp", "csv");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    //read the file temp.csv
                    try{
                        CSVReader reader = new CSVReader(new FileReader(localFile));
                        //skip the first two line
                        next = reader.readNext();next = reader.readNext();
                        while(true) {
                            next = reader.readNext();
                            if(next != null) {
                                list_temp.add(next);
                            } else{
                                break;
                            }
                        }
                    } catch (IOException e) {
                        //display error message
                        textView.setText("File not found");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //do nothing
                }
            });
        } catch (IOException e){
            //do nothing
        }
    }

    public void readAnotherFile(){
        try {
            final File localFile = File.createTempFile("temp", "csv");
            storageRef2.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    //read the file temp.csv
                    try{
                        CSVReader reader = new CSVReader(new FileReader(localFile));
                        //skip the first two line
                        next = reader.readNext();next = reader.readNext();
                        while(true) {
                            next = reader.readNext();
                            if(next != null) {
                                list_preci.add(next);
                            } else{
                                break;
                            }
                        }
                    } catch (IOException e) {
                        //display error message
                        textView.setText("File not found");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //do nothing
                }
            });
        } catch (IOException e){
            //do nothing
        }
    }

    //after we hit show button
    public void sendMessage(View view){
        //store the msg into a string variable
        msg_text_date = (EditText)findViewById(R.id.msg_text_date);
        String msg_date = msg_text_date.getText().toString();

        msg_text_month = (EditText)findViewById(R.id.msg_text_month);
        String msg_month = msg_text_month.getText().toString();

        msg_text_year = (EditText)findViewById(R.id.msg_text_year);
        String msg_year = msg_text_year.getText().toString();

        //combine all the date information
        String msg = msg_date+"-"+msg_month+"-"+msg_year;

        //create a string info for 10 years after the selected year
        String[] msg10 = new String[10];
        for(int i=0;i<10;i++){
            //add the String of date
            msg10[i]=msg_date+"-"+msg_month+"-"+(Integer.parseInt(msg_year)+i);
        }

        //call second window
        Intent intent = new Intent(this,SecondActivity.class);
        //input the msg we get
        intent.putExtra("DATE",msg);

        //get the selection of time period
        RadioButton rb = (RadioButton) radioTimeGroup.findViewById(radioTimeGroup.getCheckedRadioButtonId());
        String time = (String) rb.getText();

        //get the selection of climate model
        RadioButton rb2 = (RadioButton) radioModelGroup.findViewById(radioModelGroup.getCheckedRadioButtonId());
        String model = (String) rb2.getText();

        //input the selection we get
        intent.putExtra("Time",time);
        intent.putExtra("Model",model);

        //use FindVal module to find the corresponding value
        FindVal find = new FindVal(list_temp,list_preci);
        String r1 = find.searchTemp(msg,time,model);
        String r2 = find.searchPreci(msg,time,model);
        //forward to second activity
        intent.putExtra("RESULT_TEMP",r1);
        intent.putExtra("RESULT_PRECI",r2);

        String[] tempstring = new String[10];
        String[] precistring = new String[10];
        //String temptotal = null;
        //String precitotal = null;
        for(int i =0;i<10;i++){
            tempstring[i] = find.searchTemp(msg10[i],time,model);
            precistring[i] = find.searchPreci(msg10[i],time,model);
            //temptotal = temptotal+"\n"+tempstring[i];
            //precitotal = precitotal+"\n"+precistring[i];
        }

        intent.putExtra("RESULT_TEMP_LIST",tempstring);
        intent.putExtra("RESULT_PRECI_LIST",precistring);
        //only for test purpose
        //textView.setText(r);

        //start the activity
        startActivity(intent);

    }

}
