package com.william.thread;

import com.william.thread.pk.ThreadDemo;
import com.william.thread.pk.ThreadPoolDemo;

/**
 * User: William Cheng
 * Create Time: 2018/9/15 16:24
 * Description:
 */
public class ThreadPkPoolTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadDemo threadDemo = new ThreadDemo();
        ThreadPoolDemo threadPoolDemo = new ThreadPoolDemo();
        threadDemo.test();

        System.out.println("--------------------------");

        threadPoolDemo.test();
    }
}