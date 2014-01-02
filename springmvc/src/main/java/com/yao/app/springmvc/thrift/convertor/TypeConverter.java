package com.yao.app.springmvc.thrift.convertor;


public interface TypeConverter {
    Class<?> getNormalType();
    
    Class<?> getThriftType();
    
    Object convert(Object obj);
}
