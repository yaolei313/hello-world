package com.yao.app.protocol.thrift.client.pool;

import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.thrift.protocol.TProtocol;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2020-03-02
 */
public class ThriftClassUtils {

    private static final ConcurrentHashMap<Class, Class> ifaceMap = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<Class, Class> clientMap = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<Class, Constructor> constructorMap = new ConcurrentHashMap<>();

    public static Class getIfaceClass(Class topClass) {
        return ifaceMap.computeIfAbsent(topClass, param -> {
            try {
                return getInnerClass(param, IFACE);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static Class getClientClass(Class topClass) {
        return clientMap.computeIfAbsent(topClass, param -> {
            try {
                return getInnerClass(param, CLIENT);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static <T> T getClientInstance(Class topClass, TProtocol protocol) {
        Constructor<T> constructor = constructorMap.computeIfAbsent(topClass, param -> {
            try {
                Class clazz = getInnerClass(param, CLIENT);
                return clazz.getConstructor(TProtocol.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        try {
            return constructor.newInstance(protocol);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ----

    private static Class getInnerClass(Class topClass, String innerClassName) throws Exception {
        ClassLoader classLoader = topClass.getClassLoader();
        String name = topClass.getName() + innerClassName;
        return classLoader.loadClass(name);

    }

    private static final String IFACE = "$Iface";

    private static final String CLIENT = "$Client";
}
