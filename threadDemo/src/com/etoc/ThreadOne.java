package com.etoc;

/**
 * 模拟线程不安全的问题
 *  什么是线程安全，
 *  当多个线程同时共享同一个全局变量或者静态变量的时候，做写的操作时，可能会发生数据冲突，也就是线程安全的问题，做读的时候不会发生线程安全
 * @author Admin
 */
public class ThreadOne {
	public static void main(String[] args) {
		Runnable threadTest = new Thread3();
		// 启动线程 先走主线程，start去启动一个线程，并行，当做是子线程
		Thread thread=new Thread(threadTest);
		Thread thread1=new Thread(threadTest);
		thread.setName("窗口1");
		thread.start();
		thread1.setName("窗口2");
		thread1.start();
	}
}

/**
 * 继承thread类，重写run方法
 * 
 * @author Admin
 *
 */
class Thread3 implements Runnable {
	private   int count = 100;
	/*
	 * 线程任务的执行体
	 * 
	 */
	@Override
	public  void run() {
		while (count > 0) {
			try {
				Thread.sleep(100);
				int i = 100 - count + 1;
				System.out.println(Thread.currentThread().getName() + "恭喜你抢到票了，第" + i + "位乘客");
				count--;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
