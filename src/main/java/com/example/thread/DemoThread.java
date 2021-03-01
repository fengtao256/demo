package com.example.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

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

public class DemoThread {
    public static void main(String[] args) {
        Demo t = new Demo();
        t.start();

        MyRunnable myRunnable = new MyRunnable() ;
        Thread thread = new Thread(myRunnable,"111") ;
        thread.start();
        ExecutorService service = Executors.newFixedThreadPool(3) ;
        FutureTask ft1 = new FutureTask(new T1()) ;
        FutureTask ft2 = new FutureTask(new T2()) ;
        service.submit(ft1) ;
        service.submit(ft2) ;
        service.shutdown();
        try {
            System.out.println(ft2.get());
            System.out.println(ft1.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class T1 implements Callable {


    @Override
    public Object call() throws Exception {
        Thread.sleep(3000);
        return "哈哈哈哈";
    }
}

class T2 implements Callable {


    @Override
    public Object call() throws Exception {
        Thread.sleep(1000);
        return "嘿嘿嘿";
    }
}