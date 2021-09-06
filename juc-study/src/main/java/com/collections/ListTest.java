package com.collections;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @description: 集合测试
 * @author: Ryan
 * @create: 2021-09-02 17:42
 **/
public class ListTest {

    public static void main(String[] args) {
        //List<String> list = new ArrayList<>(); //java.util.ConcurrentModificationException 多线程修改异常
        //List<String> list = new Vector<>(); //vector可以解决  因为add方法加了synchronized关键字

        //List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<String> list = new CopyOnWriteArrayList<>();  //list线程安全类 juc中使用这个 写入时复制  使用了Lock锁
        Set<String> set = new CopyOnWriteArraySet<>();         //set线程安全类 同理list
        Map<String, String> map = new ConcurrentHashMap<>();   //map线程安全类 加入了分段锁

        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(list);
            },String.valueOf(i)).start();

        }

    }
}
