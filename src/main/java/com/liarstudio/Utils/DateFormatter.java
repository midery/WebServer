package com.liarstudio.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {
    public static DateFormatter instanse;
    public static String DATE_FORMAT_FULL = "dd/MM/yyyy";
    public static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_FULL, Locale.getDefault());

    private DateFormatter() {
    }

    public static Date parseDate(String date) {
        try {
            return sdf.parse(date);
        } catch (ParseException pe) {
            pe.printStackTrace();
            return null;
        }
    }

    public static String toString(Date date) {
        return sdf.format(date);
    }

}
