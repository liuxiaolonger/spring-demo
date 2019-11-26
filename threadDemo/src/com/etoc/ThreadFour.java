package com.etoc;

/**
 * 
 * @author Admin
 *
 */
public class ThreadFour {
	public static void main(String[] args) {
	    Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i <20; i++) {
					System.out.println("子线程运行到"+i);
				}
				
			}
		});
	    thread.start();
		System.out.println("主线程开始");
		for (int i = 0; i <20; i++) {
			System.out.println("主线程运行到"+i);
		}
	}
}

