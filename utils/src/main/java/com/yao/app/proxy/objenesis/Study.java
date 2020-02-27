package com.yao.app.proxy.objenesis;

import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;
import org.springframework.objenesis.instantiator.ObjectInstantiator;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2020-02-25
 */
public class Study {

    public static void main(String[] args) {
        Objenesis objenesis = new ObjenesisStd();
        ObjectInstantiator instantiator = objenesis.getInstantiatorOf(AlonePerson.class);

        AlonePerson a1 = (AlonePerson)instantiator.newInstance();
        a1.setName("li san");
        a1.set
    }
}
