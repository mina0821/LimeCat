package com.example.mina.limecatapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mina on 06/05/17.
 */

public class FindVal {
    //define variables to store the two dataset
    private List<String[]> list_temp = new ArrayList<String[]>();
    private List<String[]> list_preci = new ArrayList<String[]>();

    //constructor
    public FindVal (List<String[]> temp, List<String[]> preci){
        list_temp = temp;
        list_preci = preci;
    }

    //search through the dataset
    public String searchTemp(String date){
        //iterate each line of the list
        for (String[] line:list_temp){
            //check if date matches
            if (line[0].equals(date)){
                return line[1];
            }
        }
        return "1";
    }

    //search through the dataset
    public String searchPreci(String date){
        //iterate each line of the list
        for (String[] line:list_preci){
            //check if date matches
            if (line[0].equals(date)){
                return line[1];
            }
        }
        return "1";
    }
}
