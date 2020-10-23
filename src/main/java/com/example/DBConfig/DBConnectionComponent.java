package com.example.DBConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

public class DBConnectionComponent {

  /**
   * 获取MySQL
   *
   * @param data
   * @return
   * @throws Exception
   */
  public static Connection getMySQLConnection(Map<String, Object> data) throws Exception {
    Connection con = null;
    Class.forName("com.mysql.jdbc.Driver");
    String url = "jdbc:mysql://" + data.get("ip") + ":" + data.get("port") + "/" + data.get("database_name")+"?useUnicode=true&characterEncoding=UTF-8";
    String user = (String) data.get("username");
    String password = (String) data.get("password");
    DriverManager.setLoginTimeout(3);
    con = DriverManager.getConnection(url, user, password);
    return con;
  }

  /**
   * 获取Oracle连接
   *
   * @param data
   * @return
   * @throws Exception
   */
  public static Connection getOracleConnection(Map<String, Object> data) throws Exception {
    Connection con = null;
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:" + "thin:@" + data.get("ip") + ":" + data.get("port") + ":" + data.get("database_name");
    String user = (String) data.get("username");
    String password = (String) data.get("password");
    DriverManager.setLoginTimeout(3);
    con = DriverManager.getConnection(url, user, password);
    return con;
  }

  /**
   * 获取Postgres连接
   *
   * @param data
   * @return
   * @throws Exception
   */
  public static Connection getPostgresConnection(Map<String, Object> data) throws Exception {
    Connection con = null;
    Class.forName("org.postgresql.Driver");
    String url = "jdbc:postgresql://" + data.get("ip") + ":" + data.get("port") + "/" + data.get("database_name");
    String user = (String) data.get("username");
    String password = (String) data.get("password");
    DriverManager.setLoginTimeout(3);
    con = DriverManager.getConnection(url, user, password);
    return con;
  }

  /**
   * 获取SQLserver连接
   *
   * @param data
   * @return
   * @throws Exception
   */
  public static Connection getSqlServerConnection(Map<String, Object> data) throws Exception {
    Connection con = null;
    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    String url = "jdbc:sqlserver://" + data.get("ip") + ":" + data.get("port") + ";databaseName=" + data.get("database_name");
    String user = (String) data.get("username");
    String password = (String) data.get("password");
    DriverManager.setLoginTimeout(3);
    con = DriverManager.getConnection(url, user, password);
    return con;
  }

  /**
   * 获取Xugu连接
   *
   * @param data
   * @return
   * @throws Exception
   */
  public static Connection getXuguConnection(Map<String, Object> data) throws Exception {
    Connection con = null;
    Class.forName("com.xugu.cloudjdbc.Driver");
    String url = "jdbc:xugu://" + data.get("ip") + ":" + data.get("port") + "/" + data.get("database_name");
    String user = (String) data.get("username");
    String password = (String) data.get("password");
    DriverManager.setLoginTimeout(3);
    con = DriverManager.getConnection(url, user, password);
    return con;
  }

  /**
   * 获取查询tables SQL语句
   *
   * @return
   */
  public static String getQueryTableSql(Map<String, Object> DBdata) {
    String sql = "";
    switch (DBdata.get("database_type").toString()) {
      case "Oracle":
        sql = "SELECT TABLE_NAME,COMMENTS as TABLE_COMMENT FROM USER_TAB_COMMENTS";
        ;
        break;
      case "MySQL":
        sql = "SELECT TABLE_NAME,TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' and TABLE_SCHEMA = '" + DBdata.get("database_name") + "'";
        break;
      case "SqlServer":
        sql = "SELECT Name FROM SysObjects Where XType='U' ORDER BY Name";
        break;
      case "Postgres":
        sql = "SELECT relname AS table_name, CAST ( obj_description (relfilenode,'pg_class') AS VARCHAR ) AS table_comment FROM pg_class  WHERE relkind = 'r' AND relname NOT LIKE 'pg_%' AND relname NOT LIKE 'sql_%' ORDER BY relname";
        break;
      case "Xugu":
        sql = "SELECT table_name,comments as table_comment FROM user_tables order by table_name" ;
    }
    return sql;
  }

  /**
   * 根据表名查询表字段信息
   * @param DBdata
   * @return
   */
  public static String getQueryFieldSql(Map<String, Object> DBdata){
    String sql = "";
    String table_name = DBdata.get("table_name").toString();
    //String username = DBdata.get("username").toString();
    String database_name = DBdata.get("database_name").toString();
    switch (DBdata.get("database_type").toString()) {
      case "Oracle":
        sql = "SELECT b.COLUMN_NAME as column_name,b.DATA_TYPE as column_type,A.COMMENTS as column_comment FROM USER_TAB_COLUMNS b,USER_COL_COMMENTS A WHERE b.TABLE_NAME = '"+ table_name +"' AND b.TABLE_NAME = A .TABLE_NAME AND b.COLUMN_NAME = A .COLUMN_NAME";
        break;
      case "MySQL":
        sql = "SELECT a.COLUMN_NAME AS column_name,a.COLUMN_TYPE AS column_type,a.COLUMN_COMMENT AS column_comment FROM information_schema. COLUMNS a where  a.table_name = '"+table_name+"' and a.table_schema = '"+database_name+"';";
        break;
      case "SqlServer":
        sql = "select cast([value] as varchar(500)) from_name ,a.name from_code,a.xprec from_length,c.name from_type from syscolumns a left join sys.extended_properties b  on  a.id=b.major_id and a.colid=b.minor_id left join systypes c on a.xtype=c.xtype where id=object_id('"+table_name+"');";
        break;
      case "Postgres":
        sql = "SELECT A.attname AS column_name,T.typname AS column_type,A.atttypmod AS from_length,	b.description AS column_comment FROM	pg_class C INNER JOIN pg_attribute A ON C .oid = A .attrelid INNER JOIN pg_type T ON A .atttypid = T .oid LEFT JOIN pg_description b ON b.objsubid = A .attnum AND C .oid = b.objoid WHERE	A .attnum > 0 AND C .relname = '"+table_name+"' ORDER BY A .attnum ;";
        break;
      case "Xugu" :
        sql = "SELECT x.COL_NAME AS column_name, x.TYPE_NAME AS column_type, x.SCALE AS from_length, x.COMMENTS AS column_comment FROM user_columns as x where table_id = ( select table_id from user_tables where table_name='"+table_name+"' );" ;
        break;
    }
    return sql;
  }
}
