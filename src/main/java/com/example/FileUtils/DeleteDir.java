package com.example.FileUtils;

import java.io.File;

public class DeleteDir {

    public static void main(String[] args) {
        String uploadZipPath = null ;
        if(System.getProperty("os.name").startsWith("Win")){
            System.out.println("FileConfig.zipAddr");
        }else{
            System.out.println("FileConfig.zipAddr11111");
        }
        //removeAllByDir(new File("D:\\sunsheen\\天气雷达\\test"));
    }
    private static void removeAllByDir(File dir) {
        File[] files=dir.listFiles();
        for(File file:files){
            if(file.isDirectory()){
                removeAllByDir(file);
            }else{
                System.out.println("delete "+file+":"+file.delete());
            }
        }
        System.out.println("delete "+dir+":"+dir.delete());
    }
}
