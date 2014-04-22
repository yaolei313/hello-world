package com.yao.app.protocol.thrift.convertor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * thrift生成的bean和系统内bean的转换
 * 
 * @author summer
 * 
 */
public class TypeConverterRegistry {

    private Map<Class<?>, TypeConverter> beanConverterList = new LinkedHashMap<Class<?>, TypeConverter>();

    public TypeConverterRegistry() {
        register(new TimestampTypeConverter());
    }

    public void register(TypeConverter typeConverter) {
        beanConverterList.put(typeConverter.getNormalType(), typeConverter);
        beanConverterList.put(typeConverter.getThriftType(), typeConverter);
    }

    public TypeConverter getRegisterConverter(Class<?> javaType) {
        return beanConverterList.get(javaType);
    }

}
