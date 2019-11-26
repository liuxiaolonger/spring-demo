package com.etoc.test1;

/**
 * 线程基础
 * 
 * @author Admin 进程：进程是线程的集合 线程：线程就是一条执行路径（线程是为了提高效率） GC垃圾回收机制 main函数是主线程
 */
public class ThreadOne {
	public static void main(String[] args) {
		Res res = new Res();
		ThreadIput input = new ThreadIput(res);
		ThreadOut out = new ThreadOut(res);
		input.start();
		out.start();

	}
}

class Res {
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
class ThreadIput extends Thread {
	public Res res;

	public ThreadIput(Res res) {
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
				if (count == 0) {
					res.name = "王小龙";
					res.sex = "男";
				} else {
					res.name = "周乐乐";
					res.sex = "女";
				}
				count = (count + 1) % 2;
				res.bool=true;
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
class ThreadOut extends Thread {
	public Res res;

	public ThreadOut(Res res) {
		this.res = res;
	}

	/*
	 * 线程任务的执行体
	 * 
	 */
	@Override
	public void run() {
		while (true) {
			if(res.bool) {
				synchronized (res) {
					System.out.println(res.name + res.sex);
					res.bool=false;
				}
			}
			
		}
	}
}
