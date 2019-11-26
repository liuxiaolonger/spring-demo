package com.etoc;

/**
 * 
 * @author Admin
 *
 */
public class ThreadThree {
	public static void main(String[] args) {
		Runnable threadTest = new ThreadTes1();
	    Thread thread = new Thread(threadTest);
	    thread.start();
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
class ThreadTes1 implements Runnable {
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