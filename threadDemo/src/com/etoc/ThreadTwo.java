package com.etoc;

/**
 * 
 * @author Admin
 *
 */
public class ThreadTwo {
	public static void main(String[] args) {
		ThreadTest threadTest = new ThreadTest();
		//启动线程  先走主线程，start去启动一个线程，并行，当做是子线程
		 threadTest.start();
		//启动线程  先走子线程，start去启动一个线程，当做主线程，从上往下
		//threadTest.run();
		System.out.println("主线程开始");
		for (int i = 0; i <20; i++) {
			System.out.println("主线程运行到"+i);
		}
	}
}

/**
 * 继承thread类，重写run方法
 * 
 * @author Admin
 *
 */
class ThreadTest extends Thread {
	/*
	 * 线程任务的执行体
	 * 
	 */
	@Override
	public void run() {
		for (int i = 0; i <20; i++) {
			System.out.println("当前运行到"+i);
		}
	}
}