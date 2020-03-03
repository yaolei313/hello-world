package com.yao.app.java.jvm;

import java.lang.instrument.Instrumentation;
import org.openjdk.jol.info.GraphLayout;
import org.springframework.instrument.InstrumentationSavingAgent;

/**
 * 关于InstrumentationSavingAgent,有2中获取方式
 *
 * 1.代理在main前启动
 * 清单必须包含Premain-Class属性
 * premain方法有两种可能的签名。
 *
 * JVM 首先尝试在代理类上调用以下方法：
 * public static void premain(String agentArgs, Instrumentation inst);
 * 如果代理类没有实现此方法，那么 JVM 将尝试调用：
 * public static void premain(String agentArgs);
 *
 * 2.代理在main开始执行之后启动
 * 清单必须包含属性Agent-Class属性
 *
 * 此处使用spring-instrument.jar即可, vm options: -javaagent:/Users/yaolei/spring-instrument.jar
 *
 * @author yaolei
 */
public class InstrumentationStudy {

    public static void main(String[] args) {
        // 搜索下就知道，系统可能缺少显示设备、键盘或鼠标这些外设的情况下可以使用该模式，服务器往往可能缺少前述设备
        System.setProperty("java.awt.headless", "true");

        Instrumentation instrumentation = InstrumentationSavingAgent.getInstrumentation();
        // TODOL
    }
}
