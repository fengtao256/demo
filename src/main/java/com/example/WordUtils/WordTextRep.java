package com.example.WordUtils;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//全文替换word指定的内容
public class WordTextRep {
    public static void main(String[] args) {
        //加载Word文档
        Document document = new Document("D:/complay-file/公司项目/医疗大数据/5床真追.docx");

        //使用新文本替换文档中的指定文本
        document.replace("金雷", "XX", false, true);
        //保存文档
        try{
            document.saveToFile("D:/complay-file/公司项目/医疗大数据/XXX.docx", FileFormat.Docx_2013);
            System.out.println("修改成功！");
        }catch(Exception e){

            System.out.println(e.getMessage() );
        }

//        String regEx=",[\\u4e00-\\u9fa5]+,\\\\d";
//        Pattern p = Pattern.compile(regEx);
//        Matcher m = p.matcher("四川省成都市温江区");
//        while(m.find()){
//            System.out.println(m.group());
//        }

    }
}
