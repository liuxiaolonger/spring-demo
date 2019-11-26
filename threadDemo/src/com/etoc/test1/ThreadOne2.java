package com.etoc.test1;

/**
 * 线程基础
 * 
 * @author Admin 进程：进程是线程的集合 线程：线程就是一条执行路径（线程是为了提高效率） GC垃圾回收机制 main函数是主线程
 */
public class ThreadOne2 {
	public static void main(String[] args) {
		Res1 res = new Res1();
		ThreadIput1 input = new ThreadIput1(res);
		ThreadOut1 out = new ThreadOut1(res);
		input.start();
		out.start();

	}
}

class Res1 {
	public String name;
	public String sex;
	public boolean bool=false;
}

/**
 * 继承thread类，写入动作
 * 
 * @author Admin
 *
 */
class ThreadIput1 extends Thread {
	public Res1 res;

	public ThreadIput1(Res1 res) {
		this.res = res;
	}

	/*
	 * 线程任务的执行体
	 * 
	 */
	@Override
	public void run() {
		int count = 0;
		while (true) {
			synchronized (res) {
				if(res.bool==true) {
					try {
						res.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (count == 0) {
					res.name = "王小龙";
					res.sex = "男";
				} else {
					res.name = "周乐乐";
					res.sex = "女";
				}
				count = (count + 1) % 2;
				res.bool=true;
				res.notify();
			}
		}

	}
}

/**
 * 继承thread类，读出动作
 * 
 * @author Admin
 *
 */
class ThreadOut1 extends Thread {
	public Res1 res;

	public ThreadOut1(Res1 res) {
		this.res = res;
	}

	/*
	 * 线程任务的执行体
	 * 
	 */
	@Override
	public void run() {
		while (true) {
			synchronized (res) {
				if(res.bool==false) {
					try {
						res.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println(res.name + res.sex);
				res.bool=false;
				res.notify();
			}
		}
	}
}
