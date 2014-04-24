package com.yao.app.protocol.thrift.convertor;


public interface TypeConverter {
    Class<?> getNormalType();
    
    Class<?> getThriftType();
    
    Object convert(Object obj);
}
