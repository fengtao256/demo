package com.example.demo;

import java.util.Scanner;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName Test.java
 * @Description TODO
 * @createTime 2021年02月24日 15:23:00
 */

public class Test {

    private static UserRelCache cache;

    static {
        long start = System.currentTimeMillis();
        cache = new UserRelCache(1000000);
        System.out.println("初始化用户构建完毕，花费时间："+( System.currentTimeMillis()-start )+ " ms");
    }

    public static void main(String[] args) {
        new Scanner(System.in).next() ;
        Test t = new Test() ;
        t.test() ;
        //相互关注
        System.out.println("adasda ");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                cache.addConcern(i, j);
            }
        }
        //相互取消
        System.out.println("concernList" + cache.getConcernList(10));
        System.out.println("fansList" + cache.getFansList(20));
        for (int i = 0; i < 90; i++) {
            for (int j = 0; j < 90; j++) {
                cache.removeConcern(i, j);
            }
        }
        System.out.println("-----取消部分关注后-----");
        System.out.println("concernList" + cache.getConcernList(10));
        System.out.println("fansList" + cache.getFansList(20));

        System.out.println(System.currentTimeMillis() - start + " ms");
    }

    public void test(){
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("=======");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }) ;
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.start();

    }
}
