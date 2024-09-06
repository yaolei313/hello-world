package com.yao.app.proxy.cglib;


import com.yao.app.proxy.jdk.INamed;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

public class CgProxyTest {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();

        HelloImpl helloImpl = new HelloImpl();
        Callback callback1 = new CGWatcher(helloImpl);

        Collection SKIP_NAMES = Arrays.asList(new String[] {"hashCode", "equals", "toString",});

        enhancer.setInterfaces(new Class[] {Serializable.class, INamed.class});
        enhancer.setSuperclass(HelloImpl.class);
        enhancer.setCallbacks(new Callback[] {NoOp.INSTANCE, callback1});
        enhancer.setCallbackFilter(new CallbackFilter() {
            @Override
            public int accept(Method method) {
                if (SKIP_NAMES.contains(method.getName())) {
                    // hashCode,equals,toString使用NoOp
                    return 0;
                }
                return 1;
            }
        });

        Object proxy = enhancer.create();

        System.out.println("--------------------");
        System.out.println(proxy.toString());
        System.out.println("--------------------");
        ((HelloImpl) proxy).sayHello();
        
        ((INamed) proxy).getName();

        System.out.println("====================");
        Class<?> cl = proxy.getClass();
        printClasses(cl.getSuperclass());
        System.out.println("--------------------");
        printClasses(cl.getInterfaces());
        System.out.println("--------------------");

    }

    private static void printClasses(Class<?>... cls) {
        for (Class<?> cl : cls) {
            System.out.print(cl.getName() + "\t");
        }
        System.out.println();
    }

}
