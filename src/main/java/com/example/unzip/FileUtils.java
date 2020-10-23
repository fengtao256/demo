package com.example.unzip;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtils {
    public static String ReadFile(String Path){
        BufferedReader reader = null;
        String laststr = "";
        try{
            FileInputStream fileInputStream = new FileInputStream(Path);
            String fileCode = EncodingDetect.getJavaEncode(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, fileCode);
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine()) != null){
                laststr += tempString;
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }

    public static void main(String[] args){
        String filePath = "F:/123/data.json";
        String jsonContent = FileUtils.ReadFile(filePath);
        System.out.println(jsonContent);
//		List<Student> list = JSON.parseArray(jsonContent,Student.class);
    }
}
