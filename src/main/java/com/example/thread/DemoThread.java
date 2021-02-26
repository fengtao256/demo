package com.example.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class Demo extends Thread {

    @Override
    public void run() {
        super.run();
        log.info("线程运行。。。");
        // Perform time-consuming operation...
    }
}

@Slf4j
class MyRunnable implements Runnable {

    @Override
    public void run() {
        log.info("三无产品");
//        int a = 1/0 ;
    }

}

@Slf4j
public class DemoThread {
    public static void main(String[] args) {
        Demo t = new Demo();
        t.start();

        MyRunnable myRunnable = new MyRunnable() ;
        Thread thread = new Thread(myRunnable,"111") ;
        thread.start();
    }
}