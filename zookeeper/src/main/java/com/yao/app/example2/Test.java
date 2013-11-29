package com.yao.app.example2;

public class Test {

	public static void main(String[] args) throws Exception {
		final String host = "127.0.0.1:2181";
		final Integer count = 5;
		Performer p1 = new Performer(host,count);
		Performer p2 = new Performer(host,count);
		Performer p3 = new Performer(host,count);
		Performer p4 = new Performer(host,count);
		Performer p5 = new Performer(host,count);
		
		new Thread(p1).start();
		new Thread(p2).start();
		new Thread(p3).start();
		new Thread(p4).start();
		System.out.println("sleep 10s");
		Thread.sleep(10000);
		new Thread(p5).start();
		
		System.in.read();

	}

}
