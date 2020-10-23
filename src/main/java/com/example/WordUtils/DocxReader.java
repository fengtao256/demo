//package com.example.WordUtils;
//
//import com.mysql.jdbc.StringUtils;
//import org.docx4j.TraversalUtil;
//import org.docx4j.XmlUtils;
//import org.docx4j.openpackaging.exceptions.Docx4JException;
//import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
//import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
//import org.docx4j.wml.*;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBElement;
//import java.io.File;
//import java.util.*;
//
//
//public class DocxReader {
//	private static int count = 0 ;
//	private static int countDoc = 0 ;
//	private static int countPatient = 0 ;
//	/**
//	 * 文件递归
//	 * @param filePath
//	 */
//	public static void getFiles(String filePath){
//		File[] firstLevelPaths = (new File(filePath)).listFiles();
//		List<Object> dataList = new ArrayList<Object>() ;
//		for (File file: firstLevelPaths) {
//			//是文件，解析入库
//			if(file.isFile()){
//				System.out.println("第一层下的文件，没解析"+file.getAbsolutePath());
//			}else if(file.isDirectory()){ //文件夹，则递归调用
//				dataList.clear() ;
//				for(File file2: file.listFiles()) {
//					//是文件，解析入库
//					if(file2.isFile()){
//						//文件自定义判断
//						if(file2.getName().startsWith("~$") || file2.getName().endsWith("证明.docx") || file2.getName().endsWith("证.docx") || file2.getName().endsWith("doc") ){
//							//System.out.println("不解析该文件 ---> "+file.getName());
//						}else if(file2.getName().endsWith("docx")){
//							readWord(dataList,file2);
//						}
//					}else if(file2.isDirectory()){ //文件夹，则递归调用
//						dataList.clear() ;
//						for(File file3: file2.listFiles()) {
//							if(file3.isFile()){
//								//文件自定义判断
//								if(file3.getName().startsWith("~$") || file3.getName().endsWith("证明.docx") || file3.getName().endsWith("证.docx") || file3.getName().endsWith("doc") ){
//
//								}else if(file2.getName().endsWith("docx")){
//									readWord(dataList,file3);
//								}
//							}else{
//								dataList.clear() ;
//							}
//						}
//						//TODO 存储
//					}
//
//				}
//				listToMap(dataList) ;
//				//TODO 存储
//
//			}
//
//
//		}
//	}
//
//	/**
//	 * word阅读
//	 * @param
//	 */
//	public static void readWord(List<Object> dataList,File file ) {
//
//		WordprocessingMLPackage wordMLPackage = null;
//		try {
//			wordMLPackage = WordprocessingMLPackage.load(file);
//		} catch (Docx4JException e) {
//			e.printStackTrace();
//		}
//		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
//
//
//		Document wmlDocumentEl =  documentPart
//				.getJaxbElement();
//		Body body = wmlDocumentEl.getBody();
//		new TraversalUtil(body,
//
//				new TraversalUtil.Callback() {
//
//					String indent = "";
//					Integer count = 1 ;
//					@Override
//					public List<Object> apply(Object o) {
//
//						String wrapped = "";
//						if (o instanceof JAXBElement) wrapped =  " (wrapped in JAXBElement)";
//
//						o = XmlUtils.unwrap(o);
//
//						String text = "";
//						if (o instanceof org.docx4j.wml.Text)
//							text = ((org.docx4j.wml.Text) o).getValue();
//
////						System.out.println(indent + o.getClass().getName() + wrapped + text+ o.toString());
//						return null;
//					}
//
//					@Override
//					public boolean shouldTraverse(Object o) {
//						return true;
//					}
//
//					// Depth first
//					@Override
//					public void walkJAXBElements(Object parent) {
//
//						indent += "    ";
//
//						List children = getChildren(parent);
//						if (children != null) {
//							for (Object o : children) {
//								if(o instanceof org.docx4j.wml.P){
////									System.out.println(" ob -> {" + o.toString() + "}");
//									//TODO 数据拼装
//									if(o!=null && !StringUtils.isEmptyOrWhitespaceOnly(o.toString())){
//										dataList.add(o) ;
//									}
//
//								}
//								//不是p标签的时候，递归获取
//								o = XmlUtils.unwrap(o);
//								if (this.shouldTraverse(o)) {
//									walkJAXBElements(o);
//								}
//							}
//
//						}
//
//						indent = indent.substring(0, indent.length() - 4);
//					}
//
//					@Override
//					public List<Object> getChildren(Object o) {
//						return TraversalUtil.getChildrenImpl(o);
//					}
//
//				}
//
//		);
//
//	}
//
//	public static void listToMap(List<Object> dataList) {
//
//		Map<String,Object> dataMap = new HashMap<String , Object>() ;
//		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>() ;
//		dataMap.put("patient_id",UUID.randomUUID().toString().replace("-","")) ;
//		if(dataList!=null && dataList.size()>0){
//			for(int i = 0 ; i < dataList.size() ; i ++){
//				//System.out.println(dataList.get(i));
//				if(dataList.get(i)!=null && dataList.get(i).toString().startsWith("姓名：")){
//					dataMap.put("patient_name",""+dataList.get(i+1).toString().replace(" ","")) ;
//				}
//				if(dataList.get(i)!=null && dataList.get(i).toString().startsWith("年龄：")){
//					dataMap.put("patient_age",""+dataList.get(i+1)) ;
//				}
//				if(dataList.get(i)!=null && dataList.get(i).toString().startsWith("入院时间：")){
//					dataMap.put("patient_in_time",""+dataList.get(i+1)) ;
//				}
//				if(dataList.get(i)!=null && dataList.get(i).toString().startsWith("出院日期")){
//					dataMap.put("patient_out_time",""+dataList.get(i+1)) ;
//				}
//				if(dataList.get(i)!=null && dataList.get(i).toString().startsWith("主诉：")){
//					dataMap.put("ZS",dataList.get(i).toString().split("主诉：")[1]) ;
//				}
//				if(dataList.get(i)!=null && dataList.get(i).toString().startsWith("现病史：")){
//					dataMap.put("XBS",dataList.get(i).toString().split("现病史：")[1]) ;
//				}
//				if(dataList.get(i)!=null && dataList.get(i).toString().startsWith("既往史：")){
//					dataMap.put("JWS",dataList.get(i).toString().split("既往史：")[1]) ;
//				}
//				if(dataList.get(i)!=null && dataList.get(i).toString().startsWith("个人史：")){
//					dataMap.put("GRS",dataList.get(i).toString().split("个人史：")[1]) ;
//				}
//				if(dataList.get(i)!=null && dataList.get(i).toString().startsWith("月经史：")){
//					dataMap.put("YJS",dataList.get(i).toString().split("月经史：")[1]) ;
//				}
//				if(dataList.get(i)!=null && dataList.get(i).toString().startsWith("婚育史：")){
//					dataMap.put("HYS",dataList.get(i).toString().split("婚育史：")[1]) ;
//				}
//				if(dataList.get(i)!=null && dataList.get(i).toString().startsWith("家族史：")){
//					dataMap.put("JZS",dataList.get(i).toString().split("家族史：")[1]) ;
//				}
//				if(dataList.get(i)!=null && dataList.get(i).toString().startsWith("中医四诊情况")){
//					dataMap.put("ZYSZQK",dataList.get(i+1)+"\n"+dataList.get(i+2)+"\n"+dataList.get(i+3)+"\n"+dataList.get(i+4)) ;
//				}
//				if(dataList.get(i)!=null && dataList.get(i).toString().startsWith("体  格  检  查")){
//					dataMap.put("TGJC",dataList.get(i+1)+"\n"+dataList.get(i+2)) ;
//				}
//				if(dataList.get(i)!=null && (dataList.get(i).toString().startsWith("专 科 查 体") || dataList.get(i).toString().startsWith("专  科  查  体"))){
//					dataMap.put("ZKJC",""+dataList.get(i+1)) ;
//				}
//				if(dataList.get(i)!=null && dataList.get(i).toString().startsWith("入院诊断")){
//					dataMap.put("RYZD",dataList.get(i+1)+"\n"+dataList.get(i+1)) ;
//				}
//				if(dataList.get(i)!=null && (dataList.get(i).toString().startsWith("辅 助 检 查") || dataList.get(i).toString().startsWith("辅  助  检  查"))){
//					dataMap.put("FZJC",""+dataList.get(i+1)) ;
//				}
//				//出院信息解析
//				if(dataList.get(i)!=null && dataList.get(i).toString().startsWith("入院时情况")){
//					dataMap.put("RYSQK",""+dataList.get(i+1)) ;
//				}
//				if(dataList.get(i)!=null && dataList.get(i).toString().startsWith("入院后诊疗经过")){
//					dataMap.put("RYHZLJG",""+dataList.get(i+1)) ;
//				}
//				if(dataList.get(i)!=null && dataList.get(i).toString().startsWith("出院诊断")){
//					dataMap.put("CYZD",dataList.get(i+1)+"\n"+dataList.get(i+2)) ;
//				}
//				if(dataList.get(i)!=null && dataList.get(i).toString().startsWith("出院情况")){
//					dataMap.put("CYQK",""+dataList.get(i+1)) ;
//				}
//				if(dataList.get(i)!=null && dataList.get(i).toString().startsWith("中医调护：")){
//					dataMap.put("ZYTH",dataList.get(i).toString().split("中医调护：")[1]) ;
//				}
//				if(dataList.get(i)!=null && dataList.get(i).toString().startsWith("出院带药：")){
//					dataMap.put("CYDY",""+dataList.get(i+1)) ;
//				}
//				if(dataList.get(i)!=null && dataList.get(i).toString().startsWith("出院医嘱：")){
//					int count = 1 ;
//					String temp = "" ;
//					while(dataList.size() >(i+count) && dataList.get(i+count) != null && !(dataList.get(i+count).toString().startsWith("中医调护")||
//													dataList.get(i+count).toString().startsWith("出院带药"))){
//						temp += dataList.get(i+count) ;
//						count ++ ;
//					}
//					dataMap.put("CYYZ",temp) ;
//				}
//				//TODO 病程解析
//				//日期匹配
//				if(dataList.get(i)!=null && dataList.get(i).toString().length() > 8 &&dataList.get(i).toString().substring(4,5).equals("-")){
//					Map<String,Object> tempMap = new HashMap<String , Object>() ;
//					int count = 1 ;
//					String day = dataList.get(i).toString().split(" ")[0] ;
//					tempMap.put("day",day) ;
//					String hour = "" ;
//					for(String hourStr : dataList.get(i).toString().split(" ")){
//						if(!StringUtils.isEmptyOrWhitespaceOnly(hourStr) && hourStr.length()>3 && (hourStr.substring(2,3).equals(":")||hourStr.substring(1,2).equals(":"))){
//							hour = hourStr ;
//
//						}
//					}
//					if(StringUtils.isEmptyOrWhitespaceOnly(hour)){
//						hour = "10:01" ;
//					}
//					tempMap.put("hour",hour) ;
//					String str = "" ;
//					while(dataList.size() >(i+count) && dataList.get(i+count) != null &&
//							!(dataList.get(i+count).toString().replace(" ","").startsWith("医师签名")||
//							dataList.get(i+count).toString().replace(" ","").startsWith("医师签字")||
//							dataList.get(i+count).toString().replace(" ","").startsWith("医生签名")||
//									dataList.get(i+count).toString().replace(" ","").startsWith("出院记录")||
//									dataList.get(i+count).toString().replace(" ","").startsWith("签名"))){
//						str += dataList.get(i+count)+"\n" ;
//						count ++ ;
//					}
//					tempMap.put("content",str);
//					mapList.add(tempMap) ;
//				}
//
//			}
//		}
//		//System.out.println(" 病人："+ dataMap.get("patient_name"));
////		if(dataMap.get("patient_name")!=null && dataMap.get("patient_name").toString().startsWith("2011-7-6                       出院记录"))
////		for (Map.Entry str: dataMap.entrySet()) {
////			System.out.println(str.getKey()+"  >  "+str.getValue());
////		}
//
//		//解析完毕，入库
//		if(dataMap.get("patient_name") == null || dataMap.get("patient_age") == null ||dataMap.get("patient_name") == "" || dataMap.get("patient_age") == "" ){
//			System.out.println("信息不全");
//		}else{
//			StationService stationService = new StationService() ;
//			stationService.insertStationsItem(dataMap,mapList,countPatient) ;
//			countPatient++ ;
//		}
//		System.out.println("添加完毕"+countPatient + "个病人");
//	}
//
//
//	public static void main(String[] args) {
//
//		Map<String,Object> dataMap = new HashMap<String ,Object>() ;
//		String filePath = "D:\\complay-file\\公司项目\\医疗大数据\\数据\\数据\\病历" ;
//		getFiles(filePath);
////		String str = "5215-21054" ;
////		System.out.println(str.substring(4,5));
////		readWord(dataMap,new File("D:\\complay-file\\公司项目\\医疗大数据\\数据\\数据\\+5 秦有权.docx"));
//
//	}
//
//}