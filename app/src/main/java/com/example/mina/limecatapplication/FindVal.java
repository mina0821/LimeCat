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
    public String searchTemp(String date, String time, String model){
        return null;
    }
}
