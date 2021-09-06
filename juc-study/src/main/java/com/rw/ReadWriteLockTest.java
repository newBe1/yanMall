package com.rw;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 读写锁测试
 * @author: Ryan
 * @create: 2021-09-03 10:06
 **/
public class ReadWriteLockTest {

    public static void main(String[] args) {
        MyCacheNoLock mycache = new MyCacheNoLock();

        //开启5个线程 写入数据
        for (int i = 1; i <=100 ; i++) {
            int finalI = i;
            new Thread(()->{
                mycache.put(String.valueOf(finalI),String.valueOf(finalI));
            }).start();
        }
        //开启10个线程去读取数据
        for (int i = 1; i <=10 ; i++) {
            int finalI = i;
            new Thread(()->{
                String o = mycache.get(String.valueOf(finalI));
            }).start();
        }
    }
}

//不加锁时 写入方法执行过程中可能会插入其他线程的写入无法保证原子性   读取线程相互交叉不改变数据值 故交叉不影响
class MyCacheNoLock{
    private volatile Map<String, String> map = new HashMap<>();   //使用 volatile 保证可见性

    //写入方法
    public void put(String key , String value){
        System.out.println(Thread.currentThread().getName()+" 线程 开始写入");
        map.put(key, value);
        System.out.println(Thread.currentThread().getName()+" 线程 写入OK");
    }

    //获取方法
    public String get(String key){
        System.out.println(Thread.currentThread().getName()+" 线程 开始读取");
        String result =  map.get(key);
        System.out.println(Thread.currentThread().getName()+" 线程 读取OK");
        return result;
    }

}