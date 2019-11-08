package com.yao.app.spring;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 描述
 *
 * @auther allen@xiaohongshu.com 2019-10-31
 */
public class TestFactoryBean implements FactoryBean<TempName>, InitializingBean, DisposableBean {

    @Override
    public TempName getObject() throws Exception {
        System.out.println("create");
        TempName result = new TempName();
        result.setName("hello");
        return result;
    }

    @Override
    public Class<?> getObjectType() {
        return TempName.class;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destory");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("init");
    }
}
