package com.example.io;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StreamIO {
    public static void main(String args[]) throws IOException, ParseException {
//        String c;
//        // 使用 System.in 创建 BufferedReader
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        System.out.println("输入字符, 按下 'q' 键退出。");
//        // 读取字符
//        do {
//            c = br.readLine();
//            System.out.println(c);
//        } while (!c.equals("q"));
        System.out.println();
        System.out.println(new SimpleDateFormat("yyyy:MM:dd HH:mm:ss").format(new SimpleDateFormat("yyyyMMddHHmmss").parse("20191122112211")));
    }
}