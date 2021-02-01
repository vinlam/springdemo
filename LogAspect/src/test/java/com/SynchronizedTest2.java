package com;
//一个线程获取了该对象的锁之后，其他线程来访问其他非synchronized,当线程1还在执行时，线程2也执行了，所以当其他线程来访问非synchronized修饰的方法时是可以访问的
public class SynchronizedTest2 {
    public synchronized void method1() {
        System.out.println("Method 1 start");
        try {
            System.out.println("Method 1 execute");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 1 end");
    }

    public void method2() {
        System.out.println("Method 2 start");
        try {
            System.out.println("Method 2 execute");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 2 end");
    }


    public static void main(String[] args) {
        final SynchronizedTest2 test = new SynchronizedTest2();

        new Thread(test::method1).start();

        new Thread(test::method2).start();
        
//        Method 1 start
//        Method 1 execute
//        Method 2 start
//        Method 2 execute
//        Method 2 end
//        Method 1 end
    }

}

