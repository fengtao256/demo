package com.example.Jousp;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class JoupDemo {

//    public static void main(String[] args) throws IOException {
//        Document doc = Jsoup.connect("http://edu.sunplusedu.com/").get();
////        Elements newsHeadlines = doc.getElementsByClass("tempWrap");
////        Element newsHeadline = newsHeadlines.get(2);
//        Elements ssss= doc.getElementsByAttribute("alt") ;
//        int num = 0 ;
//        for (Element element:ssss) {
//            ssss.get(num);
//            num ++ ;
//            System.out.print(ssss.get(num).attr("alt")+"、");
//        }
//
//    }
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.parse(new File("C:\\Users\\Administrator\\Desktop\\PublishOnly.html"), "UTF-8", "");
        //Jsoup.parse("");
//        Elements newsHeadlines = doc.getElementsByClass("tempWrap");
//        Element newsHeadline = newsHeadlines.get(2);
        Element element1= doc.getElementById("ext-gen181");
        Elements elements = element1.select("tr");
        //System.out.println(elements.get(0));
        int num = 0 ;
        for (Element element:elements) {
            Elements element3 = element.getElementsByClass("x-grid3-cell-inner x-grid3-col-1");
            Elements element4= element.getElementsByClass("x-grid3-cell-inner x-grid3-col-2");
            Elements element5 = element.getElementsByClass("x-grid3-cell-inner x-grid3-col-3");
//            System.out.println(element3.get(0).select("span").text());
//            System.out.println(element4.get(0).select("span").text());
            System.out.println(element5.get(0).select("span").text());
        }

}
}
