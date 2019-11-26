package com.etoc.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 模拟线程不安全的问题 什么是线程安全，
 * 当多个线程同时共享同一个全局变量或者静态变量的时候，做写的操作时，可能会发生数据冲突，也就是线程安全的问题，做读的时候不会发生线程安全
 * 
 * @author Admin
 */
public class newFixedThreadPools2 {
	public static void main(String[] args) {
		ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
		// 执行execute,表示创建了线程，类似start,但线程执行
		for (int i = 0; i < 10; i++) {
			final int index=i;
			newSingleThreadExecutor.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println("启动线程+" + Thread.currentThread().getName()+"===="+index);
				}
			});
		}
	
	}
}
