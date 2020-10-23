package com.example.WordUtils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.*;


public class StationService {



    /**
     * 新增台站item信息
     * @return
     */
    public void insertStationsItem(Map<String, Object> dataMap,List<Map<String,Object>> dataList,Integer countPatient){
		//实例化SessionFactory
		SessionFactory sessionFactory = new Configuration().configure("WRSSKF.cfg.xml").buildSessionFactory() ;
        //遍历需要添加台站信息的list列
        Session session = sessionFactory.openSession() ;
        Transaction transaction = session.beginTransaction();
        String queryStationSql = "select count(*) from tb_patient where patient_name=? and patient_age=?" ;
        String insertSql = "insert into tb_patient ( patient_id, patient_name, patient_age, patient_in_time,patient_out_time,insert_time )\n" +
							"values( ?, ?, ?, ?, ? ,now())" ;
        String insertSql01 = "insert into tb_admission_record (id,patient_id,ZS,XBS,JWS,GRS,YJS,HYS,JZS,TGJC,ZKJC,ZYSZQK,RYZD,FZJC,insert_time)\n" +
							 "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())" ;
        String insertSql02 = "insert into tb_discharge_record (id,patient_id,RYSQK,RYZD,RYHZLJG,CYZD,CYQK,CYYZ,ZYTH,CYDY,insert_time)\n" +
							 "values(?,?,?,?,?,?,?,?,?,?,now())" ;
        String insertSql03 = "insert into tb_procedure_record (id,patient_id,p_day,p_hour,content,insert_time)\n" +
							 "values(?,?,?,?,?,now())" ;
        try{
        	Object num = session.createSQLQuery(queryStationSql).setString(0,(String) dataMap.get("patient_name"))
															.setString(1,(String)dataMap.get("patient_age"))
														 	.uniqueResult();
        	if(num.toString().equals("0")){//添加信息
        		String uuid01 = UUID.randomUUID().toString().replace("-","") ;
				String uuid02 = UUID.randomUUID().toString().replace("-","") ;
//				int effectNum = session.createSQLQuery(insertSql)
//						.setString(0, (String)dataMap.get("patient_id"))
//						.setString(1, (String)dataMap.get("patient_name"))
//						.setString(2, (String)dataMap.get("patient_age"))
//						.setString(3, (String)dataMap.get("patient_in_time"))
//						.setString(4, (String)dataMap.get("patient_out_time"))
//						.executeUpdate();
//				Thread.sleep(2);
//				int effectNum01 = session.createSQLQuery(insertSql01)
//						.setString(0,uuid01)
//						.setString(1, (String)dataMap.get("patient_id"))
//						.setString(2, (String)dataMap.get("ZS"))
//						.setString(3, (String)dataMap.get("XBS"))
//						.setString(4, (String)dataMap.get("JWS"))
//						.setString(5, (String)dataMap.get("GRS"))
//						.setString(6, (String)dataMap.get("YJS"))
//						.setString(7, (String)dataMap.get("HYS"))
//						.setString(8, (String)dataMap.get("JZS"))
//						.setString(9, (String)dataMap.get("TGJC"))
//						.setString(10, (String)dataMap.get("ZKJC"))
//						.setString(11, (String)dataMap.get("ZYSZQK"))
//						.setString(12, (String)dataMap.get("RYZD"))
//						.setString(13, (String)dataMap.get("FZJC"))
//						.executeUpdate();Thread.sleep(2);
//				int effectNum02 = session.createSQLQuery(insertSql02)
//						.setString(0,uuid02)
//						.setString(1, (String)dataMap.get("patient_id"))
//						.setString(2, (String)dataMap.get("RYSQK"))
//						.setString(3, (String)dataMap.get("RYZD"))
//						.setString(4, (String)dataMap.get("RYHZLJG"))
//						.setString(5, (String)dataMap.get("CYZD"))
//						.setString(6, (String)dataMap.get("CYQK"))
//						.setString(7, (String)dataMap.get("CYYZ"))
//						.setString(8, (String)dataMap.get("ZYTH"))
//						.setString(9, (String)dataMap.get("CYDY"))
//						.executeUpdate();Thread.sleep(2);
				int effectNum03 = 0 ;
                for ( Map<String, Object> map : dataList ) {
					String uuid03 = UUID.randomUUID().toString().replace("-","") ;
                    effectNum03 = session.createSQLQuery(insertSql03)
							.setString(0,uuid03)
                            .setString(1, (String)dataMap.get("patient_id"))
                            .setString(2, (String)map.get("day"))
                            .setString(3, (String)map.get("hour"))
                            .setString(4, (String)map.get("content"))
							.setString(5,(String)map.get("name"))
                            .executeUpdate();Thread.sleep(2);

                }
				if(effectNum03 > 0){
					System.out.println("添加成功");
					countPatient ++ ;
				}else{
					transaction.rollback();
				}
            }else{
				System.out.println("病人信息已存在");
            }

        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }finally {
            transaction.commit();
            session.close();
            sessionFactory.close();
        }
    }
}
