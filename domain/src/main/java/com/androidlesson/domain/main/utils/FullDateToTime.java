package com.androidlesson.domain.main.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FullDateToTime {
    public String execute(String input) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

            Date date = inputFormat.parse(input);
            return outputFormat.format(date);
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
            return ""; // Вернуть пустую строку в случае ошибки
        }
    }
}

