import com.yao.app.spi.processor.TestProcessor;

/**
 * <pre>
 * requires 表示这个模块依赖哪些模块，java.base不需要明确requires, 默认会增加，就像java.lang不用明确import一样的。
 * export   表示这个模块向外暴露哪些包，这个是在编译时可以引用的包。
 * open     表示这个模块向外暴露哪些包，只不过是运行时可用，就是编译时不能引用，但是可以用反射的方法调用。
 * provides 表示这个模块提供的接口或是虚类的实现，可以通过java.util.ServiceLoader来加载实现的。
 * uses     表示这个模块要用到的另人申明的接口或是虚类。
 * </pre>
 */
module apt.processor {
    requires java.base;
    requires java.compiler;

    requires apt.api;
    requires auto.common;
    requires auto.service;
    requires com.squareup.javapoet;

    provides javax.annotation.processing.Processor with TestProcessor;

    uses javax.annotation.processing.Processor;
}