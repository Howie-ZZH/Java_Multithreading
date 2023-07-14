package com.howie.TheoreticalBasis;


import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 此类简单展示了一些实例
 */
public class ThreadLocking {

    public static void main(String[] args) {

        //乐观锁和悲观锁示例
        optimisticAndPessimistic();

    }

    //此方法示例乐观锁和悲观锁
    public static void optimisticAndPessimistic() {

        //悲观锁 synchronized和Lock
        synchronized(ThreadLocking.class) {
            System.out.println("Pessimistic locking: synchronized");
        }

        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        System.out.println("Pessimistic locking: Lock");


        //乐观锁 Atomic相关类
        AtomicInteger integer = new AtomicInteger();
        int i = integer.addAndGet(1);
        System.out.println("Optimistic locking: " + i);


    }
}


