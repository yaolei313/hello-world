package com.yao.app.nio.buffer;

import java.nio.ByteBuffer;

/**
 * 缓冲区并不是线程安全的
 * 
 * @author yaolei
 *
 */
public class BufferStudy {

	public static void main(String[] args) {
		ByteBuffer bb = ByteBuffer.allocate(128);
		
		bb.put("hello-world".getBytes());
		outputStatus(bb);
		
		bb.limit(bb.position()).flip();
		outputStatus(bb);
		
		//output(bb);
		System.out.println((char)bb.get());
		System.out.println((char)bb.get());
		
		bb.compact();
		outputStatus(bb);
	}
	
	private static void outputStatus(ByteBuffer bb){
		System.out.println(bb.toString());
	}
	
	private static void output(ByteBuffer bb){
		int count = bb.remaining();
		for(int i= 0;i< count;i++){
			System.out.println((char)bb.get());
			System.out.println(bb.toString());
		}
		System.out.println();
	}

}
