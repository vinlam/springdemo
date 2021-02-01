package com;

//作用于同步代码块
//为什么要同步代码块呢？在某些情况下，我们编写的方法体可能比较大，同时存在一些比较耗时的操作，而需要同步的代码又只有一小部分，
//如果直接对整个方法进行同步操作，可能会得不偿失，此时我们可以使用同步代码块的方式对需要同步的代码进行包裹，这样就无需对整个方法进行同步操作了
public class SynchronizedStaticTest2 implements Runnable {
		    static SynchronizedStaticTest2 instance=new SynchronizedStaticTest2();
		    static int i=0;
		    @Override
		    public void run() {
		        //省略其他耗时操作....
		        //使用同步代码块对变量i进行同步操作,锁对象为instance
		        synchronized(instance){
		            for(int j=0;j<10000;j++){
		                i++;
		            }
		        }
		    }
		    public static void main(String[] args) throws InterruptedException {
		        Thread t1=new Thread(instance);
		        Thread t2=new Thread(instance);
		        t1.start();
		        t2.start();
		        t1.join();
		        t2.join();
		        System.out.println(i);
		    }
		}
