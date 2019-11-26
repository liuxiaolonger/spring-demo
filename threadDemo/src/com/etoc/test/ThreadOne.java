package com.etoc.test;

/**
 * 线程基础
 * 
 * @author Admin 
 * 进程：进程是线程的集合 
 * 线程：线程就是一条执行路径（线程是为了提高效率）
 * GC垃圾回收机制
 * main函数是主线程
 */
public class ThreadOne {
public static void main(String[] args) {
	Runnable b = new Thread3();
	Thread threadTest = new Thread(b);
	// 启动线程 先走主线程，start去启动一个线程，并行，当做是子线程
	threadTest.setName("窗口1");
	threadTest.start();
	Thread threadTest1 = new Thread(b);
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
* @author Admin
*
*/
class Thread3 implements Runnable {
private int num = 100;

/*
 * 线程任务的执行体
 * 
 */
@Override
public void run() {
	while (num > 0) {
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
}
