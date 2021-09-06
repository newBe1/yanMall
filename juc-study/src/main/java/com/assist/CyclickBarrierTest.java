package com.assist;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @description: 加法计数器
 * @author: Ryan
 * @create: 2021-09-02 18:20
 **/
public class CyclickBarrierTest {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{
            System.out.println("集齐了7颗龙珠 可以召唤神龙了！");
        });

        for (int i = 1; i < 9; i++) {
            int temp = i;

            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"收集到第 "+ temp + "颗龙珠");

                try {
                    cyclicBarrier.await();          //加 1 等待

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();

        }


    }
}
