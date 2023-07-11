package com.howie.TheoreticalBasis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadWorkTogether {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new CodeThread());
        executorService.execute(new GItCommitThread());

    }
}

class Coder {

    private static volatile boolean codeFinish = false;

    public synchronized void code() throws InterruptedException {
        while (true) {

            while (codeFinish) {
                wait();
            }

            System.out.println("写代码...");
            Thread.sleep(500);
            System.out.println("代码完成，等待提交...");
            codeFinish = true;

        }
    }

    public synchronized void gitCommit() throws InterruptedException {
        while (true) {
            while (codeFinish) {
                System.out.println("提交代码...");
                Thread.sleep(500);
                System.out.println("等待下一段代码...");
                codeFinish = false;

            }
        }
    }

}

class CodeThread implements Runnable {
    @Override
    public void run() {
        Coder coder = new Coder();
        try {
            coder.code();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class GItCommitThread implements Runnable {
    @Override
    public void run() {
        Coder coder = new Coder();
        try {
            coder.gitCommit();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}