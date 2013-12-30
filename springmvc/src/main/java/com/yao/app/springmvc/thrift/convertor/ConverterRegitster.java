package com.yao.app.springmvc.thrift.convertor;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.yao.app.springmvc.domain.UserBean;
import com.yao.app.springmvc.thrift.YTimestamp;
import com.yao.app.springmvc.thrift.YUser;

/**
 * thrift生成的bean和系统内bean的转换
 * 
 * @author summer
 * 
 */
@Component
public class ConverterRegitster implements InitializingBean {

    private Map<Class<?>, Converter> normalBeanConverterList = new LinkedHashMap<Class<?>, Converter>();
    
    private Map<Class<?>, Converter> thriftBeanConverterList = new LinkedHashMap<Class<?>, Converter>();

    @Override
    public void afterPropertiesSet() throws Exception {
        UserConverter userConverter = new UserConverter();
        normalBeanConverterList.put(UserBean.class, userConverter);
        thriftBeanConverterList.put(YUser.class, userConverter);
        
        TimestampConverter timeConverter = new TimestampConverter();
        normalBeanConverterList.put(Date.class, timeConverter);
        thriftBeanConverterList.put(YTimestamp.class, timeConverter);
    }

    public Object convertToThrift(Object obj) {
        return null;
    }

}
