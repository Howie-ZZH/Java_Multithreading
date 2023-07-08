package com.example.java_multithreading;

public class ThreadTest {

    void syncAdd() {
        //synchronized线程互斥同步
        //同步方法
        synchronized (this) {
            for (int i = 0; i < 100; i++) {
                System.out.println(i);
            }
        }
    }

    void syncObjectAdd() {
        //synchronized线程互斥同步
        //同步这个类，当创建了不同对象的时候，不同对象调用此方法也会同步
        synchronized (ThreadTest.class) {
            for (int i = 0; i < 100; i++) {
                System.out.println(i);
            }
        }
    }

}
