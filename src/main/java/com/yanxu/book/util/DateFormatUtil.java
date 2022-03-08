package com.yanxu.book.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public  class DateFormatUtil {

    private static final String SHORT=new String("yyyy-MM-dd HH:mm:ss");



    public static String ShortStringFormat(Date date){
        DateFormat df =new SimpleDateFormat(SHORT);
        return df.format(date);
    }

    public static Date ShortDateFormat(Date dateParam) throws ParseException {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(dateParam);
        calendar.add(Calendar.HOUR,-8);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(DateFormatUtil.SHORT);
        Date date=simpleDateFormat.parse(DateFormatUtil.ShortStringFormat(calendar.getTime()));
        return calendar.getTime();
    }





}
