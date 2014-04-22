package com.yao.app.protocol.thrift.convertor;

import java.util.Calendar;
import java.util.Date;

import com.yao.app.protocol.thrift.service.YTimestamp;

public class TimestampTypeConverter implements TypeConverter {

    @Override
    public Class<?> getNormalType() {
        return Date.class;
    }

    @Override
    public Class<?> getThriftType() {
        return YTimestamp.class;
    }

    @Override
    public Object convert(Object obj) {
        if (obj == null) {
            return null;
        }

        if (this.getNormalType().isAssignableFrom(obj.getClass())) {
            // 入参为Date或Date的子类
            Date dt = (Date) obj;
            YTimestamp y = new YTimestamp();

            Calendar c = Calendar.getInstance();
            c.setTime(dt);

            y.setYear((short) c.get(Calendar.YEAR));
            // 月份是从0开始的
            y.setMonth((byte) c.get(Calendar.MONTH));
            y.setDay((byte) c.get(Calendar.DAY_OF_MONTH));
            y.setHour((byte) c.get(Calendar.HOUR_OF_DAY));
            y.setMinute((byte) c.get(Calendar.MINUTE));
            y.setSecond((byte) c.get(Calendar.SECOND));
            y.setMillisecond((short) c.get(Calendar.MILLISECOND));

            return y;
        } else if (this.getThriftType().isAssignableFrom(obj.getClass())) {
            // 入参为YTimestamp或YTimestamp的子类
            YTimestamp y = (YTimestamp) obj;

            Calendar c = Calendar.getInstance();

            c.set(Calendar.YEAR, y.getYear());
            c.set(Calendar.MONTH, y.getMonth());
            c.set(Calendar.DAY_OF_MONTH, y.getDay());
            c.set(Calendar.HOUR_OF_DAY, y.getHour());
            c.set(Calendar.MINUTE, y.getMinute());
            c.set(Calendar.SECOND, y.getSecond());
            c.set(Calendar.MILLISECOND, y.getYear());

            return c.getTime();
        } else {
            return null;
        }
    }

}
