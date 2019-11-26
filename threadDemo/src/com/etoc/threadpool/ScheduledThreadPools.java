package com.etoc.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 模拟线程不安全的问题 什么是线程安全，
 * 当多个线程同时共享同一个全局变量或者静态变量的时候，做写的操作时，可能会发生数据冲突，也就是线程安全的问题，做读的时候不会发生线程安全
 * 
 * @author Admin
 */
public class ScheduledThreadPools {
	public static void main(String[] args) {
		// 定义定时线程池，三秒钟运行一次run方法
	ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(3);
		// 执行execute,表示创建了线程，类似start
	newScheduledThreadPool.schedule(new Runnable() {
		@Override
		public void run() {	
			System.out.println("启动线程+" + Thread.currentThread().getName());
		}
	},3,TimeUnit.SECONDS);
	}
}
