package com.yao.app.nebula.command;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.Future;

import org.junit.Test;

public class CommandTest {

    @Test
    public void testSynchronous() {
        // 同步调用
        assertEquals("Hello World!", new CommandHelloWorld("World").execute());
        assertEquals("Hello Bob!", new CommandHelloWorld("Bob").execute());
    }

    @Test
    public void testAsynchronous1() throws Exception {
        // 异步调用
        assertEquals("Hello World!", new CommandHelloWorld("World").queue().get());
        assertEquals("Hello Bob!", new CommandHelloWorld("Bob").queue().get());
    }

    @Test
    public void testAsynchronous2() throws Exception {
        // 异步调用
        Future<String> fWorld = new CommandHelloWorld("World").queue();
        Future<String> fBob = new CommandHelloWorld("Bob").queue();

        assertEquals("Hello World!", fWorld.get());
        assertEquals("Hello Bob!", fBob.get());
    }
    
    @Test
    public void testObservable(){
        new CommandHelloWorld1("World").observe();
        
        new CommandHelloWorld1("World").toObservable();
    }
}
