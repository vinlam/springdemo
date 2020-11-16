package com;

public class ThreadVolatile {
	static volatile int num = 0;
	static volatile boolean flag = false;
	public static void main(String[] args) {
		Thread t1 = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//super.run();
				for(;100>num;) {
					if(!flag && (num == 0 || ++num % 2 == 0)) {
						try {
							Thread.sleep(100);//防止打印速度过快导致混乱
						}catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
						System.out.println("t1:"+num);
						flag = true;
					}
				}
			}
		};
		Thread t2 = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//super.run();
				for(;100>num;) {
					if(flag && (++num % 2 != 0)) {
						try {
							Thread.sleep(100);//防止打印速度过快导致混乱
						}catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
						System.out.println("t2:"+num);
						flag = false;
					}
				}
			}
		};
		
		t1.start();
		t2.start();
	}
}
