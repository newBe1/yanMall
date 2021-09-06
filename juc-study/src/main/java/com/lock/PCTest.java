package com.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 生产者消费者问题 线程通知唤醒
 * @author: Ryan
 * @create: 2021-09-02 17:03
 **/
public class PCTest {
    public static void main(String[] args) {
        Data3 data = new Data3();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.planeA();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.planeB();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.planeC();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();

    }
}

//synchronized版
class Data{
    private int num = 0;

    public synchronized  void increment() throws InterruptedException {
        while (num>0){
            this.wait();

        }
        num ++;
        System.out.println(Thread.currentThread().getName() + "数字加1完毕  num =" + num);
        this.notifyAll();  //通知其他线程
    }

    public synchronized  void decrement() throws InterruptedException {
        while (num==0){
            this.wait();

        }
        num --;
        System.out.println(Thread.currentThread().getName() + "数字减1完毕  num =" + num);
        this.notifyAll();  //通知其他线程
    }
}

//Lock版
class Data2{
    private int num = 0;

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void increment() throws InterruptedException {
        lock.lock();

        try {
            while (num>0){     //必须用while 防止虚假唤醒
                condition.await();  //等待
            }
            num ++;
            System.out.println(Thread.currentThread().getName() + "数字加1完毕  num =" + num);
            condition.signalAll();  //通知其他线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() throws InterruptedException {
        lock.lock();

        try {
            while (num==0){
                condition.await();
            }
            num --;
            System.out.println(Thread.currentThread().getName() + "数字减1完毕  num =" + num);
            condition.signalAll();  //通知其他线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

//condition 精准唤醒
class Data3{
    private  int num = 0;
    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    public void planeA() throws InterruptedException {
        lock.lock();
        try {
            //业务流程 判断 -> 执行 -> 通知
            while (num != 0){
                condition1.await();
            }
            num = 1;
            System.out.println(Thread.currentThread().getName() + "执行 plane A");
            condition2.signal();  //唤醒2
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void planeB() throws InterruptedException {
        lock.lock();
        try {
            while (num != 1){
                condition2.await();
            }
            num = 2;
            System.out.println(Thread.currentThread().getName() + "执行 plane B");
            condition3.signal();  //唤醒3
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void planeC() throws InterruptedException {
        lock.lock();
        try {
            while (num != 2){
                condition3.await();
            }
            num = 0;
            System.out.println(Thread.currentThread().getName() + "执行 plane C");
            condition1.signal();  //唤醒1
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

}
