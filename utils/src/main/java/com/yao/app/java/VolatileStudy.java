package com.yao.app.java;

public class VolatileStudy {

    public volatile int count = 0;
    
    public void test(){
        for(int i=0;i<1000;i++){
            new Thread(new Runnable(){

                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                }
                
            }).start();
        }
    }
    
    public static void main(String[] args) {
        VolatileStudy test = new VolatileStudy();
        test.test();
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(test.count);

    }

}
