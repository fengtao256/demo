package com.example.unzip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Unzip{

    /**
     *
     * @Title: unZipFiles
     * @Description:
     * @author: FengTao
     * @date 2020年9月8日 下午2:19:57
     * @param zipDir 待解压的zip文件路径
     * @param descDir 指定目录
     * @throws IOException void
     * @version
     */
    public static void unZipFiles02(String zipDir, String descDir) throws IOException {
        try {
            String fileCode = EncodingDetect.getJavaEncode( zipDir ); //获取编码格式
            File zipFile = new File( zipDir ) ;
            //开始构建
            ZipFile zip = new ZipFile(zipFile, Charset.forName( fileCode ));//解决中文文件夹乱码,构件zip输入流
            ZipEntry entry = null;
            File file = null;
            for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {
                entry = (ZipEntry) entries.nextElement();
                if (!entry.isDirectory()) {
                    InputStream in = zip.getInputStream(entry);
                    // 判断路径是否存在,不存在则创建文件路径
                    file = new File(descDir , entry.getName());
                    if (!file.exists()) {
                        new File(file.getParent()).mkdirs();//创建此文件的上级目录
                    }
                    // 输出文件路径信息
                    FileOutputStream out = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = in.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }
                    in.close();
                    out.close();
                }
            }
            zip.close();
            System.out.println("******************解压完毕********************");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("******************解压文件出错********************");
        }
        return;
    }


    /**
     * 解压文件到指定目录
     * 解压后的文件名，和之前一致
     * @param zipFile   待解压的zip文件
     * @param descDir   指定目录
     */
    public static void unZipFiles(File zipFile, String descDir) throws IOException {
        try {
            ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));//解决中文文件夹乱码
            String name = zip.getName().substring(zip.getName().lastIndexOf(File.separator)+1, zip.getName().lastIndexOf('.'));
            //System.out.println("+++++++++++"+name);
            File pathFile = new File(descDir+name);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                InputStream in = zip.getInputStream(entry);
                String outPath = (descDir + name +"/"+ zipEntryName).replaceAll("\\*", "/");

                // 判断路径是否存在,不存在则创建文件路径
                File file = new File(outPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if (new File(outPath).isDirectory()) {
                    System.out.println(outPath);
                    continue;
                }
                // 输出文件路径信息
                FileOutputStream out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[1024];
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
                in.close();
                out.close();
            }
            System.out.println("******************解压完毕********************");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    //测试
    public static void main(String[] args) {
        try {
            unZipFiles(new File("D:/phpwind9.0mobankaifashouce.zip"), "E:/test/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}