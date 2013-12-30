package com.yao.app.springmvc.thrift.convertor;

public interface Converter {
    Object convertToThrift(Object obj);

    Object convertToNormal(Object obj);
}
