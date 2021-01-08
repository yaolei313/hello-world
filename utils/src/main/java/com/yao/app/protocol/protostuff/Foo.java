package com.yao.app.protocol.protostuff;

public class Foo {

    private String name;

    private int id;

    public Foo(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
