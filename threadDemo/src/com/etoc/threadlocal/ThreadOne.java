package com.etoc.threadlocal;

/**
 * ThreadLoca提高一个线程的局部变量，访问某个线程拥有自己局部变量
 * 
 * @author Admin
 */
public class ThreadOne {
	public static void main(String[] args) {
		Res res1=new Res();
		Res res2=new Res();
		Res res3=new Res();
		ThreadLoca t1=new ThreadLoca(res1);
		ThreadLoca t2=new ThreadLoca(res2);
		ThreadLoca t3=new ThreadLoca(res3);
		t1.start();
		t2.start();
		t3.start();
	}
}

class ThreadLoca extends Thread {
	private Res res;

	public ThreadLoca(Res res) {
		this.res = res;
	}

	public void run() {
    	for (int i = 0; i < 3; i++) {
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(getName()+"---->i:"+i+"订单号:"+res.getNum());
		}
    };
}

class Res {
	private int count = 0;

	public int getNum() {
		count = count + 1;
		return count;
	}
}