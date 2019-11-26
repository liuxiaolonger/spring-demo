package com.etoc.test;

/**
 * 线程基础
 * 
 * @author Admin 进程：进程是线程的集合 线程：线程就是一条执行路径（线程是为了提高效率） GC垃圾回收机制 main函数是主线程
 */
public class ThreadFive {
	public static void main(String[] args) throws InterruptedException {
		Thread7 b = new Thread7();
		Thread threadTest = new Thread(b);
		Thread threadTest1 = new Thread(b);
		// 启动线程 先走主线程，start去启动一个线程，并行，当做是子线程
		threadTest.setName("窗口1");
		threadTest.start();
		b.flag = false;
		// 启动线程 先走主线程，start去启动一个线程，并行，当做是子线程
		threadTest1.setName("窗口2");
		threadTest1.start();
		// 启动线程 先走子线程，start去启动一个线程，当做主线程，从上往下
		// threadTest.run();

	}
}

/**
 * 继承thread类，重写run方法
 * 
 * @author Admin 当多个线程共享一个线程变量或静态变量的时候，做写的动作时，可能会发生数据冲突
 */
class Thread7 implements Runnable {
	private int num = 100;
	// 同一把锁
	private Object mutex1 = new Object();
	private Object mutex2 = new Object();
	public boolean flag = true;

	/*
	 * 线程任务的执行体
	 * 
	 */
	@Override
	public void run() {
		if (Thread.currentThread().getName().equals("窗口1")) {
			synchronized (mutex1) {
				while (num > 0) {
					try {
						Thread.sleep(1000L);
						System.out.println("mutex1");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					synchronized (mutex2) {
						show();
					}
				}
			}
		} else {
			synchronized (mutex2) {
				while (num > 0) {
					try {
						Thread.sleep(1000L);
						System.out.println("mutex2");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					synchronized (mutex1) {
						show();
					}
				}
			}
		}
		
	}

	public void show() {
		try {
			Thread.sleep(100);
			int i = 100 - num + 1;
			System.out.println("在" + Thread.currentThread().getName() + "购买到第" + i + "张票");
			num--;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
