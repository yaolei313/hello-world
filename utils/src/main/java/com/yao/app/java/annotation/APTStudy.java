package com.yao.app.java.annotation;

import java.util.Iterator;
import java.util.ServiceLoader;
import javax.annotation.processing.Processor;

/**
 * Created by yaolei02 on 2018/11/19.
 */
public class APTStudy {
    public static void main(String[] args) {
        ServiceLoader<Processor> serviceLoader = ServiceLoader.load(Processor.class);
        Iterator<Processor> it = serviceLoader.iterator();
        while (it.hasNext()) {
            System.out.println("Iterator<Processor> next()方法调用..");
            Processor item = it.next();
            System.out.println("Processor:" + item.getSupportedAnnotationTypes() + "," + item.getSupportedOptions());
        }
    }
}
