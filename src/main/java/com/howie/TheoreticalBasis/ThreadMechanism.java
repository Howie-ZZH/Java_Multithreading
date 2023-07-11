package com.howie.TheoreticalBasis;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadMechanism {
    public static void main(String[] args) {

        //三种Executors的创建
        executors();

        //守护线程，在其他线程死后，守护进程一般存活时间为60s(以定义的KeepAlive为准)
        daemon();

        yieldTest();


    }

    /**
     * yield()方法示例
     */
    private static void yieldTest() {
        Thread thread1 = new Thread(new RunnableYieldThread("RunnableYieldThread1"));
        Thread thread2 = new Thread(new RunnableYieldThread("RunnableYieldThread2"));

        //执行调度器放入两个任务
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(thread1);
        executorService.execute(thread2);


    }

    /**
     * 守护线程示例
     */
    private static void daemon() {
        //守护进程
        Thread thread = new Thread(new RunnableThreadDaemon());
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * 三种Executors的创建
     */
    private static void executors() {
        //1.CachedThreadPool每个加入的任务都会创建一个线程，
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        //2.FixedThreadPool创建自定义线程池线程数量的线程池
        int num = 10;
        Executors.newFixedThreadPool(num);
        //3.SingleThreadExecutor相当于传参为1的FixedThreadPool
        Executors.newSingleThreadExecutor();


        cachedThreadPool.execute(new RunnableThread());
    }

}
class RunnableThread implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(50000);
            String name = Thread.currentThread().getName();
            System.out.println("Im runnable" + name + "!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

class RunnableThreadDaemon implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
                System.out.println("Im RunnableThreadDaemon!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class RunnableYieldThread implements Runnable {

    public RunnableYieldThread(String name) {
        setThreadName(name);
    }

    private String name;

    private void setThreadName(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        int num = 0;
        try {
            while (true) {

                //num每次+1
                num++;
                //每5秒输出一次
                Thread.sleep(5000);
                System.out.println("Im runnable" + name + "!" + " Num: " + num);

                //num为5时暗示线程调度器放弃CPU时间片
                if (num == 5) {
                    break;
                }
            }
            System.out.println("Im runnable" + name + "!" + "Before yield!");
            Thread.yield();
            System.out.println("Im runnable" + name + "!" + "After yield!");


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
