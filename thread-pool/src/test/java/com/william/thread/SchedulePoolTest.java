package com.william.thread;

/**
 * User: William Cheng
 * Create Time: 2018/9/15 15:02
 * Description:
 */
public class SchedulePoolTest {

    public static void main(String[] args) {
        SchedulePoolDemo demo = new SchedulePoolDemo();
        demo.submit();
        demo.schedule();
        demo.scheduleWithFixedDelay(5);
        demo.scheduleAtFixedRate(1);
    }
}