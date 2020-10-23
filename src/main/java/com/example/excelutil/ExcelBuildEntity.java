package com.example.excelutil;

import java.util.List;

public class ExcelBuildEntity {

  //字段名称
  private String keyId ;
  //显示名称
  private String keyName ;
  //字段类型
  private String dataType ;
  //填写提示
  private String tips ;
  //层级数量
  private String levelCount ;
  //子级表头
  private List<ExcelBuildEntity> children ;
  //填写框类型
  private String itemType ;
  //格式化格式
  private String formatStyle ;
  //下拉列表
  private String[] pullDownList ;

  public String getKeyId() {
    return keyId;
  }
  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }
  public String getKeyName() {
    return keyName;
  }
  public void setKeyName(String keyName) {
    this.keyName = keyName;
  }
  public String getDataType() {
    return dataType;
  }
  public void setDataType(String dataType) {
    this.dataType = dataType;
  }
  public String getTips() {
    return tips;
  }
  public void setTips(String tips) {
    this.tips = tips;
  }
  public String getLevelCount() {
    return levelCount;
  }
  public void setLevelCount(String levelCount) {
    this.levelCount = levelCount;
  }
  public List<ExcelBuildEntity> getChildren() {
    return children;
  }
  public void setChildren(List<ExcelBuildEntity> children) {
    this.children = children;
  }
  public String getItemType() {
    return itemType;
  }
  public void setItemType(String itemType) {
    this.itemType = itemType;
  }
  public String getFormatStyle() {
    return formatStyle;
  }
  public void pullDownList(String formatStyle) {
    this.formatStyle = formatStyle;
  }
  public String[] getPullDownList() {
    return pullDownList;
  }
  public void setPullDownList(String[] pullDownList) {
    this.pullDownList = pullDownList;
  }


}
