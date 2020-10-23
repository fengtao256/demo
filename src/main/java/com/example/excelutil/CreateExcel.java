package com.example.excelutil;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 创建excel From ListMap
 */
public class CreateExcel {


  /**
   *
   * @Title: buildExcelObject
   * @Description: 创建Excel模板
   * @param @param excelBuildEntity
   * @param @param workbookName
   * @param @param sheetName
   * @param @return 参数
   * @return XSSFWorkbook 返回类型
   * @throws
   * @author: FengTao
   * @date 2020年10月22日
   */
  public static XSSFWorkbook buildExcelObject(ExcelBuildEntity excelBuildEntity ,String workbookName , String sheetName){
    //创建一个Excel对象
    XSSFWorkbook workbook = new XSSFWorkbook() ;
    createExcel(workbook , excelBuildEntity , workbookName , sheetName) ;
    return  workbook ;
  }

  /**
   *
   * @Title: buildExcelObject
   * @Description: 创建带有数据的Excel对象
   * @param @param excelBuildEntity
   * @param @param workbookName
   * @param @param sheetName
   * @param @return 参数
   * @return XSSFWorkbook 返回类型
   * @throws
   * @author: FengTao
   * @date 2020年10月22日
   */
  public static XSSFWorkbook buildExcelObject(ExcelBuildEntity excelBuildEntity , List<List<String>> buildDataList ,String workbookName , String sheetName){
    //创建一个Excel对象
    XSSFWorkbook workbook = new XSSFWorkbook() ;
    createExcel(workbook , excelBuildEntity , buildDataList , workbookName , sheetName) ;
    return  workbook ;
  }

  /**
   *
   * @Title: createExcel
   * @Description: 创建不带有数据的Excel对象
   * @param @param workbook
   * @param @param excelBuildEntity
   * @param @param buildDataList
   * @param @param workbookName
   * @param @param sheetName 参数
   * @return void 返回类型
   * @throws
   * @author: FengTao
   * @date 2020年10月22日
   */
  private static void createExcel(XSSFWorkbook workbook , ExcelBuildEntity excelBuildEntity , String workbookName , String sheetName){
    //如果未传参数，则自动初始化表格名称
    if(workbookName==null || workbookName == ""){ workbookName = System.currentTimeMillis()+".xlsx" ; }
    if(sheetName==null || sheetName == ""){ sheetName = "sheet" ; }
    XSSFSheet sheet = workbook.createSheet( sheetName ) ;
    AtomicInteger headDeep = getDeep( excelBuildEntity , new AtomicInteger(0) , new AtomicInteger(1)) ;
    //根据树的深度初始化行信息
    for(int i =0;i<headDeep.intValue()+2;i++){
      sheet.createRow((short)i);
    }
    //创建表格表头信息
    XSSFRow row = sheet.getRow(0);
    XSSFCell xCell = row.createCell(0) ;
    xCell.setCellValue(workbookName);
    xCell.setCellStyle( ExcelCellStyleUtils.buildHeadCellStyle(workbook) );
    // 数据第一行为表格名称，从第二行开始创建
    AtomicInteger startRow = new AtomicInteger(1) ;
    // 第一列为提示列,默认从第二列开始创建
    AtomicInteger startCell = new AtomicInteger(1) ;
    // 最大列宽
    AtomicInteger maxWidth = new AtomicInteger(1) ;
    //往后合并的格子数量大小
    AtomicInteger mergedLength = new AtomicInteger(0) ;
    // 创建表格动态表头和列信息
    createExcelCell( workbook ,sheet , excelBuildEntity , startRow , startCell , maxWidth , mergedLength, headDeep);
    // 合并单元格，参数依次为起始行，结束行，起始列，结束列 （索引0开始）
    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, maxWidth.get()-1));
    //创建填表字段
    XSSFRow row1 = sheet.getRow(1);
    XSSFCell xCell1 = row1.createCell(0) ;
    xCell1.setCellValue("填表字段");
    xCell1.setCellStyle( ExcelCellStyleUtils.buildDefaultCellStyle(workbook) );
    sheet.addMergedRegion(new CellRangeAddress(1, headDeep.intValue(), 0, 0));
    //创建填表说明
    XSSFRow row2 = sheet.getRow(headDeep.intValue()+1);
    XSSFCell xCell2 = row2.createCell(0) ;
    xCell2.setCellValue("填表说明");
    xCell2.setCellStyle( ExcelCellStyleUtils.buildDefaultCellStyle(workbook) );
    // 设置数据起始符和数据列大小
    XSSFRow row3 = sheet.createRow(sheet.getLastRowNum()+1);
    for(int i =0 ; i < maxWidth.intValue() ; i ++ ){
      XSSFCell xCelli = row3.createCell(i) ;
      xCelli.setCellStyle( ExcelCellStyleUtils.buildDefaultCellStyle(workbook) );
    }
    XSSFCell xCell3 = row3.getCell(1) ;
    xCell3.setCellValue("********");
    XSSFCell xCell4 = row3.getCell(2) ;
    xCell4.setCellValue(maxWidth.intValue()-1);
    // 设置自动设置列宽
    //设置表头区域信息
    // 1、设置背景
    //内容区域设置不锁定
    for(int i = headDeep.get()+3  ; i < 500  ; i ++){
      XSSFRow contentRow = sheet.createRow(i) ;
      for(int j = 0  ; j < maxWidth.get() ; j ++){
        XSSFCell contentCell = contentRow.createCell(j) ;
        contentCell.setCellStyle(ExcelCellStyleUtils.buildContentCellStyle(workbook));
      }
    }

    for(int i = 0 ; i < maxWidth.intValue()+1 ; i++){
      sheet.autoSizeColumn((short)i);
    }
    setSizeColumn(sheet,maxWidth) ;
    sheet.protectSheet("100200");
    sheet.enableLocking();
  }


  /**
   *
   * @Title: createExcel
   * @Description: 创建带有数据的Excel对象
   * @param @param workbook
   * @param @param excelBuildEntity
   * @param @param buildDataList
   * @param @param workbookName
   * @param @param sheetName 参数
   * @return void 返回类型
   * @throws
   * @author: FengTao
   * @date 2020年10月22日
   */
  private static void createExcel(XSSFWorkbook workbook , ExcelBuildEntity excelBuildEntity , List<List<String>> buildDataList , String workbookName , String sheetName){
    //如果未传参数，则自动初始化表格名称
    if(workbookName==null || workbookName == ""){ workbookName = System.currentTimeMillis()+".xlsx" ; }
    if(sheetName==null || sheetName == ""){ sheetName = "sheet" ; }
    XSSFSheet sheet = workbook.createSheet( sheetName ) ;
    AtomicInteger headDeep = getDeep( excelBuildEntity , new AtomicInteger(0) , new AtomicInteger(1)) ;
    //根据树的深度初始化行信息
    for(int i =0;i<headDeep.intValue()+2;i++){
      sheet.createRow((short)i);
    }
    //创建表格表头信息
    XSSFRow row = sheet.getRow(0);
    XSSFCell xCell = row.createCell(0) ;
    xCell.setCellValue(workbookName);
    xCell.setCellStyle( ExcelCellStyleUtils.buildHeadCellStyle(workbook) );
    // 数据第一行为表格名称，从第二行开始创建
    AtomicInteger startRow = new AtomicInteger(1) ;
    // 第一列为提示列,默认从第二列开始创建
    AtomicInteger startCell = new AtomicInteger(1) ;
    // 最大列宽
    AtomicInteger maxWidth = new AtomicInteger(1) ;
    //往后合并的格子数量大小
    AtomicInteger mergedLength = new AtomicInteger(0) ;
    // 创建表格动态表头和列信息
    createExcelCell( workbook ,sheet , excelBuildEntity , startRow , startCell , maxWidth , mergedLength, headDeep);
    // 合并单元格，参数依次为起始行，结束行，起始列，结束列 （索引0开始）
    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, maxWidth.get()-1));
    //创建填表字段
    XSSFRow row1 = sheet.getRow(1);
    XSSFCell xCell1 = row1.createCell(0) ;
    xCell1.setCellValue("填表字段");
    xCell1.setCellStyle( ExcelCellStyleUtils.buildDefaultCellStyle(workbook) );
    sheet.addMergedRegion(new CellRangeAddress(1, headDeep.intValue(), 0, 0));
    //创建填表说明
    XSSFRow row2 = sheet.getRow(headDeep.intValue()+1);
    XSSFCell xCell2 = row2.createCell(0) ;
    xCell2.setCellValue("填表说明");
    xCell2.setCellStyle( ExcelCellStyleUtils.buildDefaultCellStyle(workbook) );
    // 设置数据起始符和数据列大小
    XSSFRow row3 = sheet.createRow(sheet.getLastRowNum()+1);
    for(int i =0 ; i < maxWidth.intValue() ; i ++ ){
      XSSFCell xCelli = row3.createCell(i) ;
      xCelli.setCellStyle( ExcelCellStyleUtils.buildDefaultCellStyle(workbook) );
    }
    XSSFCell xCell3 = row3.getCell(1) ;
    xCell3.setCellValue("********");
    XSSFCell xCell4 = row3.getCell(2) ;
    xCell4.setCellValue(maxWidth.intValue()-1);
    // 设置自动设置列宽
    //设置表头区域信息
    // 1、设置背景
    //填充内容信息
    //buildDataList
    for(int i = headDeep.get()+3  ; i < buildDataList.size()+headDeep.get()+3  ; i ++){
      XSSFRow contentRow = sheet.createRow(i) ;
      for(int j = 1  ; j < buildDataList.get(i-3-headDeep.get()).size()+1 ; j ++){
        XSSFCell contentCell = contentRow.createCell(j) ;
        contentCell.setCellValue(((Object)buildDataList.get(i-3-headDeep.get()).get(j-1))+""); //设置内容信息
        contentCell.setCellStyle(ExcelCellStyleUtils.buildContentCellStyle(workbook));

      }
    }
    for(int i = 0 ; i < maxWidth.intValue()+1 ; i++){
      sheet.autoSizeColumn((short)i);
    }
    setSizeColumn(sheet,maxWidth) ;
    sheet.protectSheet("100200");
    sheet.enableLocking();
  }

  /**
   *
   * @Title: createExcelCell
   * @Description: 创建一个单元格信息
   * @param @param workbook
   * @param @param buildDataList
   * @param @param workbookName
   * @param @param sheetName 参数
   * @return void 返回类型
   * @throws
   * @author: FengTao
   * @date 2020年10月15日
   */
  private static void createExcelCell( XSSFWorkbook workbook, XSSFSheet sheet ,  ExcelBuildEntity excelBuildEntity ,
                                       AtomicInteger startRow ,AtomicInteger startCell,AtomicInteger maxWidth ,AtomicInteger mergedLength , AtomicInteger headDeep){
    //表头开始行信息行
    XSSFRow xRow = sheet.getRow(startRow.get());
    //创建说明行
    XSSFRow memoRow = sheet.getRow(headDeep.intValue()+1);
    for(ExcelBuildEntity entity : excelBuildEntity.getChildren()){
      //设置填写字段
      XSSFCell xCell = xRow.createCell(startCell.get()) ;
      xCell.setCellValue(entity.getKeyName());
      xCell.setCellStyle( ExcelCellStyleUtils.buildDefaultCellStyle(workbook) );
      if(entity.getChildren() != null && entity.getChildren().size() > 0){ //含有子集表头信息,则优先创建父级表头信息
        mergedLength.compareAndSet( mergedLength.get() , 0 ) ;
        startRow.incrementAndGet() ;//下一层，层数增加
        createExcelCell( workbook ,sheet , entity , startRow , startCell , maxWidth, mergedLength , headDeep) ;
        //合并本层数据
        startRow.decrementAndGet() ;//跳回本层，层数-1，列数+1 ,列宽-1
        startCell.decrementAndGet() ;
        maxWidth.decrementAndGet() ;
        //子集修整完毕，上层向右合并
        CellRangeAddress cra = new CellRangeAddress(startRow.get(),startRow.get(),startCell.get()-mergedLength.get()+1, startCell.get()) ;
        setMergedRegion(workbook,sheet,cra) ;
      }else if(startRow.intValue() < headDeep.intValue()){
        //没有子集并且层级没到最后一级，则向下合并
        CellRangeAddress cra = new CellRangeAddress(startRow.get(),startRow.get()+1,startCell.get(), startCell.get()) ;
        setMergedRegion(workbook,sheet,cra) ;
      }
      if(entity.getChildren() == null || entity.getChildren().size() <= 0 ){ //没有子集并且遍历完毕
        //设置填写说明
        XSSFCell memoCell = memoRow.createCell(startCell.get()) ;
        memoCell.setCellValue(entity.getTips());
        memoCell.setCellStyle( ExcelCellStyleUtils.buildDefaultCellStyle(workbook) );
        if( entity.getPullDownList() !=null &&entity.getPullDownList().length > 0){
          System.out.println("创建下拉列表");
          //设置数据信息和格式信息
          int contentCol = startCell.get() ;
          //设置下拉选择
          setPullDownList(sheet, entity.getPullDownList(), headDeep.get()+3, 65535, contentCol, contentCol);
        }
      }
      mergedLength.incrementAndGet() ;
      startCell.incrementAndGet() ;
      maxWidth.incrementAndGet() ;
//            if( "carchar".equalsIgnoreCase(entity.getDataType()) ){ }
//            else if( "integer".equalsIgnoreCase(entity.getDataType()) ){ }
//            else if( "numeric".equalsIgnoreCase(entity.getDataType()) ){ }
//            else if( "date".equalsIgnoreCase(entity.getDataType())||"datetime".equalsIgnoreCase(entity.getDataType()) ){ }
    }
  }

  /**
   *
   * @Title: setMergedRegion
   * @Description: 合并单元格
   * @param @param workbook
   * @param @param sheet
   * @param @param cra 参数
   * @return void 返回类型
   * @throws
   * @author: FengTao
   * @date 2020年10月21日
   */
  private static void setMergedRegion( XSSFWorkbook workbook, XSSFSheet sheet ,CellRangeAddress cra){
    sheet.addMergedRegion(cra);
    // 使用RegionUtil类为合并后的单元格添加边框
    RegionUtil.setBorderBottom(1, cra, sheet, workbook); // 下边框
    RegionUtil.setBorderLeft(1, cra, sheet, workbook); // 左边框
    RegionUtil.setBorderRight(1, cra, sheet, workbook); // 有边框
    RegionUtil.setBorderTop(1, cra, sheet, workbook); // 上边框
  }
  /**
   *
   * @Title: setPullDownList
   * @Description: 为指定数据行列设置下拉选项
   * @param @param sheet
   * @param @param pullDownList
   * @param @param firstRow
   * @param @param lastRow
   * @param @param firstCol
   * @param @param lastCol 参数
   * @return void 返回类型
   * @throws
   * @author: FengTao
   * @date 2020年10月16日
   */
  private static void setPullDownList(XSSFSheet sheet , String[] pullDownList , int firstRow, int lastRow, int firstCol, int lastCol ){
    // 加载下拉列表内容
    XSSFDataValidation validation = null;
    XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
    XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint)dvHelper.createExplicitListConstraint(pullDownList);
    // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
    CellRangeAddressList regions = new CellRangeAddressList(firstRow,lastRow, firstCol, lastCol);
    // 数据有效性对象
    validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, regions);
    sheet.addValidationData(validation);
  }

  /**
   *
   * @Title: setSizeColumn
   * @Description: 手动设置列宽
   * @param @param sheet
   * @param @param maxWidth 参数
   * @return void 返回类型
   * @throws
   * @author: FengTao
   * @date 2020年10月16日
   */
  private static void setSizeColumn(XSSFSheet sheet ,AtomicInteger maxWidth) {
    for (int columnNum = 1; columnNum <= maxWidth.intValue(); columnNum++) {
      int columnWidth = sheet.getColumnWidth(columnNum) / 256;
      for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
        XSSFRow currentRow;
        //当前行未被使用过
        if (sheet.getRow(rowNum) == null) {
          currentRow = sheet.createRow(rowNum);
        } else {
          currentRow = sheet.getRow(rowNum);
        }

        if (currentRow.getCell(columnNum) != null) {
          XSSFCell currentCell = currentRow.getCell(columnNum);
          if (currentCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
            int length = ( currentCell.getStringCellValue().getBytes().length + currentCell.getStringCellValue().length() ) / 2;
            if (columnWidth < length) {
              columnWidth = length;
            }
          }
        }
      }
      sheet.setColumnWidth(columnNum, (int) (columnWidth * 256 * (1.3) ) > 10000 ? 10000 : (int) (columnWidth * 256 * (1.3) ) );
    }
  }

  /**
   *
   * @Title: getDeep
   * @Description: 获取树的深度
   * @param @param excelBuildEntity
   * @param @param deep
   * @param @param maxDeep
   * @param @return 参数
   * @return AtomicInteger 返回类型
   * @throws
   * @author: FengTao
   * @date 2020年10月21日
   */
  private static AtomicInteger getDeep(ExcelBuildEntity excelBuildEntity , AtomicInteger deep , AtomicInteger maxDeep){
    if(excelBuildEntity.getChildren().size() > 0){
      deep.incrementAndGet() ;
      for(ExcelBuildEntity temp : excelBuildEntity.getChildren()){
        if(temp.getChildren() != null){
          getDeep( temp , deep , maxDeep) ;
          if(maxDeep.intValue() < deep.intValue()){ maxDeep.compareAndSet(maxDeep.intValue(), deep.intValue()) ; }
          deep.decrementAndGet() ;
        }
      }
    }
    return maxDeep ;
  }

}
