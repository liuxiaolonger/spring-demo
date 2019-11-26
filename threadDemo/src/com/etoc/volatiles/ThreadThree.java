package com.etoc.volatiles;

/**
 * volatile(不具有原子性)
 * 
 * @author Admin
 *
 */
public class ThreadThree {
	public static void main(String[] args) {
		ThreadTes1[] threads = new ThreadTes1[10];
	     for (int i = 0; i < threads.length; i++) {
	    	 threads[i]=new ThreadTes1();
		} 
	     for (int i = 0; i < threads.length; i++) {
	    	 threads[i].start();
		}
	}
}

/**
 * 继承thread类，重写run方法
 * 
 * @author Admin
 *
 */
class ThreadTes1 extends Thread {
	private static volatile int count = 0;

	/*
	 * 线程任务的执行体
	 * 
	 */
	@Override
	public void run() {
		System.out.println("子线程开始，，，");
		for (int i = 0; i < 1000; i++) {
			count++;
		}
		System.out.println("子线程结束" + count);
	}
}