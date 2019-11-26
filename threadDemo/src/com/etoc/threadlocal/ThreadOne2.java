package com.etoc.threadlocal;

/**
 * ThreadLoca提高一个线程的局部变量，访问某个线程拥有自己局部变量
 * 
 * @author Admin
 */
public class ThreadOne2 {
	public static void main(String[] args) {
		Res1 res1=new Res1();
		ThreadLoca1 t1=new ThreadLoca1(res1);
		ThreadLoca1 t2=new ThreadLoca1(res1);
		ThreadLoca1 t3=new ThreadLoca1(res1);
		t1.start();
		t2.start();
		t3.start();
	}
}

class ThreadLoca1 extends Thread {
	private Res1 res;

	public ThreadLoca1(Res1 res) {
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

class Res1 {
	private ThreadLocal<Integer>  count = new ThreadLocal<Integer>() {
		protected Integer initialValue() {
			return 0;
		}
	};

	public int getNum() {
		Integer count =this.count.get()+1;
		this.count.set(count);
		return count;
	}
}