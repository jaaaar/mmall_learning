package com.mmall.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * create by YuWen
 */
public class DateTimeUtil {

    //joda-time 开源包
    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";


    private DateTimeUtil() {
    }

    //str  -->   date
    public static Date strToDate(String dateTimeStr, String formatStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    //date  -->  str
    public static String dateToStr(Date date, String formatStr) {
        if (date == null)
            return StringUtils.EMPTY;
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }

    public static Date strToDate(String dateTimeStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    //date  -->  str
    public static String dateToStr(Date date) {
        if (date == null)
            return StringUtils.EMPTY;
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }

}
