package com.yao.app.java.jvm;

import java.lang.instrument.Instrumentation;

/**
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
 * @author yaolei
 *
 */
public class InstrumentationSavingAgent {
    private static volatile Instrumentation instrumentation;

    public static void premain(String agentArgs, Instrumentation inst) {
        instrumentation = inst;
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        instrumentation = inst;
    }

    public static Instrumentation getInstrumentation() {
        return instrumentation;
    }
}
