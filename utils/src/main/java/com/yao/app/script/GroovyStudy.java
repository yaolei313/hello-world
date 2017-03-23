package com.yao.app.script;

import groovy.util.Eval;

/**
 * 如何集成groovy脚本
 * http://www.groovy-lang.org/integrating.html
 *
 * Created by yaolei02 on 2017/3/23.
 */
public class GroovyStudy {
    public static void main(String[] args) {
        // 1.执行Groovy脚本最简单的方式
        println(Eval.me("33*3"));
    }

    public static void println(Object object){
        System.out.println(object);
    }
}
