package com.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 传统Synchronized锁
 * @author: Ryan
 * @create: 2021-09-02 16:23
 **/
public class AddLockTest {
    public static void main(String[] args) {

        Ticket2 ticket = new Ticket2();
        new Thread(()->{
            for(int i=0;i<40;i++){
                ticket.sale();
            }
        },"A").start();
        new Thread(()->{
            for(int i=0;i<40;i++){
                ticket.sale();
            }
        },"B").start();
        new Thread(()->{
            for(int i=0;i<40;i++){
                ticket.sale();
            }
        },"C").start();
    }

}

//synchronized 版
class Ticket{
    private int num = 50;

    public synchronized void sale(){
        if(num > 0){
            num --;
            int temp = num;
            System.out.println(Thread.currentThread().getName() + "卖出了第" + (50-temp) + "张票；还剩"+ num +"张票");
        }
    }
}

//Lock版
class Ticket2{
    private int num = 50;
    Lock lock = new ReentrantLock();  //可重入锁  默认是非公平锁 可通过参数改为公平锁

    public void sale(){

        //加锁
        lock.lock();
        try {

            //业务代码 必须 try catch
            if(num > 0){
                num --;
                int temp = num;
                System.out.println(Thread.currentThread().getName() + "卖出了第" + (50-temp) + "张票；还剩"+ num +"张票");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            //解锁
            lock.unlock();
        }
    }
}
