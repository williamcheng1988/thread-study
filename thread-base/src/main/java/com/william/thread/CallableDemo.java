package com.william.thread;

import java.util.concurrent.Callable;

/**
 * User: William Cheng
 * Create Time: 2018/9/15 11:08
 * Description:
 */
public class CallableDemo implements Callable<String> {


    @Override
    public String call() throws Exception {

        return "william===>Callable demo test ...";
    }
}