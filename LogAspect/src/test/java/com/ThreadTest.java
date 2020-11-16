package com;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ThreadTest {
	public static void main(String[] args) {
		MyThread myThread = new MyThread();
		myThread.start();
		try{
			//Thread.sleep(10000);
			TimeUnit.SECONDS.sleep(10);//更好可读性
		}catch(InterruptedException e){
			e.printStackTrace();
			System.out.println("阻断线程");
		}
		myThread.interrupt();
	}
}

class MyThread extends Thread{
	@Override
	public void run() {
		while(true){
			System.out.println(new Date());
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){
				System.out.println("当前阻断线程");
				return;//此处若无return将无限循环执行
			}
		}
		
	}
}