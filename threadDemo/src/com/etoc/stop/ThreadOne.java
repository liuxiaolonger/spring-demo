package com.etoc.stop;

/**
 * 线程基础:线程中断
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
		input1.start();
		for (int i = 0; i < 30; i++) {
			try {
				Thread.sleep(100);
				if (i == 15) {
					input.interrupt();
					input1.interrupt();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			System.out.println("主线程" + i);
		}

	}
}

class Res {
	public String name;
	public String sex;
	public boolean bool = false;
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
		System.out.println(Thread.currentThread().getName() + "线程");

	}
}
