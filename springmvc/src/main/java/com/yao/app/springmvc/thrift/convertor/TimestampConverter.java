package com.yao.app.springmvc.thrift.convertor;

import java.util.Calendar;
import java.util.Date;

import com.yao.app.springmvc.thrift.YTimestamp;

public class TimestampConverter implements Converter {

    @Override
    public Object convertToThrift(Object obj) {
        Date dt = (Date)obj;
        YTimestamp y = new YTimestamp();
        
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        
        y.setYear((short)c.get(Calendar.YEAR));
        // 月份是从0开始的
        y.setMonth((byte)c.get(Calendar.MONTH));
        y.setDay((byte)c.get(Calendar.DAY_OF_MONTH));
        y.setHour((byte)c.get(Calendar.HOUR_OF_DAY));
        y.setMinute((byte)c.get(Calendar.MINUTE));
        y.setSecond((byte)c.get(Calendar.SECOND));
        y.setMillisecond((short)c.get(Calendar.MILLISECOND));
        
        return y;
    }

    @Override
    public Object convertToNormal(Object obj) {
        YTimestamp y = (YTimestamp)obj;
        
        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, y.getYear());
        c.set(Calendar.MONTH, y.getMonth());
        c.set(Calendar.DAY_OF_MONTH, y.getDay());
        c.set(Calendar.HOUR_OF_DAY, y.getHour());
        c.set(Calendar.MINUTE, y.getMinute());
        c.set(Calendar.SECOND, y.getSecond());
        c.set(Calendar.MILLISECOND, y.getYear());
        
        return c.getTime();
    }

}
