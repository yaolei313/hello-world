package com.yao.app.jmx.mxbean;

import java.lang.management.ManagementFactory;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeData;

public class Test {

    public static void main(String[] args) {
        // 不能这样获取
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        
        try {
            ObjectName name = new ObjectName("com.yao.app.mxbeans:type=QueueSampler");
            CompositeData data = (CompositeData)mbs.getAttribute(name, "QueueSample");
            System.out.println(data.get("size"));
            System.out.println(data.toString());
        } catch (MalformedObjectNameException | AttributeNotFoundException | InstanceNotFoundException | MBeanException | ReflectionException e) {
            e.printStackTrace();
        }

    }

}
