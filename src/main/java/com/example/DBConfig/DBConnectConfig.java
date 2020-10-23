//package com.sunsheen.component.dbconnectconfig;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.example.DBConfig.ConnectionFactory;
//import com.example.DBConfig.DBConnectionComponent;
//import org.hibernate.transform.Transformers;
//import org.jboss.seam.annotations.Name;
//import org.jboss.seam.annotations.remoting.WebRemote;
//import com.alibaba.fastjson.JSON;
//import com.sunsheen.component.commons.utils.CommonTools;
//import com.sunsheen.component.commons.utils.ValidateUtil;
//import com.sunsheen.jfids.system.database.DBSession;
//import com.sunsheen.jfids.util.DataBaseUtil;
//
///**
// * 数据库连接配置
// * @author yanjc
// * @date 2019-07-12
// */
//@Name("com.sunsheen.component.dbconnectconfig.DBConnectConfig")
//public class DBConnectConfig {
//  /**
//   * 数据库连接测试
//   *
//   * @param param
//   * @return
//   */
//  @WebRemote
//  public HashMap<String, Object> testConnection(HashMap<String, Object> param) {
//    HashMap<String, Object> retmap = new HashMap<>();
//    try {
//      Connection conn = ConnectionFactory.getConnection(param);
//      if (!ValidateUtil.isEmpty(conn)) {
//        retmap.put("retcode", "1");
//        retmap.put("retmsg", "连接成功！");
//        conn.close();
//      }
//    } catch (Exception e) {
//      e.printStackTrace();
//      retmap.put("retcode", "0");
//      retmap.put("retmsg", "连接失败！");
//    }
//    return retmap;
//  }
//
//  /**
//   * 新增数据库连接
//   *
//   * @param param
//   * @return
//   */
//  @WebRemote
//  public HashMap<String, Object> addNew(HashMap<String, Object> param) {
//    HashMap<String, Object> retmap = new HashMap<>();
//    DBSession session = DataBaseUtil.getHibernateSession();
//    param.put("connection_id", CommonTools.getUUIDStr(32,1));//主键
//    param.put("created_time", CommonTools.getCurrentTime());//创建时间
//    int result = session.createDySQLQuery("DBConfig.databaseConfigInsert", param).executeUpdate();
//    if (result > 0) {
//      retmap.put("retcode", "1");
//      retmap.put("retmsg", "新增成功！");
//    }else {
//      retmap.put("retcode", "0");
//      retmap.put("retmsg", "新增失败！");
//    }
//    return retmap;
//  }
//
//  /**
//   * 修改数据库连接信息
//   * @param param
//   * @return
//   */
//  @WebRemote
//  public HashMap<String, Object> updateForm(HashMap<String, Object> param){
//    HashMap<String, Object> retmap = new HashMap<>();
//    DBSession session = DataBaseUtil.getHibernateSession();
//    int result = session.createDySQLQuery("DBConfig.databaseConfigUpdate", param).executeUpdate();
//    if (result > 0) {
//      retmap.put("retcode", "1");
//      retmap.put("retmsg", "修改成功！");
//    }else {
//      retmap.put("retcode", "0");
//      retmap.put("retmsg", "修改失败！");
//    }
//    return retmap;
//  }
//
//  /**
//   * 删除数据库连接信息
//   * @param param
//   * @return
//   */
//  @WebRemote
//  public HashMap<String, Object> delForm(HashMap<String, Object> param){
//    HashMap<String, Object> retmap = new HashMap<>();
//    DBSession session = DataBaseUtil.getHibernateSession();
//    int result = session.createDySQLQuery("DBConfig.databaseConfigDelete", param).executeUpdate();
//    if (result > 0) {
//      retmap.put("retcode", "1");
//      retmap.put("retmsg", "删除成功！");
//    }else {
//      retmap.put("retcode", "0");
//      retmap.put("retmsg", "删除失败！");
//    }
//    return retmap;
//  }
//
//
//  /**
//   * 获取数据库表
//   *
//   * @param param
//   * @return
//   */
//  @WebRemote
//  public HashMap<String, Object> getTables(HashMap<String, Object> param) {
//    HashMap<String, Object> retmap = new HashMap<>();
//    DBSession session = DataBaseUtil.getHibernateSession();
//    HashMap<String, Object> conn_map = (HashMap) session.createDySQLQuery("DBConfig.databaseConfigSelect", param)
//            .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
//    ArrayList<String> list = new ArrayList<>();
//    ArrayList<String> tables = new ArrayList<>();
//    try {
//      // 获取目标数据库连接
//      Connection connection = ConnectionFactory.getConnection(conn_map);
//      String sql = DBConnectionComponent.getQueryTableSql(conn_map);
//      PreparedStatement ps = connection.prepareStatement(sql);
//      ResultSet rs = ps.executeQuery();
//      while (rs.next()) {
//        // 拼接下拉列表数据
//        tables.add("{table_name:'" + rs.getString(1) + "',table_comment:'" + rs.getString(2) + "'}");
//        list.add("{code:'" + rs.getString(1) + "',text:'" + rs.getString(1) + "'}");
//      }
//      retmap.put("tables", tables.toString());
//      retmap.put("list", list.toString());
//      connection.close();
//    } catch (Exception e) {
//      e.printStackTrace();
//      retmap.put("retcode", "0");
//      retmap.put("retmsg", "数据库连接失败！");
//    }
//    return retmap;
//  }
//
//  /**
//   * 获取字段信息
//   *
//   * @param param
//   * @return
//   */
//  @WebRemote
//  public HashMap<String, Object> getField(HashMap<String, Object> param) {
//    HashMap<String, Object> retmap = new HashMap<>();
//    DBSession session = DataBaseUtil.getHibernateSession();
//    HashMap<String, Object> conn_map = (HashMap) session.createDySQLQuery("DBConfig.databaseConfigSelect", param)
//            .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
//    try {
//      // 获取目标数据库连接
//      Connection connection = ConnectionFactory.getConnection(conn_map);
//      conn_map.putAll(param);
//      //虚谷大小写问题，只能用sqlMap查询才能查出小写,查出覆盖list
//      if("xugu".equals(conn_map.get("database_type"))){
//        retmap.put("table_name",conn_map.get("table_name") ) ;
//        List<Map<String,Object>> test = session.createDySQLQuery("XuguConfig.getXuguColumns", retmap).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
//        retmap.put("list", JSON.toJSONString(test));
//      }else{
//        String sql = DBConnectionComponent.getQueryFieldSql(conn_map);
//        System.out.println("sql===="+sql);
//        PreparedStatement ps = connection.prepareStatement(sql);
//        ResultSet rs = ps.executeQuery();
//        retmap.put("list", JSON.toJSONString(CommonTools.convertList(rs)));
//      }
//
//      connection.close();
//    } catch (Exception e) {
//      e.printStackTrace();
//      retmap.put("retcode", "0");
//      retmap.put("retmsg", "字段获取失败！");
//    }
//    return retmap;
//  }
//
//
//}
