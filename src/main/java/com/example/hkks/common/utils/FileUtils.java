package com.example.hkks.common.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.mysql.jdbc.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class FileUtils {
	
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");
	/**
	 * @author zhangyunchao
	 * @Description: 文件大小计算
	 * @param fileLength
	 *            文件长度
	 * @return String
	 */
	public static String FormetFileSize(Long fileLength) {
        String fileSizeString = "";
        DecimalFormat df = new DecimalFormat("#.00");
        if (fileLength != null) {
            if (fileLength < 1024) {
                fileSizeString = df.format((double) fileLength) + "B";
            }
            else if (fileLength < 1048576) {
                fileSizeString = df.format((double) fileLength / 1024) + "KB";
            }
            else if (fileLength < 1073741824) {
                fileSizeString = df.format((double) fileLength / 1048576) + "MB";
            }
            else {
                fileSizeString = df.format((double) fileLength / 1073741824) + "GB";
            }
        }
        return fileSizeString;
    }

	/**
	 * @author zhangyunchao
	 * @Description: 文件路径处理
	 * @param path
	 *            文件路径
	 * @return String
	 */ 
	public static String getRealFilePath(String path) {
		return path.replace("/", FILE_SEPARATOR).replace("\\", FILE_SEPARATOR);
	}
 
	/**
	 * @author zhangyunchao
	 * @Description: url路径处理
	 * @param path
	 *            url路径
	 * @return String
	 */ 
	public static String getHttpURLPath(String path) {
		return path.replace("\\", "/");
	}
	
	/**
	 * 
	* @Title: genDirTree
	* @Description: 获取目录下的节点信息
	* @author: FengTao
	* @date 2020年9月9日 下午12:40:55
	* @param path
	* @param level
	* @param dir
	* @param tree void
	* @version
	 */
    public static void genDirTree(String path, Integer level , String dir, FileNode tree) {
        level++ ;
        File file = new File(path);
        File[] files = file.listFiles();
        if (!file.exists()) {
            System.out.println("文件不存在");
            return;
        }
        if (files.length != 0) {
            int i = 0 ; //同级目录下第几个节点信息
            List<FileNode> fileNodes = new ArrayList<FileNode>() ;
            for (File f : files) {
                //当前节点信息
                FileNode fileNode = new FileNode() ;
                fileNode.setTitle(f.getName());
                fileNode.setKey(UUID.randomUUID().toString());
                fileNode.setChildren(new ArrayList<FileNode>() );
                //添加同级节点信息
                fileNodes.add(fileNode) ;
                tree.setChildren(fileNodes);
                if (f.isDirectory()) { //是文件，则继续往下递归
                    dir = f.getName();
                    genDirTree(f.getAbsolutePath(), level, dir ,tree.getChildren().get(i));
                }
                i ++ ;
            }
        }
    }
    
    public static List<String> readFileByLine(String Path){
        BufferedReader reader = null;
        List<String> lines = new ArrayList<String>();
        try{
            FileInputStream fileInputStream = new FileInputStream(Path);
            String fileCode = com.sunsheen.hkks.common.util.EncodingDetect.getJavaEncode(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, fileCode);
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine()) != null){
            	lines.add(tempString) ;
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
        return lines;
    }
    
	/**
	 * 
	* @Title: ReadFile
	* @Description: 按行读取文件
	* @author: FengTao
	* @date 2020年9月8日 上午11:29:22
	* @param Path
	* @return String
	* @version
	 */
	public static String ReadFile(String Path){
        BufferedReader reader = null;
        String laststr = "";
        try{
            FileInputStream fileInputStream = new FileInputStream(Path);
            String fileCode = com.sunsheen.hkks.common.util.EncodingDetect.getJavaEncode(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, fileCode);
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine()) != null){
                laststr += tempString;
            }
            reader.close();
        }catch(IOException e){
            //e.printStackTrace();
        	System.out.print("找不到文件"+Path);
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
	
	/**
	 * 判断指定的文件删除是否成功
	 * @param FileName 文件路径
	 * @return true or false 成功返回true，失败返回false
	 */
	@SuppressWarnings("resource")
	public static String readWord(String Path){
        String content = "";
        InputStream is = null;
        try {
        	is = new FileInputStream(new File(Path));
            if (Path.endsWith(".doc")) {
            	HWPFDocument doc = new HWPFDocument(is);   
            	content = doc.getDocumentText(); 
//            	doc.close();
            } else if (Path.endsWith(".docx")) {
            	XWPFDocument  docx = new XWPFDocument(is);  
//            	XWPFWordExtractor extractor = new XWPFWordExtractor(docx);           	   
//            	content = extractor.getText();  
//            	extractor.close();
//            	docx.close();
            } else {
                System.out.println("此文件不是word文件！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
            	try {
            		is.close();
            	} catch (IOException e) {
	                e.printStackTrace();
	                System.out.println("读取word文件失败！");
            	}
            }
        }
        return content;
	}

	/**
     * 将JSON数据格式化并保存到文件中
     * @param jsonData 需要输出的json
     * @param filePath 输出的文件地址
     * @return
     */
    public static boolean createJsonFile(Object jsonData, String filePath) {
        String content = JSON.toJSONString(jsonData);
        content = prettyJson(content) ;
        // 标记文件生成是否成功
        boolean flag = true;
        // 生成json格式文件
        try{
        	File file = new File(filePath);
            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                file.getParentFile().mkdirs();
            }
            if (file.exists()) { // 如果已存在,删除旧文件
                file.delete();
            }
            file.createNewFile();
            FileWriter fw = new FileWriter(file) ;
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            fw.flush();
            bw.flush();
            fw.close();
            bw.close();
        }catch(IOException e){
        	flag = false;
            System.out.println("JsonFile write error \n "+ e.getMessage());
        }
        
        return flag;
    }
    
    /**
     * 
    * @Title: createFileFromBytes
    * @Description: 从字节流创建文件信息
    * @author: FengTao
    * @date 2020年9月8日 下午3:57:29
    * @param bytes
    * @param descDir
    * @return boolean
    * @version
     */
    public static boolean createFileFromBytes(byte[] bytes, String descDir) {
    	// 标记文件生成是否成功
        boolean flag = true;
        try{
        	File src = new File( descDir );
        	if(!src.exists()){
        		src.getParentFile().mkdirs() ;
        	}
        	OutputStream os = new FileOutputStream(src,true); 
        	os.write(bytes);
            os.flush();
            os.close();
        }catch(IOException e){
        	flag = false;
            System.out.println("JsonFile write error \n "+ e.getMessage());
        }
        return flag;
    }
	
    /**
     * json 美化
     * @param json
     * @return
     */
    private static String prettyJson(String json){
        if(StringUtils.isEmptyOrWhitespaceOnly(json)){
            return json;
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.parseObject(json);
        }catch (Exception e){
            return json;
        }
        return JSONObject.toJSONString(jsonObject,true);
    }
    
    /**
     * 获取路径下的所有文件/文件夹
     * @param directoryPath 需要遍历的文件夹路径
     * @param isAddDirectory 是否将子文件夹的路径也添加到list集合中
     * @return
     */
    public static List<String> getAllFile(String directoryPath,boolean isAddDirectory) {
        List<String> list = new ArrayList<String>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if(isAddDirectory){
                    //list.add(file.getAbsolutePath());
                }
                list.addAll(getAllFile(file.getAbsolutePath(),isAddDirectory));
            } else {
                list.add(file.getAbsolutePath());
            }
        }
        return list;
    }
    /**
     * 
    * @Title: distinct
    * @Description:
    * @author: FengTao
    * @date 2020年8月28日 下午5:35:24 void
    * @version
     */
    public static void removeHasFileName(List<String> localFiles , List<String> markFiles){
    	Iterator<String> iterator = markFiles.iterator();
		while( iterator.hasNext() ){
			String fileName = iterator.next() ;
			for( String str : localFiles ){
				if(str.endsWith(fileName)){ //有这个文件
					iterator.remove();
				}
			}
		}
    }
    
	/**
	 * 判断指定的文件或文件夹删除是否成功
	 * @param FileName 文件或文件夹的路径
	 * @return true or false 成功返回true，失败返回false
	 */
	public static boolean deleteAnyone(String FileName){
        File file = new File(FileName);//根据指定的文件名创建File对象
		if ( !file.exists() ){  //要删除的文件不存在
			System.out.println("文件"+FileName+"不存在，删除失败！" );
			return false;
		}else{ //要删除的文件存在
			if ( file.isFile() ){ //如果目标文件是文件
				return deleteFile(FileName);
			}else{  //如果目标文件是目录
				return deleteDir(FileName);
			}
		}
	}

	/**
	 * 判断指定的文件删除是否成功
	 * @param FileName 文件路径
	 * @return true or false 成功返回true，失败返回false
	 */
	public static boolean deleteFile(String fileName){
		File file = new File(fileName);//根据指定的文件名创建File对象
		if (  file.exists() && file.isFile() ){ //要删除的文件存在且是文件
				if ( file.delete()){
					//System.out.println("文件"+fileName+"删除成功！");
					return true;
				}else{
					System.out.println("文件"+fileName+"删除失败！");
					return false;
				}
		}else{	
			    System.out.println("文件"+fileName+"不存在，删除失败！" );
				return false;
			}		
	}

	/**
	 * 删除指定的目录以及目录下的所有子文件
	 * @param dirName is 目录路径
	 * @return true or false 成功返回true，失败返回false
	 */
	public static boolean deleteDir(String dirName){
		
		if ( dirName.endsWith(File.separator) )//dirName不以分隔符结尾则自动添加分隔符 
			dirName = dirName + File.separator;	
		File file = new File(dirName);//根据指定的文件名创建File对象	
		if ( !file.exists() || ( !file.isDirectory() ) ){ //目录不存在或者
//			System.out.println("目录删除失败"+dirName+"目录不存在！" );
			return false;
		}	
		File[] fileArrays = file.listFiles();//列出源文件下所有文件，包括子目录
		for ( int i = 0 ; i < fileArrays.length ; i++ ){//将源文件下的所有文件逐个删除
			deleteAnyone(fileArrays[i].getAbsolutePath());
		}
		if ( file.delete() ) {//删除当前目录
		}
//			System.out.println("目录"+dirName+"删除成功！" );
		return true;	
	}
	
}
