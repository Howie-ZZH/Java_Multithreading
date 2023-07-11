package com.howie.TheoreticalBasis;

import java.util.concurrent.*;

public class ThreadInterrupt {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        //Thread的Interrupt
        //interruptThread();

        //Executors的Interrupt
        //executorsInterrupt();

        //Executor中的单个线程控制示例
        executorsInterruptSingleControl();

    }

    private static void executorsInterruptSingleControl() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> submit = executorService.submit(new RunnableInterruptExecutorsSubmitThread());
        submit.cancel(true);
        System.out.println("Is cancelled: " + submit.isCancelled());
        System.out.println("返回值: " + submit.get());
        System.out.println("是否执行完成" + submit.isDone());
    }

    /**
     * Executors的Interrupt
     * 此示例是控制整个Executor中的线程
     */
    private static void executorsInterrupt() throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new RunnableInterruptExecutorsThread2());
        //需要等待线程全部执行完才关闭
        executorService.shutdown();

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(new RunnableInterruptExecutorsThread3());
        //等待50s后，调用shutdownNow方法，相当于每个线程都Interrupt
        Thread.sleep(3000);
        cachedThreadPool.shutdownNow();


    }

    /**
     * Thread interrupt
     */
    private static void interruptThread() throws InterruptedException {
        Thread thread = new Thread(new RunnableInterruptThread());
        thread.start();
        Thread.sleep(5000);
        thread.interrupt();
    }
}

class RunnableInterruptThread implements Runnable {
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            System.out.println("进入While循环...");
        }
        System.out.println("当前中断状态：" + Thread.interrupted());
        Thread.currentThread().interrupt();
        System.out.println("当前中断状态：" + Thread.interrupted());
    }
}

class RunnableInterruptExecutorsThread implements Runnable {
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("进入While循环...");
        }
        System.out.println("当前中断状态：" + Thread.interrupted());
        Thread.currentThread().interrupt();
        System.out.println("当前中断状态：" + Thread.interrupted());
    }
}

class RunnableInterruptExecutorsThread2 implements Runnable {
    @Override
    public void run() {
        System.out.println("当前中断状态：" + Thread.interrupted());
        Thread.currentThread().interrupt();
        System.out.println("当前中断状态：" + Thread.interrupted());
    }
}

class RunnableInterruptExecutorsThread3 implements Runnable {
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            System.out.println("进入While循环...");
        }
        System.out.println("当前中断状态：" + Thread.interrupted());
        Thread.currentThread().interrupt();
        System.out.println("当前中断状态：" + Thread.interrupted());
    }
}

class RunnableInterruptExecutorsSubmitThread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        while (!Thread.interrupted()) {
            //System.out.println("进入While循环...");
        }
        System.out.println("当前中断状态：" + Thread.interrupted());
        return 1;
    }
}