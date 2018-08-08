package com.yao.app.java.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalStudy {
	public static void main(String[] args) throws Exception {

		ExecutorService service = Executors.newFixedThreadPool(1);
		while (true) {
			try {
				Thread.sleep(100);
				service.execute(new TestTask(false, 10));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	static class TestTask implements Runnable {

		/** 是否清除上下文参数变量 */
		private boolean clearThreadLocal;
		/** 线程休眠时间 */
		private long sleepTime;

		public TestTask(boolean clearThreadLocal, long sleepTime) {
			this.clearThreadLocal = clearThreadLocal;
			this.sleepTime = sleepTime;
		}

		public void run() {
			try {
				System.out.println(ThreadLocalHolder.get());
				try {
					// 大于0的时候才休眠，否则不休眠
					if (sleepTime > 0) {
						Thread.sleep(sleepTime);
					}
					ThreadLocalHolder.record();
				} catch (InterruptedException e) {

				}
			} finally {
				if (clearThreadLocal) {
					ThreadLocalHolder.clear();
				}
			}
		}
	}

	public static class ThreadLocalHolder {

		// 用integer的增大模拟list的增加
		public static final ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

		public static Integer get(){
			if(threadLocal.get()==null){
				threadLocal.set(0);
			}
			return threadLocal.get();
		}
		
		public static void record(){
			Integer i = threadLocal.get();
			threadLocal.set(i+1);
		}
		
//		public static final void set(byte[] b) {
//			threadLocal.set(b);
//		}

		public static final void clear() {
			threadLocal.set(null);
		}
	}
}
