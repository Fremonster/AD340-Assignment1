package com.shaffer.ad340assignments;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    private static String DATE_FORMAT = "MM/dd/yyyy";

    public static boolean isEmpty(String string){
        return TextUtils.isEmpty(string);
    }

    public static boolean isDateValid(String date) {
        if(isEmpty(date)) {
            return false;
        }
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isValidEmail(String email) {
        if(isEmpty(email)) {
            return false;
        }
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

//    public static boolean isValidTime(String time) {
//        if(isEmpty(time)) {
//            return false;
//        }
//        String TIME_PATTERN = "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
//        Pattern pattern = Pattern.compile(TIME_PATTERN);
//        Matcher matcher = pattern.matcher(time);
//        return matcher.matches();
//    }

    public static boolean isNumeric(String string){
        if(isEmpty(string)) {
            return false;
        }
        return TextUtils.isDigitsOnly(string);
    }

    public static boolean isValidUsername(String string) {
        if (string != null && string.length() >= 5) {
            return true;
        }
        return false;
    }

}
