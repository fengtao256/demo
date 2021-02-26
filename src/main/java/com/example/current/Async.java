package com.example.current;

import org.springframework.retry.backoff.Sleeper;

import java.util.concurrent.atomic.AtomicInteger;

public class Async {
    private static AtomicInteger count = new AtomicInteger(0) ;

    private static int count1 = 0 ;
    public static void main(String[] args) throws InterruptedException {
        async() ;
        System.out.println("线程");
        async() ;
        Thread.sleep(5000);
        System.out.println(count1);
    }

    public static synchronized void async(){
        //异步执行
        new Thread(() -> {

            long start = System.currentTimeMillis() ;
            for(int i = 0 ; i < 10000 ; i ++ ){
              for(int j = 0 ; j < 10000 ; j ++ ){
//                  count.incrementAndGet() ;
                count1++ ;
              }
            }
            long end = System.currentTimeMillis() ;
            System.out.println("线程执行完毕,"+"耗时："+(end-start)+"ms");
        }).start();
        System.out.println("执行完毕");
    }
}
