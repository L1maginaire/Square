package com.example.square.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by l1maginaire on 1/28/18.
 */

public class StringProcessor {
    private final SimpleDateFormat dfFrom = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
    private final SimpleDateFormat dfTo = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    private Date date;

    public String dateFormat(String dateString){
        try {
            date = dfFrom.parse(dateString);
            dateString = dfTo.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString;
    }

    public String nameFormat(String name){
        String[] strings = name.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String s:strings) {
            sb.append(s.substring(0, 1).toUpperCase());
            sb.append(s.substring(1).toLowerCase());
            sb.append(" ");
        }
        return sb.toString();
    }
}
