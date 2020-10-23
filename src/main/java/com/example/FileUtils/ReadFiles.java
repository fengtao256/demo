package com.example.FileUtils;

import java.io.File;

public class ReadFiles {
    public static void main(String[] args) {
        File[] files = new File("D:\\complay-file\\公司项目\\医疗大数据\\bookimages").listFiles() ;
        for (File file :files ) {
            if(file.isFile()){
                System.out.print("'"+file.getName()+"',");
            }
        }
    }
}
