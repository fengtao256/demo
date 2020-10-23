package com.example.excelutil;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;


/**
 *
 * @Title: ExcelCellStyleUtils
 * @Description: 创建Excel使用的样式库
 * @author: FengTao
 * @date 2020年10月15日 上午11:56:58
 */
public class ExcelCellStyleUtils {

  /**
   *
   * @Title: buildDefaultCellStyle
   * @Description: 创建默认的样式
   * @param @param workbook
   * @param @return 参数
   * @return CellStyle 返回类型
   * @throws
   * @author: FengTao
   * @date 2020年10月15日
   */
  public static CellStyle buildDefaultCellStyle(Workbook workbook){
    CellStyle cellStyle = workbook.createCellStyle() ;
    Font font = workbook.createFont() ;
    //构件字体信息
    font.setFontName("宋体");
    font.setFontHeightInPoints((short)12);
    //设置默认字体信息
    cellStyle.setFont(font);
    //开启自动换行
    cellStyle.setWrapText(true);
    //背景颜色
    cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
    cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
    //边框
    cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
    cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
    cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
    cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
    cellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
    cellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
    cellStyle.setRightBorderColor(IndexedColors.BLACK.index);
    cellStyle.setTopBorderColor(IndexedColors.BLACK.index);
    //垂直
    cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    //居中
    cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
    cellStyle.setLocked(true);
    return cellStyle ;
  }

  /**
   *
   * @Title: buildBackgroundCellStyle
   * @Description: 设置背景色的样式
   * @param @param workbook
   * @param @return 参数
   * @return CellStyle 返回类型
   * @throws
   * @author: FengTao
   * @date 2020年10月15日
   */
  public static CellStyle buildBackgroundCellStyle(Workbook workbook){
    CellStyle cellStyle = workbook.createCellStyle() ;
    Font font = workbook.createFont() ;
    //构件字体信息
    font.setFontName("宋体");
    font.setFontHeightInPoints((short)12);
    //font.setBold(true);
    //设置默认字体信息
    cellStyle.setFont(font);
    //设置背景颜色
    cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
    cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
    //垂直
    cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    //居中
    cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
    cellStyle.setLocked(true);
    return cellStyle ;
  }

  /**
   *
   * @Title: buildContentCellStyle
   * @Description: 创建默认的样式
   * @param @param workbook
   * @param @return 参数
   * @return CellStyle 返回类型
   * @throws
   * @author: FengTao
   * @date 2020年10月15日
   */
  public static CellStyle buildContentCellStyle(Workbook workbook){
    CellStyle cellStyle = workbook.createCellStyle() ;
    Font font = workbook.createFont() ;
    //构件字体信息
    font.setFontName("宋体");
    font.setFontHeightInPoints((short)12);
    //font.setBold(true);
    //设置默认字体信息
    cellStyle.setFont(font);
    //开启自动换行
    cellStyle.setWrapText(true);
    //垂直
    cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    //居中
    cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
    cellStyle.setLocked(false);
    return cellStyle ;
  }

  /**
   *
   * @Title: buildHeadCellStyle
   * @Description: 表头格式信息
   * @param @param workbook
   * @param @return 参数
   * @return CellStyle 返回类型
   * @throws
   * @author: FengTao
   * @date 2020年10月16日
   */
  public static CellStyle buildHeadCellStyle(Workbook workbook){
    CellStyle cellStyle = workbook.createCellStyle() ;
    Font font = workbook.createFont() ;
    //构件字体信息
    font.setFontName("宋体");
    font.setFontHeightInPoints((short)14);
    font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD); //加粗
    cellStyle.setLocked(true);
    font.setBold(true); //加粗
    //设置默认字体信息
    cellStyle.setFont(font);
    //垂直
    cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    //居中
    cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
    //设置背景颜色
    cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
    cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
    //设置边框信息
    cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
    cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
    cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
    cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
    cellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
    cellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
    cellStyle.setRightBorderColor(IndexedColors.BLACK.index);
    cellStyle.setTopBorderColor(IndexedColors.BLACK.index);
    return cellStyle ;
  }
}
