package com.yao.app.spring;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 描述:若通过FactoryBean创建实现了InitializingBean, DisposableBean的对象，则这几个对象的方法不会被调用。具体参照逻辑
 *
 *
 * @auther allen@xiaohongshu.com 2019-10-31
 */
public class RoomFactoryBean implements FactoryBean<Room>, InitializingBean, DisposableBean {

    @Override
    public Room getObject() throws Exception {
        System.out.println("create");
        Room result = new Room();
        result.setName("hello");
        return result;
    }

    @Override
    public Class<?> getObjectType() {
        return Room.class;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destory factory bean");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("init factory bean");
    }
}
