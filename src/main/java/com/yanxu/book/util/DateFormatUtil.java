package com.yanxu.book.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public  class DateFormatUtil {

    private static final String LONG=new String("yyyy-MM-dd HH:mm:ss");

    private static final String SHORT=new String("yyyy-MM-dd");



    public static String LongStringFormat(Date date){
        DateFormat df =new SimpleDateFormat(LONG);
        return df.format(date);
    }


    public static String ShortStringFormat(Date date){
        DateFormat df =new SimpleDateFormat(SHORT);
        return df.format(date);
    }





}
