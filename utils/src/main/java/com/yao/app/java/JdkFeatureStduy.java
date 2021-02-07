package com.yao.app.java;

import static java.time.DayOfWeek.WEDNESDAY;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class JdkFeatureStduy {


    public static void main(String[] args) {
        studyJDK9();
        System.out.println("-".repeat(20));
        studyJdk10();
        System.out.println("-".repeat(20));
        studyJdk11();
        System.out.println("-".repeat(20));
    }

    public static void studyJDK9() {
        List<String> list = List.of("Java", "Python", "Ruby");
        System.out.println(list);
    }

    public static void studyJdk10() {
        // 只能用于局部变量上, 声明时必须初始化,不能用作方法参数,不能在Lambda表达式中使用(jdk11就可以了)
        var name = "hello world";
        System.out.println(name);

        Arrays.asList("Java", "Python", "Ruby").forEach((var s) -> System.out.println("Hello, " + s));

        // G1 FullGC改进，单线程变为多线程，通过-XX:ParallelGCThreads参数调整

        // 实验性JIT编译器Graal.
        // Graal使用JDK 9中引入的JVM编译器接口（JVMCI）。
        // Graal是一个以 Java 为主要编程语言、面向 Java bytecode 的编译器。与用 C++实现的 C1 及 C2 相比，它的模块化更加明显，也更加容易维护。
        // Graal既可以作为动态编译器，在运行时编译热点方法；亦可以作为静态编译器，实现 AOT 编译。
        // 在 Java 10 中，Graal 作为试验性 JIT 编译器一同发布（JEP 317）。
        // 将 Graal 编译器研究项目引入到 Java 中，或许能够为 JVM 性能与当前 C++ 所写版本匹敌（或有幸超越）提供基础。
        // Java 10 中默认情况下 HotSpot 仍使用的是 C2 编译器，要启用 Graal 作为 JIT 编译器，请在 Java 命令行上使用以下参数：
        // -XX:+UnlockExperimentalVMOptions -XX:+UseJVMCICompiler
    }

    public static void studyJdk11() {
        // JDK8 -> 11 参考
        // https://zhuanlan.zhihu.com/p/87157172
        // LTS版本，可以使用

        // 新的httpclient支持HTTP/2和WebSocket,异步调用
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://openjdk.java.net/")).GET().build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();

        CompletableFuture<HttpResponse<String>> future = client.sendAsync(request, bodyHandler);
        future.thenApply(HttpResponse::body).thenAccept(System.out::println).join();

        List<String> list = List.of("Java", "Python", "Ruby");
        // 旧的方法:传入String[]:
        String[] oldway = list.toArray(new String[list.size()]);
        System.out.println(oldway);
        // 新的方法:传入IntFunction:
        String[] newway = list.toArray(String[]::new);
        System.out.println(newway);

        // Files.readString writeString

        String s = " Hello, JDK11!\u3000\u3000";
        System.out.println("     original: [" + s + "]");
        System.out.println("         trim: [" + s.trim() + "]");
        System.out.println("        strip: [" + s.strip() + "]");
        System.out.println(" stripLeading: [" + s.stripLeading() + "]");
        System.out.println("stripTrailing: [" + s.stripTrailing() + "]");

        String s1 = " \u3000"; // 由一个空格和一个中文空格构成
        System.out.println(s1.isEmpty()); // false
        System.out.println(s1.isBlank()); // true

        String s2 = "Java\nPython\nRuby";
        s2.lines().forEach(System.out::println);

        // 增强 Java 启动器,可以直接通过java HelloWorld.java允许单个java文件，而不需要
        // javac HelloWorld.java
        // java -cp . hello.World

        // 允许lambda表达式中, 使用var
        // (x,y) -> x.process(y)
        // (@Nonnull var x, @Nullable var y) -> x.process(y);

        // ZGC
        // -XX:+UnlockExperimentalVMOptions -XX:+UseZGC
    }

    public void studyJdk12() {
        // Switch 表达式扩展
        int dayNumber;
        DayOfWeek day = DayOfWeek.FRIDAY;
        switch (day) {
            case MONDAY:
            case FRIDAY:
            case SUNDAY:
                dayNumber = 6;
                break;
            case TUESDAY:
                dayNumber = 7;
                break;
            case THURSDAY:
            case SATURDAY:
                dayNumber = 8;
                break;
            case WEDNESDAY:
                dayNumber = 9;
                break;
            default:
                throw new IllegalStateException("Huh? " + day);
        }

        /*
        int dayNumber2 = switch (day) {
            case MONDAY, FRIDAY, SUNDAY -> 6;
            case TUESDAY                -> 7;
            case THURSDAY, SATURDAY     -> 8;
            case WEDNESDAY              -> 9;
            default                      -> throw new IllegalStateException("Huh? " + day);
        }
         */

        // 13新特性

        // 14新特性

        // 15新特性
    }
}
