package com.example.JavaExp;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Test {
    public static void main(String[] args) throws Exception {
        String f = "D:/a.txt" ;
        double q[] = {1.23,69.88,34.45,67.98,2345.67} ;
        FileOutputStream fos = new FileOutputStream(f) ;
        for (int i = 0; i < q.length; i++) {
            fos.write((char)q[i]);
        }
    }
}
class Test1 {
    public static void main(String[] args) throws Exception {
        String n = "asdasdasdasdasd" ;
        BufferedWriter bw = new BufferedWriter(new FileWriter("D:/b.txt"));
        bw.write(n);
        bw.close();
    }
}
class Test2 {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://www.sina.com.cn");
        InputStream is = url.openStream() ;
        is = new BufferedInputStream(is) ;
        Reader reader = new InputStreamReader(is) ;
        FileOutputStream fos = new FileOutputStream("D:/c.txt");
        int c = 0 ;
        while((c = reader.read()) != -1) {
            fos.write(c);
        }
    }
}