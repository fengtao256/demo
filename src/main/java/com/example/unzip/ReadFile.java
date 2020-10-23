package com.example.unzip;

import java.io.*;

public class ReadFile {

    public static void readFileByLines(String fileName,String writePath) throws java.io.IOException {
        //fileName原始文件路径，writePath现在路径
        EncodingDetect encodingDetect = new EncodingDetect();
        String fileCode = encodingDetect.getJavaEncode(fileName);
        StringBuffer buffer = new StringBuffer();
        BufferedReader fr;
        String line_separator = System.getProperty("line.separator");
        try {
            String myCode = fileCode != null && !"".equals(fileCode) ? fileCode : "UTF-8";
            InputStreamReader read = new InputStreamReader(new FileInputStream(fileName), myCode);
            fr = new BufferedReader(read);
            String line = null;
            while ((line = fr.readLine()) != null) {
                buffer.append("<xmp>"+line+"</xmp>");
            }
            fr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(buffer.toString());
        encodingDetect.writeFile(writePath, buffer.toString(), fileCode);
    }
    public static void main(String[] args) throws java.io.IOException {
        // TODO Auto-generated method stub
//        String fileName = "E:\\test\\phpwind9.0mobankaifashouce\\phpwind9.0模板开发手册/index.html";
//        String writepath = "E:\\test\\phpwind9.0mobankaifashouce\\phpwind9.0模板开发手册/index123456.html";
        // readFileByBytes(fileName);
        // readFileByChars(fileName);
//        readFileByLines(fileName,writepath);
        File file = new File("F:"+File.separator+"HK-workspace\\JBOSS\\JBoss4.2-hkks\\server\\default\\.\\deploy\\HKKnowledgeStudio" +
                ".war\\TaskFile\\QuickLabels\\78c15943d7d8415997acc05f653c23a0\\source\\新建文本文档 (2).txt") ;
        // readFileByRandomAccess(fileName);
    }
//
//    public static void readFileByLines(String fileName,String writepath)
//            throws java.io.IOException {
//        ReadFile readFile = new ReadFile();
//        String textHtml = "";
//        StringBuffer textHtml1 = new StringBuffer();
//			/*fileName = java.net.URLDecoder.decode(fileName, "utf-8");
//			fileName=new String(fileName.getBytes("ISO-8859-1"),"UTF-8");*/
//        //System.out.println("========fileName"+fileName);
//        File file = new File(fileName);
//        BufferedReader reader = null;
//        try {
//            System.out.println("以行为单位读取文件内容，一次读一整行：");
//            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
//            String tempString = null;
//            // 一次读入一行，直到读入null为文件结束
//            while ((tempString = reader.readLine()) != null) {
//                textHtml1.append(("<xmp>"+tempString+"</xmp>"));
//            }
//
//            textHtml = ""+textHtml1.toString();//"<xmp>"+tempString+"</xmp>";
//            //System.out.println("+++++++++++++++++" + textHtml);
//            //textHtml = new String(textHtml.getBytes("GBK"),"UTF-8");
//            //System.out.println("+++++++++++++++++" + textHtml);
//            reader.close();
//        } finally {
//            if (reader != null) {
//                reader.close();
//            }
//        }
//        readFile.writerFile(writepath, textHtml);
//        }
//
//
//    //替换为html符号
//    public String changeToHtml(String text) {
//        String color = "#00688B";
//        text = text.replace("&", "&");
//        text = text.replace(" ", "&nbsp;&nbsp;");
//        text = text.replace("<", "<");
//        text = text.replace(">", ">");
//        text = text.replace(" ", "&nbsp;&nbsp;");
//        text = text.replace("	", "&#9;&#9;");
//        text = text.replace("public", "<b><font color='" + color
//                + "'>public</font></b>");
//        text = text.replace("class", "<b><font color='" + color
//                + "'>class</font></b>");
//        text = text.replace("static", "<b><font color='" + color
//                + "'>static</font></b>");
//        text = text.replace("void", "<b><font color='" + color
//                + "'>void</font></b>");
//        String t = text.replace("//", "<font color=green>//");
//        if (!text.equals(t)) {
//            text = t + "</font>";
//        }
//        return text;
//    }
//
//    //输出文件
//    public void writerFile(String writepath,String textHtml) throws java.io.IOException {
//        File file = new File(writepath);
//        FileOutputStream file1 = new FileOutputStream(file);
//        BufferedWriter output = null;
//        try {
//            //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));
//            output = new BufferedWriter(new OutputStreamWriter(file1,"GBK"));//new FileWriter(file)
//            output.write(textHtml);
//        } finally {
//            output.close();
//        }
//    }
}
