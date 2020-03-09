package com.yao.app.java.bytecode;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2020-03-09
 */
public class Hello {

    public static final String HELLO_TEXT = "hello";

    public static void main(String[] args) {
        System.out.println(HELLO_TEXT);
    }
    // javap -v -p Hello.class

    /*
    Classfile /Users/yaolei/Work/hello-world/utils/target/classes/com/yao/app/java/bytecode/Hello.class
      Last modified 2020-3-9; size 630 bytes
      MD5 checksum 8e6345fe3c8ffd0f8ba35c4468f2942d
      Compiled from "Hello.java"
    public class com.yao.app.java.bytecode.Hello
      minor version: 0
      major version: 52
      flags: ACC_PUBLIC, ACC_SUPER
    Constant pool:
       #1 = Methodref          #6.#23         // java/lang/Object."<init>":()V
       #2 = Fieldref           #24.#25        // java/lang/System.out:Ljava/io/PrintStream;
       #3 = Class              #26            // com/yao/app/java/bytecode/Hello
       #4 = String             #27            // hello
       #5 = Methodref          #28.#29        // java/io/PrintStream.println:(Ljava/lang/String;)V
       #6 = Class              #30            // java/lang/Object
       #7 = Utf8               HELLO_TEXT
       #8 = Utf8               Ljava/lang/String;
       #9 = Utf8               ConstantValue
      #10 = Utf8               <init>
      #11 = Utf8               ()V
      #12 = Utf8               Code
      #13 = Utf8               LineNumberTable
      #14 = Utf8               LocalVariableTable
      #15 = Utf8               this
      #16 = Utf8               Lcom/yao/app/java/bytecode/Hello;
      #17 = Utf8               main
      #18 = Utf8               ([Ljava/lang/String;)V
      #19 = Utf8               args
      #20 = Utf8               [Ljava/lang/String;
      #21 = Utf8               SourceFile
      #22 = Utf8               Hello.java
      #23 = NameAndType        #10:#11        // "<init>":()V
      #24 = Class              #31            // java/lang/System
      #25 = NameAndType        #32:#33        // out:Ljava/io/PrintStream;
      #26 = Utf8               com/yao/app/java/bytecode/Hello
      #27 = Utf8               hello
      #28 = Class              #34            // java/io/PrintStream
      #29 = NameAndType        #35:#36        // println:(Ljava/lang/String;)V
      #30 = Utf8               java/lang/Object
      #31 = Utf8               java/lang/System
      #32 = Utf8               out
      #33 = Utf8               Ljava/io/PrintStream;
      #34 = Utf8               java/io/PrintStream
      #35 = Utf8               println
      #36 = Utf8               (Ljava/lang/String;)V
    {
      public static final java.lang.String HELLO_TEXT;
        descriptor: Ljava/lang/String;
        flags: ACC_PUBLIC, ACC_STATIC, ACC_FINAL
        ConstantValue: String hello

      public com.yao.app.java.bytecode.Hello();
        descriptor: ()V
        flags: ACC_PUBLIC
        Code:
          stack=1, locals=1, args_size=1
             0: aload_0
             1: invokespecial #1                  // Method java/lang/Object."<init>":()V
             4: return
          LineNumberTable:
            line 8: 0
          LocalVariableTable:
            Start  Length  Slot  Name   Signature
                0       5     0  this   Lcom/yao/app/java/bytecode/Hello;

      public static void main(java.lang.String[]);
        descriptor: ([Ljava/lang/String;)V
        flags: ACC_PUBLIC, ACC_STATIC
        Code:
          stack=2, locals=1, args_size=1
             0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
             3: ldc           #4                  // String hello
             5: invokevirtual #5                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
             8: return
          LineNumberTable:
            line 13: 0
            line 14: 8
          LocalVariableTable:
            Start  Length  Slot  Name   Signature
                0       9     0  args   [Ljava/lang/String;
    }
    SourceFile: "Hello.java"
     */
}

