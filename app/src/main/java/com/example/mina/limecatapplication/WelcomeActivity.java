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
    EditText msg_text;
    public final static String MSG_KEY = "Date";

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

        //only for test purpose
        textView.setText(list_preci.get(12)[1]);

        //start the activity
        startActivity(intent);

    }

}
