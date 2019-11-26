package com.etoc.volatiles;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicInteger(原子性,实时的同步)
 * 
 * @author Admin
 *
 */
public class ThreadThree2 {
	public static void main(String[] args) {
		ThreadTesGo[] threads = new ThreadTesGo[10];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new ThreadTesGo();
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
class ThreadTesGo extends Thread {
	private static AtomicInteger ato = new AtomicInteger(0);
	/*
	 * 线程任务的执行体
	 * 
	 */
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			ato.incrementAndGet();
			System.out.println(getName()+"=" + ato);
		}
		
	}
}