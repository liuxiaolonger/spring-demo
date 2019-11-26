package com.etoc.join;

/**
 * 线程基础:线程join的用法(当调用这个方法，其他线程都停下来，走完才开始其他的线程)
 * 
 * @author Admin 进程：进程是线程的集合 线程：线程就是一条执行路径（线程是为了提高效率） GC垃圾回收机制 main函数是主线程
 */
public class ThreadOne {
	public static void main(String[] args) {
		ThreadIput input = new ThreadIput();
		ThreadIput input1 = new ThreadIput();
		input.setName("longer");
		input1.setName("hu");
		input.start();
		try {
			input.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		input1.start();
		for (int i = 0; i < 30; i++) {
			System.out.println("主线程" + i);
		}

	}
}

/**
 * 继承thread类，写入动作
 * 
 * @author Admin
 *
 */
class ThreadIput extends Thread {
	public boolean flag = true;

	/*
	 * 线程任务的执行体
	 * 
	 */
	@Override
	public synchronized void run() {
		for (int i = 0; i < 40; i++) {
			System.out.println(Thread.currentThread().getName() + "线程");
		}
	}
}
