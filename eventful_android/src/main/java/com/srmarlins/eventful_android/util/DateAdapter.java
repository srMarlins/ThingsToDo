package com.srmarlins.eventful_android.util;

import org.simpleframework.xml.transform.Transform;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateAdapter implements Transform<Date> {

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public DateAdapter() {
    }

    @Override
    public Date read(String s) throws Exception {
        return this.df.parse(s);
    }

    @Override
    public String write(Date date) throws Exception {
        return this.df.format(date);
    }
}
