package com.assist;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @description: 信号量测试
 * @author: Ryan
 * @create: 2021-09-03 09:53
 **/
public class SemaphoreTest {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);  //设置3个车位

        for (int i = 0; i < 6; i++) {
            int temp = i;
            new Thread(()->{
                try {
                    semaphore.acquire();   //获取 信号量 -1 如果没有资源就等待其他线程释放再使用

                    System.out.println(Thread.currentThread().getName()+"抢到车位"+temp);
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(Thread.currentThread().getName()+"离开车位");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();  //释放 信号量 +1  唤醒等待的线程，其他线程就可以获取
                }
            },String.valueOf(i)).start();
        }
    }
}
