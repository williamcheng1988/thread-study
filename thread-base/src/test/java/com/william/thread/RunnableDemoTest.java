package com.william.thread;

import org.junit.Test;

/**
 * User: William Cheng
 * Create Time: 2018/9/15 11:08
 * Description:
 */

public class RunnableDemoTest {

    @Test
    public void test() {
        Thread thread = new Thread(new RunnableDemo());
        thread.start();
    }

}