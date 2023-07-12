package com.howie.TheoreticalBasis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadWorkTogether {
    public static void main(String[] args) {

        //Thread join()测试，
        threadJoinTest();

        //wait() notify() notifyAll()测试
        waitAndNotifyTest();

    }

    /**
     * 测试wait和notify方法
     */
    private static void waitAndNotifyTest() {
        Coder coder = new Coder();
        ExecutorService executorService = Executors.newCachedThreadPool();

        //开启三个wait()线程
        executorService.execute(() -> {
            try {
                coder.gitCommit2();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        executorService.execute(() -> {
            try {
                coder.gitCommit3();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        executorService.execute(() -> {
            try {
                coder.gitCommit();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        //开启一个notify线程
        executorService.execute(() -> {
            try {
                coder.code();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * thread join()方法的测试
     */
    private static void threadJoinTest() {
        CodeThread code = new CodeThread();
        GItCommitThread commit = new GItCommitThread(code);

        commit.start();
        code.start();
    }
}



class Coder {

    public synchronized void code() throws InterruptedException {

        System.out.println("写代码...");
        Thread.sleep(500);
        System.out.println("代码完成，等待提交...");
        //唤醒一个线程
        notify();
        //唤醒所有线程
        notifyAll();
    }

    public synchronized void gitCommit() throws InterruptedException {

        wait();
        System.out.println("提交代码...");
        Thread.sleep(500);
        System.out.println("等待下一段代码...");

    }

    public synchronized void gitCommit2() throws InterruptedException {

        wait();
        System.out.println("提交代码2...");
        Thread.sleep(500);
        System.out.println("等待下一段代码2...");

    }

    public synchronized void gitCommit3() throws InterruptedException {

        wait();
        System.out.println("提交代码3...");
        Thread.sleep(500);
        System.out.println("等待下一段代码3...");

    }
}

class CodeThread extends Thread {
    @Override
    public void run() {
        Coder coder = new Coder();
        try {
            Thread.sleep(5000);
            coder.code();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class GItCommitThread extends Thread {

    private CodeThread codeThread;
    GItCommitThread(CodeThread codeThread) {
        this.codeThread = codeThread;
    }

    @Override
    public void run() {
        Coder coder = new Coder();
        try {
            codeThread.join();
            coder.gitCommit();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}