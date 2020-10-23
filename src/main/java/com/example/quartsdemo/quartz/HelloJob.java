package com.example.quartsdemo.quartz;



import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        Date date = new Date() ;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.err.println("当前时间："+sdf.format(date));
        JobKey key = jobExecutionContext.getJobDetail().getKey() ;
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap() ;
        System.err.println("Information of "+key+"\n  myFirstName:"+dataMap.getString("myFirstName")+
                           "\n  mylASTName:"+dataMap.getString("myLastName"));

    }

    public static void main(String[] args) {
        String test= "CreateExcel.java [in com.sunsheen.cm.common.excel.util [in resources/CM/Common/src [in " +
                "MDRSC]]]\n" +
                "  package com.sunsheen.cm.common.excel.util\n" +
                "  import org.apache.poi.ss.util.CellRangeAddress\n" +
                "  import org.apache.poi.ss.util.CellRangeAddressList\n" +
                "  import org.apache.poi.ss.util.RegionUtil\n" +
                "  import org.apache.poi.xssf.usermodel.*\n" +
                "  import java.util.List\n" +
                "  import java.util.concurrent.atomic.AtomicInteger\n" +
                "  class CreateExcel\n" +
                "    static XSSFWorkbook buildExcelObject(ExcelBuildEntity, String, String)\n" +
                "    static XSSFWorkbook buildExcelObject(ExcelBuildEntity, List<List<String>>, String, String)\n" +
                "    static void createExcel(XSSFWorkbook, ExcelBuildEntity, String, String)\n" +
                "    static void createExcel(XSSFWorkbook, ExcelBuildEntity, List<List<String>>, String, String)\n" +
                "    static void createExcelCell(XSSFWorkbook, XSSFSheet, ExcelBuildEntity, AtomicInteger, " +
                "AtomicInteger, AtomicInteger, AtomicInteger, AtomicInteger)\n" +
                "    static void setMergedRegion(XSSFWorkbook, XSSFSheet, CellRangeAddress)\n" +
                "    static void setPullDownList(XSSFSheet, String[], int, int, int, int)\n" +
                "    static void setSizeColumn(XSSFSheet, AtomicInteger)\n" +
                "    static AtomicInteger getDeep(ExcelBuildEntity, AtomicInteger, AtomicInteger)测试数据信息]-AA----AA--哈哈哈--AA--" ;
        String[] colDatas = test.split("--AA--") ;
        int count  = 0 ;
        for (String colData : colDatas){
            count++ ;
            System.out.println("这是列数据【"+count+"】"+ colData);

        }
    }
}
