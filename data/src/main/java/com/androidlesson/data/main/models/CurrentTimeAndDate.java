package com.androidlesson.data.main.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CurrentTimeAndDate {

    public String execute() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return formatter.format(new Date());
    }
}
