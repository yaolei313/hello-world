package com.yao.app.script;

import groovy.util.Eval;

/**
 * 如何集成groovy脚本
 * http://www.groovy-lang.org/integrating.html
 * <p>
 * Created by yaolei02 on 2017/3/23.
 */
public class GroovyStudy {
    public static void main(String[] args) {
        // 1.执行Groovy脚本最简单的方式
        println(Eval.me("33*3"));
        println(Eval.x(10, "x*2"));
        println(Eval.xy(10, 11, "x+y"));
        println(Eval.xyz(10, 11, 12, "x+y+z"));
    }

    public static void println(Object object) {
        System.out.println(object);
    }
}
