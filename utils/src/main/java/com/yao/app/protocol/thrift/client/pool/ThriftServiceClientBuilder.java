package com.yao.app.protocol.thrift.client.pool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2020-03-02
 */
public class ThriftServiceClientBuilder<T> {

    private GenericObjectPoolConfig poolConfig;

    private ThriftConfig thriftConfig;

    private boolean proxyTargetClass = false;

    public ThriftServiceClientBuilder() {
    }

    public ThriftServiceClientBuilder<T> setPoolConfig(GenericObjectPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
        return this;
    }

    public ThriftServiceClientBuilder<T> setThriftConfig(ThriftConfig thriftConfig) {
        this.thriftConfig = thriftConfig;
        return this;
    }

    public ThriftServiceClientBuilder<T> setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
        return this;
    }

    public T build() {
        Class serviceIfaceClass = thriftConfig.getServiceIfaceClass();
        ThriftServiceClientPool thriftPool = new ThriftServiceClientPool<>(poolConfig, thriftConfig);
        if (proxyTargetClass) {
            Enhancer enhancer = new Enhancer();
            enhancer.setInterfaces(new Class[]{serviceIfaceClass, AutoCloseable.class});
            enhancer.setCallback(new ThriftMethodInterceptor(thriftPool));
            Object proxy = enhancer.create();
            return (T) proxy;
        } else {
            ThriftInvocationHandler h = new ThriftInvocationHandler(thriftPool);
            Object proxy = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{serviceIfaceClass, AutoCloseable.class}, h);
            return (T) proxy;
        }
    }

    /**
     * 不考虑equals，hashcode，toString方法，因为thrift的接口一般没这些
     */
    private static class ThriftInvocationHandler implements InvocationHandler {

        private final ThriftServiceClientPool thriftPool;

        public ThriftInvocationHandler(ThriftServiceClientPool thriftPool) {
            this.thriftPool = thriftPool;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return doIntercept(method, args, thriftPool);
        }
    }

    private static class ThriftMethodInterceptor implements MethodInterceptor {

        private final ThriftServiceClientPool thriftPool;

        public ThriftMethodInterceptor(ThriftServiceClientPool thriftPool) {
            this.thriftPool = thriftPool;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            return doIntercept(method, objects, thriftPool);
        }
    }

    private static Object doIntercept(Method method, Object[] args, ThriftServiceClientPool thriftPool) {
        String methodName = method.getName();
        System.out.println("into " + methodName);
        if (isAutoCloseableMethod(method)) {
            System.out.println("close");
            thriftPool.close();
            return null;
        }

        Object client = thriftPool.getClient();
        System.out.println("getClient");
        if (client == null) {
            throw new RuntimeException("create fail.");
        }

        Object result = null;
        Exception error = null;
        try {
            result = method.invoke(client, args);

        } catch (Exception e) {
            error = e;
        }

        if (error != null) {
            System.out.println("returnBrokenClient");
            thriftPool.returnBrokenClient(client);
        } else {
            System.out.println("returnClient");
            thriftPool.returnClient(client);
        }

        return result;
    }

    private static boolean isAutoCloseableMethod(Method method) {
        if (method == null || !method.getName().equals("close")) {
            return false;
        }
        if (method.getParameterCount() != 0) {
            return false;
        }
        return true;
    }

    public static boolean isEqualsMethod(Method method) {
        if (method == null || !method.getName().equals("equals")) {
            return false;
        }
        if (method.getParameterCount() != 1) {
            return false;
        }
        return method.getParameterTypes()[0] == Object.class;
    }

    public static boolean isHashCodeMethod(Method method) {
        return (method != null && method.getName().equals("hashCode") && method.getParameterCount() == 0);
    }

    public static boolean isToStringMethod(Method method) {
        return (method != null && method.getName().equals("toString") && method.getParameterCount() == 0);
    }


    public static boolean isObjectMethod(Method method) {
        return (method != null && (method.getDeclaringClass() == Object.class ||
            isEqualsMethod(method) || isHashCodeMethod(method) || isToStringMethod(method)));
    }

}
