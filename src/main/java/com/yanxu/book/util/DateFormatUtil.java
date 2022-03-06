package com.yanxu.book.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public  class DateFormatUtil {

    private static final String SHORT=new String("yyyy-MM-dd HH:mm:ss");



    public static String ShortStringFormat(Date date){
        DateFormat df =new SimpleDateFormat(SHORT);
        return df.format(date);
    }





}
