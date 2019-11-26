package com.yao.app.spring;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2019-11-19
 */
public class Room implements InitializingBean, DisposableBean {
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public void destroy() throws Exception {
        System.out.println("destory bean");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("init bean");
    }
}
