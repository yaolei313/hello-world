package com.yao.app.nebula.service.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yaolei02 on 2017/7/26.
 */
public class TestA implements Test {

    private static final Logger LOG = LoggerFactory.getLogger(TestA.class);

    @Override
    public void test() {
        LOG.info("test a");
    }
}
