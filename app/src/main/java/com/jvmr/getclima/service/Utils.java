package com.jvmr.getclima.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

public class Utils {
    public static List<String> convertObjectToListString(Object obj) {
        List<String> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((String[]) obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<String>((Collection<String>) obj);
        }
        return list;
    }

    public static String getDateNow(){
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }
}
