package com.howie.TheoreticalBasis;

import com.howie.TheoreticalBasis.Model.ThreadTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.*;

@SpringBootApplication
public class ThreadUse {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SpringApplication.run(ThreadUse.class, args);

        //1.互斥锁的实现
        //threadLockTest();

        //2.线程的使用方式
        useThreadTest();

    }

    /**
     * 线程的使用方式(3种)
     * 1.实现Runnable接口
     * 2.实现Callable接口
     * 3.继承Thread
     * 1和2并不算真正意义的线程，相当于一个线程中的任务，最终还是通过Thread调用
     * 因此，任务是由线程驱动来执行
     */
    private static void useThreadTest() throws ExecutionException, InterruptedException {
        //1.实现runnable接口
        class RunnableThread implements Runnable {
            @Override
            public void run() {
                System.out.println("Im runnable!");
            }
        }
        RunnableThread runnableThread = new RunnableThread();
        Thread thread1 = new Thread(runnableThread);
        thread1.start();

        //2.实现callable
        //继承callable调用call方法是可以有返回值的，返回值通过futureTask封装
        class CallableThread implements Callable {
            @Override
            public Object call() throws Exception {
                System.out.println("This is Callable!");
                Thread.sleep(10000);
                return "100";
            }
        }
        CallableThread callableThread = new CallableThread();
        FutureTask<Object> futureTask = new FutureTask<Object>(callableThread);
        Thread thread2 = new Thread(futureTask);
        thread2.start();
        //通过get方法获取返回值
        //❤调用futureTask会等待线程的返回值，等待过程中阻塞下方代码。
        System.out.println("future: " + futureTask.get());

        //3.继承Thread类
        class ThreadRun extends Thread {
            @Override
            public void run() {
                System.out.println("Thread is this?");
            }
        }
        new ThreadRun().start();

        /**
         * 当调用start时，jvm会把当前任务线程放在线程等待队列，等待被调度，调度时会执行run反复噶
         */


    }

    /**
     * 互斥锁的实现方式
     */
    private static void threadLockTest() {
        ThreadTest test = new ThreadTest();
        ExecutorService executorService = Executors.newCachedThreadPool();
        //1.synchronized线程互斥同步（仅同步此代码块）
        //executorService.execute(test::syncAdd);
        //executorService.execute(test::syncAdd);

        ThreadTest test1 = new ThreadTest();
        //2.依然会同步，因为把ThreadTest.class作为锁
        executorService.execute(test::syncObjectAdd);
        executorService.execute(test1::syncObjectAdd);
    }

}
