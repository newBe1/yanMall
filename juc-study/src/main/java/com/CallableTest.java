package com;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @description: callable 创建线程
 * @author: Ryan
 * @create: 2021-09-02 18:04
 *
 * 与runnable不同之处
 * 1、有返回值
 * 2、可以抛出异常
 * 3、启动方式不同 用的是call()
 *
 **/
public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        for (int i = 0; i < 9; i++) {
            MyTest myTest = new MyTest();

            //启动线程都是用 Thread 但是 Thread只能传入 Runnable类 所以需要 FutureTask适配类
            FutureTask<String> futureTask = new FutureTask<String>(myTest);

            //启动线程
            new Thread(futureTask, String.valueOf(i)).start();

            //获取返回值
            String s = futureTask.get();
            System.out.println(s);
        }
    }


}

class MyTest implements Callable<String> {  //传入一个参数 返回相同类型值

    @Override
    public String call() throws Exception {
        return Thread.currentThread().getName();
    }
}
