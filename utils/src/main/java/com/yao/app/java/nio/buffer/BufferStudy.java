package com.yao.app.java.nio.buffer;

import java.nio.ByteBuffer;

/**
 * 缓冲区并不是线程安全的
 * 
 * @author yaolei
 *
 */
public class BufferStudy {

	public static void main(String[] args) {
		ByteBuffer bb = ByteBuffer.allocate(32);
		
		System.out.println("hello".length());
		
		bb.put("hello".getBytes());
		outputStatus(bb);
		
		bb.limit(bb.position()).flip();
		outputStatus(bb);
		
		System.out.println((char)bb.get());
		System.out.println((char)bb.get());
		outputStatus(bb);
		
		// 压缩
		bb.compact();
		System.out.println("压缩后状态:");
		outputStatus(bb);
		
		System.out.println((char)bb.get());
		outputStatus(bb);
		
		// 缓冲区比较
		ByteBuffer b2 = ByteBuffer.allocate(28);
		b2.put("o".getBytes());
		
		if(bb.compareTo(b2)==0){
			System.out.println("bb equals b2");
		}
	}
	
	private static void outputStatus(ByteBuffer bb){
		System.out.print(bb.toString());
		System.out.print("\t[");
		for(int i=0;i<bb.limit();i++){
			if(i!=0 && i!= bb.capacity()-1){
				System.out.print(",");
			}
			System.out.print((char)bb.get(i));
		}
		System.out.print("]");
		System.out.println();
	}

}
