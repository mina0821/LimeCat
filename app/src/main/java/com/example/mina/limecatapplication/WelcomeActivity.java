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

    //define firebase variables
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://limecatapplication.appspot.com/").child("meanTemp_modelaverage.csv");
    StorageReference storageRef2 = storage.getReferenceFromUrl("gs://limecatapplication.appspot.com/").child("precipitation_modelaverage.csv");
    StorageReference storageRef3 = storage.getReferenceFromUrl("gs://limecatapplication.appspot.com/").child("Temp_monthaverage.csv");
    StorageReference storageRef4 = storage.getReferenceFromUrl("gs://limecatapplication.appspot.com/").child("precipitation_monthaverage.csv");

    //define variables to store the cloud dataset
    String next[] = {};
    List<String[]> list_temp = new ArrayList<String[]>();
    List<String[]> list_preci = new ArrayList<String[]>();
    List<String[]> list_avg_temp = new ArrayList<String[]>();
    List<String[]> list_avg_preci = new ArrayList<String[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //locate the textview box we want to show the result
        textView = (TextView)findViewById(R.id.datemsg);

        //read the file meanTemp.csv store in filebase
        readfile();

        //read the file precipication.csv store in the firebase
        readAnotherFile();

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

        try {
            final File localFile = File.createTempFile("temp2", "csv");
            storageRef3.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    //read the file temp.csv
                    try{
                        CSVReader reader = new CSVReader(new FileReader(localFile));
                        while(true) {
                            next = reader.readNext();
                            if(next != null) {
                                list_avg_temp.add(next);
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

        try {
            final File localFile = File.createTempFile("temp2", "csv");
            storageRef4.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    //read the file temp.csv
                    try{
                        CSVReader reader = new CSVReader(new FileReader(localFile));
                        while(true) {
                            next = reader.readNext();
                            if(next != null) {
                                list_avg_preci.add(next);
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
    public void sendTemp(View view){
        //store the msg into a string variable
        msg_text_date = (EditText)findViewById(R.id.msg_text_date);
        String msg_date = msg_text_date.getText().toString();

        msg_text_month = (EditText)findViewById(R.id.msg_text_month);
        String msg_month = msg_text_month.getText().toString();
        //bug fixed: 1 represents 01 in the dataset
        String msg_month_zero = msg_month;
        if (Integer.parseInt(msg_month)<10){
            msg_month_zero = "0"+msg_month;
        }

        msg_text_year = (EditText)findViewById(R.id.msg_text_year);
        String msg_year = msg_text_year.getText().toString();

        //create a string info for 7 days after the selected date
        String[] msg7 = new String[7];
        for(int i=0;i<7;i++){
            //add the String of date
            msg7[i]=(Integer.parseInt(msg_date)+i)+"-"+msg_month_zero+"-"+msg_year;
        }

        //predict the data for future
        //get the differences between selected year and 1986
        Integer diff = Integer.parseInt(msg_year) - 1986;
        //create an array to store the future data
        String[] msg7_future =  new String[7];
        for (int i=0; i<7; i++){
            msg7_future[i]=(Integer.parseInt(msg_date)+i)+"-"+msg_month_zero+"-"+(2040+diff);
        }

        //call second window
        Intent intent = new Intent(this,SecondActivity.class);

        //use FindVal module to find the corresponding value
        FindVal find = new FindVal(list_temp,list_preci);

        String[] presentstring = new String[7];
        String[] futurestring = new String[7];
        for(int i =0;i<7;i++){
            presentstring[i] = find.searchTemp(msg7[i]);
            futurestring[i] = find.searchTemp(msg7_future[i]);
        }

        intent.putExtra("RESULT_LIST",presentstring);
        intent.putExtra("RESULT_FUTURE_LIST",futurestring);
        intent.putExtra("TYPE","mean temperature");
        intent.putExtra("AVG",findAvgTemp(msg_month,msg_year));
        intent.putExtra("AVG_FUTURE",findAvgTemp(msg_month,Integer.toString(2040+diff)));
        //only for test purpose
        //textView.setText(r);


        //start the activity
        startActivity(intent);

    }

    //after we hit show button
    public void sendPreci(View view){
        //store the msg into a string variable
        msg_text_date = (EditText)findViewById(R.id.msg_text_date);
        String msg_date = msg_text_date.getText().toString();

        msg_text_month = (EditText)findViewById(R.id.msg_text_month);
        String msg_month = msg_text_month.getText().toString();
        //bug fixed: 1 represents 01 in the dataset
        String msg_month_zero=msg_month;
        if (Integer.parseInt(msg_month)<10){
            msg_month_zero = "0"+msg_month;
        }

        msg_text_year = (EditText)findViewById(R.id.msg_text_year);
        String msg_year = msg_text_year.getText().toString();

        //create a string info for 7 days after the selected date
        String[] msg7 = new String[7];
        for(int i=0;i<7;i++){
            //add the String of date
            msg7[i]=(Integer.parseInt(msg_date)+i)+"-"+msg_month_zero+"-"+msg_year;
        }

        //predict the data for future
        //get the differences between selected year and 1986
        Integer diff = Integer.parseInt(msg_year) - 1986;
        //create an array to store the future data
        String[] msg7_future =  new String[7];
        for (int i=0; i<7; i++){
            msg7_future[i]=(Integer.parseInt(msg_date)+i)+"-"+msg_month_zero+"-"+(2040+diff);
        }

        //call second window
        Intent intent = new Intent(this,SecondActivity.class);

        //use FindVal module to find the corresponding value
        FindVal find = new FindVal(list_temp,list_preci);

        String[] presentstring = new String[7];
        String[] futurestring = new String[7];
        for(int i =0;i<7;i++){
            presentstring[i] = find.searchPreci(msg7[i]);
            futurestring[i] = find.searchPreci(msg7_future[i]);
        }

        intent.putExtra("RESULT_LIST",presentstring);
        intent.putExtra("RESULT_FUTURE_LIST",futurestring);
        intent.putExtra("TYPE","precipitation");
        intent.putExtra("AVG",findAvgPreci(msg_month,msg_year));
        intent.putExtra("AVG_FUTURE",findAvgPreci(msg_month,Integer.toString(2040+diff)));

        //only for test purpose
        //textView.setText(r);


        //start the activity
        startActivity(intent);
    }

    public String findAvgPreci(String month,String year){
        String msg = month+"-"+year;
        for(String[] line:list_avg_preci){
            if (line[0].equals(msg)){
                return line[1];
            }
        }
        return "0";
    }

    public String findAvgTemp(String month,String year){
        String msg = month+"-"+year;
        for(String[] line:list_avg_temp){
            if (line[0].equals(msg)){
                return line[1];
            }
        }
        return "0";
    }

}
