//package com.example.unzip;
//
//import java.io.File;
//import java.util.List;
//
//import org.docx4j.openpackaging.exceptions.Docx4JException;
//import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
//import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
//
//public class DocxReader {
//	public static void main(String[] args) {
//		File[] firstLevelPaths = (new File("D:\\complay-file\\公司项目\\医疗大数据\\数据\\数据")).listFiles();
//
//		int count = 0;
//
//		for (File secondLevelPath : firstLevelPaths) {
//			System.out.println(secondLevelPath.getName());
//
//			File[] secondLevelPaths = secondLevelPath.listFiles();
//
//			if (secondLevelPaths == null) {
//				continue;
//			}
//
//			for (File thirdLevelPath : secondLevelPaths) {
//				System.out.println(thirdLevelPath.getName());
//
//				File[] files = thirdLevelPath.listFiles();
//
//				if (files == null) {
//					continue;
//				}
//
//				for (File file : files) {
//					String fileName = file.getName();
//
//					if (fileName.startsWith("~$") || fileName.contains("病程") || !fileName.endsWith(".docx")) {
//						continue;
//					}
//
//					System.out.println(fileName);
//
//					try {
//						WordprocessingMLPackage wordprocessingMLPackage = WordprocessingMLPackage.load(file);
//						String contentType = wordprocessingMLPackage.getContentType();
//			            System.out.println("contentType -> {" + contentType + "}");
//
//			            MainDocumentPart mainDocumentPart = wordprocessingMLPackage.getMainDocumentPart();
//			            List<Object> content = mainDocumentPart.getContent();
//
//			            System.out.println("---------------------------------------------------------------------");
//
//			            for (Object ob : content) {
//			                System.out.println("ob -> {" + ob + "}");
//			            }
//
//			            System.out.println("******************************************************************************************************************************************");
//
//			            count++;
//					} catch (Docx4JException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//
//			System.out.println("Number of files = " + count);
//		}
//	}
//}