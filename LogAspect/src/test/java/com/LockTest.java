package com;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
	private Lock lock = new ReentrantLock();

	// 需要参与同步的方法
	public void method(Thread thread) {
		lock.lock();
		try {
			System.out.println("线程名" + thread.getName() + "获得了锁");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("线程名" + thread.getName() + "释放了锁");
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		LockTest lockTest = new LockTest();

		// 线程1
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				lockTest.method(Thread.currentThread());
			}
		}, "t1");

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				lockTest.method(Thread.currentThread());
			}
		}, "t2");

		t1.start();
		t2.start();
        List<String> a1 = Arrays.asList("a", "b", "c");

        for (String a : a1) {
            printValur(a);
        };

        a1.forEach(x -> lockTest.printValur(x));
        
        a1.forEach(LockTest::printValur);
        
        //new Thread().start();
        new Thread(lockTest::test1).start();

    }

    public static void printValur(String str) {
        System.out.println("print value : " + str);
    }
	
	public void test(String s) {
		//System.out::println();
		System.out.println("Hello " + s);
	}
	public void test1() {
		//System.out::println();
		System.out.println("Hello " );
	}
}
//执行情况：线程名t1获得了锁
//         线程名t1释放了锁
//         线程名t2获得了锁
//         线程名t2释放了锁