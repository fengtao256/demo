package com.example.HelloWord;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class FileCopy {

    public static void main(String[] args) throws IOException {
        System.out.println("fileseprator==  "+File.separator+System.getProperty("os.name")+
                UUID.randomUUID().toString().replace("-", ""));
//        String src = "D:/test/dist.zip";//需要拷贝的源文件路径
//        String dest = "D:/test/test--7.zip";//需要拷贝到指定位置的路径
//        fileCopy(src, dest);
        File file = new File("D:\\迅雷下载/test.txt") ;
        String str = ((Object)file.getAbsolutePath()).toString().split("迅雷下载")[1] ;
        String str1 = str;
        System.out.println(str+"\n"+str1);
    }

    public static void fileCopy(String src, String dest) throws IOException {
        //1.创建IO流操作对象
        FileInputStream fis = new FileInputStream(src);
        FileOutputStream fos = new FileOutputStream(dest);

        //2.边读边写
        byte[] b = new byte[1024];
        int len;
        while ((len = fis.read(b)) != -1) {
            fos.write(b, 0, len);
        }

        //3.关闭流   注意：如果有多个流，先开的后关
        fos.close();
        fis.close();
    }
}