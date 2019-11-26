package com.etoc.stop;

/**
 * 线程基础:守护进程
 * 
 * @author Admin 进程：进程是线程的集合 线程：线程就是一条执行路径（线程是为了提高效率） GC垃圾回收机制 main函数是主线程
 */
public class ThreadOne2 {
	public static void main(String[] args) {
		Thread input = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 120; i++) {
					try {
						Thread.sleep(10);
					} catch (Exception e) {
						// TODO: handle exception
					}
					System.out.println("我是子线程" + i);
				}

			}
		});
		// 启动之前设置守护线程
		input.setDaemon(true);
		input.start();
		for (int i = 0; i < 30; i++) {
			try {
				Thread.sleep(10);
			} catch (Exception e) {
				// TODO: handle exception
			}

			System.out.println("我是负极线程" + i);
		}
	}
}
