package com.howie.TheoreticalBasis;

public class ThreadSynchronized {

    public static void main(String[] args) {

        //对象锁测试:Synchronized修饰方法和Synchronized代码块
        synchronizedObjectTest();
        //修饰static和Class锁
        synchronizedClassAndStatic();

    }

    /**
     *
     */
    private static void synchronizedClassAndStatic() {
        SynchronizedObject synchronizedObject = new SynchronizedObject();
        //新建两个线程
        Thread thread1 = new Thread(() -> {
            try {
                synchronizedObject.objectSync3();
                synchronizedObject.objectSync4();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                synchronizedObject.objectSync3();
                synchronizedObject.objectSync4();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread1.start();
        thread2.start();
    }

    /**
     * objectSync1()和objectSync2()使用的是同一把锁this（也就是synchronizedObject对象）
     * 当执行objectSync1时，objectSync2无法执行
     * 分析：1.当第一个线程执行完objectSync1()后释放锁 2.两个线程再次争抢锁(此时第一个线程抢到锁执行objectSync2()，另一个线程抢到锁执行objectSync1())
     */
    private static void synchronizedObjectTest() {
        SynchronizedObject synchronizedObject = new SynchronizedObject();
        //新建两个线程
        Thread thread1 = new Thread(() -> {
            try {
                synchronizedObject.objectSync1();
                synchronizedObject.objectSync2();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                synchronizedObject.objectSync1();
                synchronizedObject.objectSync2();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread1.start();
        thread2.start();

    }

}

class SynchronizedObject {

    //修饰普通方法，默认锁对象是this
    public synchronized void objectSync1() throws InterruptedException {
        System.out.println("objectSync1");
        Thread.sleep(500);
        System.out.println("objectSync1-off");
    }

    //修饰普通方法，默认锁对象是this
    public synchronized void objectSync2() throws InterruptedException {
        System.out.println("objectSync2");
        Thread.sleep(500);
        System.out.println("objectSync2-off");
    }

    //使用synchronized代码块与objectSync1 objectSync2类似
    public synchronized void objectSync3() throws InterruptedException {
        synchronized (this) {
            System.out.println("objectSync3");
            Thread.sleep(500);
            System.out.println("objectSync3-off");
        }
    }

    //使用synchronized代码块与objectSync1 objectSync2类似
    public  void objectSync4() throws InterruptedException {
        synchronized(this) {
            System.out.println("objectSync4");
            Thread.sleep(500);
            System.out.println("objectSync4-off");
        }
    }


    //使用类锁class
    public void classSync() throws InterruptedException {
        synchronized(ThreadSynchronized.class) {
            System.out.println("classSync");
            Thread.sleep(500);
            System.out.println("classSync-off");
        }
    }

    //Synchronized修饰静态方法
    public static void staticMethodSync() throws InterruptedException {
        System.out.println("staticSync");
        Thread.sleep(500);
        System.out.println("staticSync-off");
    }

}

