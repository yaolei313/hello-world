import com.yao.app.spi.processor.TestProcessor;

/**
 * <pre>
 * https://www.oracle.com/corporate/features/understanding-java-9-modules.html
 * http://cr.openjdk.java.net/~iris/se/9/java-se-9-pr-spec-01/java-se-9-spec.html#s7
 *
 * open module [name] 表示整个模块内的包都是仅runtime时可用
 *
 * requires 表示这个模块依赖哪些模块，java.base不需要明确requires, 默认会增加，就像java.lang不用明确import一样的。
 *   - requires static
 *     表示compile time时需要，但runtime时不需要
 *   - requires transitive
 *     表示隐式递归，比如当前module a若包含requires transitive b,那requires a的模块则隐式包含requires b。
 *     jsr379规范约定了默认标准是这样，一般用不到吧
 * export   表示这个模块向外暴露哪些包，这个是在编译时可以引用的包。
 * open     表示这个模块向外暴露哪些包，即only runtime时可用，编译时不能引用，但是可以用反射的方法调用。
 * provides 表示这个模块提供的接口或是abstract类的实现，可以通过java.util.ServiceLoader来加载实现的。
 * uses     表示这个模块要用到的另人申明的接口或是abstract类。
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