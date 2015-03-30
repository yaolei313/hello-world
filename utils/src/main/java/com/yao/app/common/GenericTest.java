package com.yao.app.common;

import java.util.ArrayList;
import java.util.List;

public class GenericTest {
    public static void main(String[] args){
        // List<Student>[] array = new List<Student>[10]; 
        try{
            Test1<Test2<Student>> t = new Test1<Test2<Student>>();
            
            t.array = (Test2<Student>[]) new Test2<?>[10];
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static class Test1<T>{
        public T[] array;
    }
    
    public static class Test2<T>{
        public T student;
    }
}
