package com.yao.app.script;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

/**
 * https://docs.oracle.com/javase/7/docs/technotes/guides/scripting/programmer_guide/
 * 
 * @author lei.yao
 *
 */
public class ScriptEngineStudy {

    public static void main(String[] args) {
        try {
            // create a script engine manager
            ScriptEngineManager factory = new ScriptEngineManager();
            // create a JavaScript engine
            ScriptEngine engine = factory.getEngineByName("JavaScript");
            // evaluate JavaScript code from String
            engine.eval("print('Hello, World')");
            
            // ===Invoking Script Functions and Methods===
            // JavaScript code in a String
            String script = "function hello(name) { print('Hello, ' + name); }";
            // evaluate script
            engine.eval(script);

            // javax.script.Invocable is an optional interface.
            // Check whether your script engine implements or not!
            // Note that the JavaScript engine implements Invocable interface.
            Invocable inv = (Invocable) engine;

            // invoke the global function named "hello"
            inv.invokeFunction("hello", "Scripting!!" );
            
            test3(engine);
            test4();
            test5();
            test6();
           
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public static void test3(ScriptEngine engine) throws ScriptException, NoSuchMethodException{
        // JavaScript code in a String. This code defines a script object 'obj'
        // with one method called 'hello'.
        String script = "var obj = new Object(); obj.hello = function(name) { print('Hello, ' + name); }";
        // evaluate script
        engine.eval(script);

        // javax.script.Invocable is an optional interface.
        // Check whether your script engine implements or not!
        // Note that the JavaScript engine implements Invocable interface.
        Invocable inv = (Invocable) engine;

        // get script object on which we want to call the method
        Object obj = engine.get("obj");

        // invoke the method named "hello" on the script object "obj"
        inv.invokeMethod(obj, "hello", "Script Method !!" );
    }
    
    public static void test4() throws Exception {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        // JavaScript code in a String
        String script = "function run() { print('run called'); }";

        // evaluate script
        engine.eval(script);

        Invocable inv = (Invocable) engine;

        // get Runnable interface object from engine. This interface methods
        // are implemented by script functions with the matching name.
        Runnable r = inv.getInterface(Runnable.class);

        // start a new thread that runs the script implemented
        // runnable interface
        Thread th = new Thread(r);
        th.start();
    }
    
    public static void test5() throws Exception {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        // JavaScript code in a String
        String script = "var obj = new Object(); obj.run = function() { print('run method called'); }";

        // evaluate script
        engine.eval(script);

        // get script object on which we want to implement the interface with
        Object obj = engine.get("obj");

        Invocable inv = (Invocable) engine;

        // get Runnable interface object from engine. This interface methods
        // are implemented by script methods of object 'obj'
        Runnable r = inv.getInterface(obj, Runnable.class);

        // start a new thread that runs the script implemented
        // runnable interface
        Thread th = new Thread(r);
        th.start();
    }

    public static void test6() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        engine.put("x", "hello");
        engine.eval("print(x);");

        // Now, pass a different script context
        ScriptContext newContext = new SimpleScriptContext();
        Bindings engineScope = newContext.getBindings(ScriptContext.ENGINE_SCOPE);

        // add new variable "x" to the new engineScope
        engineScope.put("x", "world");

        // execute the same script - but this time pass a different script context
        engine.eval("print(x);", newContext);
        // the above line prints "world"
    }
}
