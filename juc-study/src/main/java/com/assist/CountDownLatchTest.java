package com.assist;

import java.util.concurrent.CountDownLatch;

/**
 * @description: 减法计数器测试
 * @author: Ryan
 * @create: 2021-09-02 18:15
 *
 * countDown 减一操作
 * await 等待计数器归零
 **/
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);  //设置总数

        for (int i = 0; i < 6; i++) {
           new Thread(()->{
               System.out.println(Thread.currentThread().getName() + "走了");
               countDownLatch.countDown();  //-1
           },String.valueOf(i)).start();

        }
        countDownLatch.await();  //计数器归零 执行下面的代码  不加就直接执行 先输出 没人了

        System.out.println("没人了");

    }
}
