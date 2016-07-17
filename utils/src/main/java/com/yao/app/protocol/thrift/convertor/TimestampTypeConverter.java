package com.yao.app.protocol.thrift.convertor;

import java.util.Date;

public class TimestampTypeConverter implements TypeConverter {

    @Override
    public Class<?> getNormalType() {
        return Date.class;
    }

    @Override
    public Class<?> getThriftType() {
        return null;
    }

    @Override
    public Object convert(Object obj) {
        return null;
    }

}
