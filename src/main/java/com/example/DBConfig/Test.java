package com.example.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws SQLException {
        Map<String,Object> DBdata = new HashMap<>() ;
        DBdata.put("database_name","WRSSKF") ; //数据库名称
        DBdata.put("table_name","ALGORITHMPRODUCT") ; //需要查询的表信息
        DBdata.put("database_type","Xugu") ; //数据库类型
        DBdata.put("ip","172.18.170.111") ;
        DBdata.put("port","5138") ;
        DBdata.put("username","SYSDBA") ;
        DBdata.put("password","SYSDBA") ;
        System.out.println("测试查询ALGORITHMPRODUCT表信息：");
        ResultSet rs  = null;
        PreparedStatement stat = null ;
        try {
            //获取数据库连接
            Connection connection = ConnectionFactory.getConnection(DBdata) ;
            stat = connection.prepareStatement(DBConnectionComponent.getQueryFieldSql(DBdata));
            rs = stat.executeQuery() ;

            while(rs.next()){
                System.out.println("{ "+rs.getString("column_name")+":"+rs.getString("column_comment")+" }");
            }
            connection.commit();
            connection.close();
        } catch (Exception e) {
            System.out.println("获取连接异常"+e.getMessage());
        }finally {
            if(rs != null ){
                rs.close();
            }
            if(stat != null) {
                stat.close();
            }
        }
    }
}
