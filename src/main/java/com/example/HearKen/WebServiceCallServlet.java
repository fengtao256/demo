//package com.sunsheen.cn.meteobasicbd.webservice.call;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.lang.reflect.Method;
//import java.net.URLDecoder;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import net.sf.json.JSONObject;
//
//import com.sunsheen.cn.meteobasicbd.webservice.MethodResolution;
//import com.sunsheen.cn.meteobasicbd.webservice.RtContent;
//import com.sunsheen.cn.meteobasicbd.webservice.TheNewIUDService;
//import com.sunsheen.cn.meteobasicbd.webservice.TheNewManyService;
//import com.sunsheen.cn.meteobasicbd.webservice.TheNewWebService;
//import com.sunsheen.cn.serviceAnalysis.DataTypeTo;
//import com.sunsheen.component.commons.utils.ValidateUtil;
//import com.sunsheen.jfids.system.servlet.Servlet;
//
//@Servlet(value="/data/webservice.svt" ,anonymous=true)
////@SuppressWarnings("serial")
//public class WebServiceCallServlet extends HttpServlet {
//    /**
//     *
//     */
//    private static final long serialVersionUID = 1L;
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        DataTypeTo too = new DataTypeTo();
//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html;charset=UTF-8");
//        request.setCharacterEncoding("utf-8");
//
//        PrintWriter pw = response.getWriter();
//        String ip = request.getServerName();// 如localhost,127.0.0.1
//        String port = request.getServerPort() + "";
//        String pname = request.getContextPath();
//        String url = "http://" + ip + ":" + port + pname;
//
//        Calendar c = Calendar.getInstance();
//        c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) - 8);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Map m = new HashMap();
//        String urlQuery = request.getQueryString();
//        System.out.println("getQueryString---------------------------------"+urlQuery);//get请求才能获取getQueryString
//        String requestParams = "";
//        if (urlQuery != null) {
//            urlQuery = urlQuery.replaceAll("%(?![0-9a-fA-F]{2})", "%25");// 传递%
//            urlQuery = URLDecoder.decode(urlQuery, "UTF-8");
//            urlQuery = replace(urlQuery);
//            String tempStr[] = urlQuery.split("&");
//            for (String temp : tempStr) {
//                if (!temp.startsWith("userId") && !temp.startsWith("pwd") && !temp.startsWith("dataFormat")) {// 去除用户名和密码
//                    requestParams = requestParams + "&" + temp;
//                }
//            }
//            if (requestParams.length() >= 1) {
//                m.put("requestParams", requestParams.substring(1));
//            } else {
//                m.put("requestParams", requestParams);
//            }
//        }//获取get请求的参数
//
//        Map<String, Object> returnMap = new HashMap();
//        if (urlQuery == null || "".equals(urlQuery) || "isRest=T&".equals(urlQuery)) {// post提交
//            Map properties = request.getParameterMap();
//
//            // 返回值Map
//            Iterator entries = properties.entrySet().iterator();
//            Map.Entry entry;
//            String name = "";
//            String value = "";
//            while (entries.hasNext()) {
//                entry = (Map.Entry) entries.next();
//                name = (String) entry.getKey();
//                name = replace(name);
//                Object valueObj = entry.getValue();
//
//                if (null == valueObj) {
//                    value = "";
//                } else if (valueObj instanceof String[]) {
//                    String[] values = (String[]) valueObj;
//                    for (int i = 0; i < values.length; i++) {
//                        value = values[i] + ",";
//                    }
//                    value = value.substring(0, value.length() - 1);
//                } else {
//                    value = valueObj.toString();
//                }
//
//                value = replace(value);
//                returnMap.put(name, new String(value.getBytes("iso8859-1"), "utf-8"));
//                if (!"userId".equalsIgnoreCase(name) && !"pwd".equals(name) && !"dataFormat".equals(name)) {// 去除用户名和密码
//                    requestParams = requestParams + "&" + name + "=" + new String(value.getBytes("iso8859-1"), "utf-8");
//                }
//            }
//            if (requestParams.length() >= 1) {
//                m.put("requestParams", requestParams.substring(1));
//                System.out.println("requestParams---------------------------------"+requestParams);
//            } else {
//                m.put("requestParams", requestParams);
//            }
//
//        }//参数简单处理
//
//        // 判断是否是Rest接口调用
//        String isRest = "F";
//        // 判断是否是多数据集
//        String isMore = "F";
//        if (request.getParameter("isRest") != null && request.getParameter("isRest") != "") {
//            isRest = request.getParameter("isRest");
//        }
//        if (request.getParameter("isMore") != null && request.getParameter("isMore") != "") {
//            isMore = request.getParameter("isMore");
//        }
//        // 服务id
//        long startTime = System.currentTimeMillis();// 执行SQL开始时间
//        m.put("startDate", sdf.format(c.getTime()));
//        m.put("startTime", startTime);
//
//        String sid = replace(request.getParameter("sid"));
//        // 判断是否是服务测试
//        String isTest = "F";
//        if (request.getParameter("isTest") != null && request.getParameter("isTest") != "") {
//            isTest = request.getParameter("isTest");
//            // m.put("ctType", "array");
//        }
//
//        if (sid == null || "".equals(sid) || "null".equals(sid)) {//sid是服务ID，也就是资料集ID
//            RtContent rtc = new RtContent();//返回结果格式化
//            Method met;
//            Map retMap = new HashMap();
//            retMap.put("returnCode", "-2001");
//            retMap.put("returnMessage", "Non InterfaceId");
//            long endTime = System.currentTimeMillis();// 执行SQL结束时间
//            float seconds = (endTime - Long.valueOf(m.get("startTime").toString())) / 1000F;// 执行SQL耗时，单位为秒
//            Calendar c2 = Calendar.getInstance();
//            c2.set(Calendar.HOUR_OF_DAY, c2.get(Calendar.HOUR_OF_DAY) - 8);
//            retMap.put("requestTime", m.get("startDate"));
//            retMap.put("rowCount", "0");
//            retMap.put("responseTime", sdf.format(c2.getTime()));
//            retMap.put("takeTime", seconds);
//            if (!"HTML".equals(dataFormat.toUpperCase()) && !"XML".equals(dataFormat.toUpperCase())) {
//                retMap.put("requestParams", m.get("requestParams"));
//            }
//            try {
//                met = rtc.getClass().getDeclaredMethod("get" + dataFormat.toUpperCase(), Map.class);
//                String content1 = (String) met.invoke(rtc.getClass().newInstance(), retMap);
//                retMap.put("content", content1);
//                System.out.println("content1---------------------------------"+content1);
//            } catch (Exception e) {
//                retMap.put("content", e.getMessage());
//            }
//            if ("T".equals(isTest)) {
//                pw.print(JSONObject.fromObject(retMap));
//            } else {
//                pw.print(retMap.get("content").toString());
//            }
//            pw.flush();
//            pw.close();
//        }//参数错误，错误返回结果
//        String dataFormat = replace(request.getParameter("dataFormat"));
//        String elements = replace(request.getParameter("elements"));//字段要素
//        String statEles = replace(request.getParameter("statEles"));//不需要
//        String dataCode = replace(request.getParameter("dataCode"));//获取资料集代码
//        List listNeed = getServiceNeed(sid);// 获取固定值信息
//        System.out.println("listNeed---------------------------------"+listNeed.toString());
//        for (int needNum = 0; needNum < listNeed.size(); needNum++) {
//            Map mapNeed = (Map) listNeed.get(needNum);
//            if ("dataCode".equals(mapNeed.get("dataitemcode"))) {// 资料集ID
//                dataCode = mapNeed.get("content") + "";
//            } else if ("elements".equals(mapNeed.get("dataitemcode"))) {// 字段信息
//                elements = mapNeed.get("content") + "";
//            } else if ("statEles".equals(mapNeed.get("dataitemcode"))) {
//                statEles = mapNeed.get("content") + "";
//                m.put(mapNeed.get("dataitemcode"), mapNeed.get("content"));
//            } else {
//                m.put(mapNeed.get("dataitemcode"), mapNeed.get("content"));
//            }
//        }//遍历固定值
//        if (dataCode == null || "".equals(dataCode) || "null".equals(dataCode)) {
//            RtContent rtc = new RtContent();
//            Method met;
//            Map retMap = new HashMap();
//            retMap.put("returnCode", "-2002");
//            retMap.put("returnMessage", "Non DataCode");
//            Calendar c2 = Calendar.getInstance();
//            c2.set(Calendar.HOUR_OF_DAY, c2.get(Calendar.HOUR_OF_DAY) - 8);
//            long endTime = System.currentTimeMillis();// 执行SQL结束时间
//            float seconds = (endTime - Long.valueOf(m.get("startTime").toString())) / 1000F;// 执行SQL耗时，单位为秒
//            retMap.put("requestTime", m.get("startDate"));
//            retMap.put("rowCount", "0");
//            retMap.put("responseTime", sdf.format(c2.getTime()));
//            retMap.put("takeTime", seconds);
//            if (!"HTML".equals(dataFormat.toUpperCase()) && !"XML".equals(dataFormat.toUpperCase())) {
//                retMap.put("requestParams", m.get("requestParams"));
//            }
//            try {
//                met = rtc.getClass().getDeclaredMethod("get" + dataFormat.toUpperCase(), Map.class);
//                String content1 = (String) met.invoke(rtc.getClass().newInstance(), retMap);
//                retMap.put("content", content1);
//            } catch (Exception e) {
//                retMap.put("content", e.getMessage());
//            }
//            if ("T".equals(isTest)) {
//                pw.print(JSONObject.fromObject(retMap));
//            } else {
//                pw.print(retMap.get("content").toString());
//            }
//            pw.flush();
//            pw.close();
//        }//dataCode不能为空
//        String orderBy = replace(request.getParameter("orderBy"));
//        String limitCnt = replace(request.getParameter("limitCnt"));
//        String dbType = TheNewWebService.getSpace(); //数据库种类postgres
//        // 参数字段以逗号隔开
//        String keys = replace(request.getParameter("keys"));
//        // 资料集code
//        String cid = replace(request.getParameter("dataCode"));
//        System.out.println("sid===="+sid+"--------------cid===="+cid);
//        boolean ifFileCatalog = getFileCatalog(dataCode);
//        if (ifFileCatalog == true) {// 如果是非结构化资料集则添加默认要素
//            System.out.println("是非结构化资料集！！");
//            if (elements == null || "".equals(elements)) {
//                elements = "FILE_NAME,FORMAT,FILE_SIZE,FILE_URL";
//            } else {
//                String[] eles = elements.split(",");
//                String elements1 = "";
//                for (int i = 0; i < eles.length; i++) {
//                    if (eles[i].toUpperCase().equals("FILE_NAME") || eles[i].toUpperCase().equals("FORMAT")
//                            || eles[i].toUpperCase().equals("FILE_SIZE") || eles[i].toUpperCase().equals("FILE_URL")) {
//                        System.out.println("有重复字段！！");
//                    } else {
//                        elements1 = elements1 + "," + eles[i];
//                    }
//                }
//                elements = "FILE_NAME,FORMAT,FILE_SIZE,FILE_URL" + elements1;
//            }
//        }
//        m.put("url", url);
//        // 返回格式
//        m.put("ctType", dataFormat);
//
//        // 服务id
//        m.put("sid", sid);
//        // 资料集id
//        m.put("cid", cid);
//        m.put("userip", ip);
//        // System.out.println("密码===="+Session.getCurrUser().getPassword());
//        // 用户
//        m.put("uid", replace(request.getParameter("userId")));
//        m.put("pwd", replace(request.getParameter("pwd")));
//        System.out.println("asdasdassdnsaldm==========="+m);
//        if (isMore.equals("T")) {
//            m.put("isMore", "T");
//            // 获取虚拟表关联的资料集和虚拟表(重命名的字段)
//            Map data = getDataCodeByMore(sid, too);
//            m.put("dataCode", data.get("dataCode"));
//            m.put("dataCode2", data.get("dataCode2"));
//            m.put("dataCode3", data.get("dataCode3"));
//            m.put("elements", data.get("elements"));
//            // 服务类型00表示查询、11表示统计、20表示新增、25表示修改、30表示删除（用于判断是否需要group by）
//            m.put("apply_type", data.get("apply_type"));
//            m.put("cid", data.get("cids"));
//            m.put("sid", data.get("service_id"));
//        } else {
//            m.put("isMore", "F");
//            String[] eles = elements.split(",");
//            String[] counteles = null;
//            try {
//                counteles = statEles.split(",");
//            } catch (Exception e) {
//                // TODO: handle exception
//                counteles = new String[0];
//            }
//            m.put("colCount", 0);
//            List datas = getDataCode(sid, dataCode, eles, counteles, too);
//            System.out.println("asda---------"+datas);
//            if (datas.size() <= 1) {// 单数据集
//                Map data = (Map) datas.get(0);
//                m.put("dataCode", data.get("dataclassid"));
//                m.put("apply_type", data.get("apply_type"));
//                m.put("elements2", data.get("elements2"));
//                m.put("elements", data.get("elements"));
//                m.put("cid", data.get("comrescatalogid"));
//                m.put("sid", data.get("service_id"));
//                m.put("rescatacode", data.get("rescatacode"));
//                m.put("countonly", data.get("countonly"));
//                m.put("elements5", data.get("elements5"));
//                m.put("elements4", data.get("elements4"));
//            } else {// 虚拟单数据集
//                String dataclassid = "";
//                String dataclassid2 = "";
//                String joinon = "";
//                String comrescatalogid = "";
//                String apply_type = "";
//                String elements2 = "";
//                String elementss = "";
//                String elements5 = "";
//                String service_id = "";
//                String str1 = "";
//                String str2 = "";
//                for (int i = 0; i < datas.size(); i++) {
//                    Map data2 = (Map) datas.get(i);
//                    if (i == 0 && datas != null && !"".equals(datas) && !"null".equals(datas)) {
//                        apply_type = data2.get("apply_type").toString();
//                        service_id = data2.get("service_id").toString();
//                        elements2 = elements2 + "," + data2.get("elements2");
//                        elementss = elementss + "," + data2.get("elements");
//                        elements5 = elements5 + "," + data2.get("elements5");
//                    } else if (i > 0) {// 去除重复的字段，只保留主表的
//                        String[] temp1 = null;
//                        String[] temp2 = null;
//                        String str3 = data2.get("elements3") + "";
//                        elements5 = elements5 + "," + data2.get("elements5");
//                        try {
//                            temp1 = data2.get("elements").toString().split(",");
//                            temp2 = data2.get("elements2").toString().split(",");
//                            for (int j = 0; j < temp1.length; j++) {
//                                if (!str3.contains(temp1[j])) {
//                                    str1 = str1 + "," + temp1[j];
//                                }
//                            }
//                            for (int j = 0; j < temp2.length; j++) {
//                                if (!str3.contains(temp2[j].substring(0, temp2[j].indexOf(" as")))
//                                        && !elements2.contains(temp2[j].substring(temp2[j].indexOf(" as") + 3))) {
//                                    str2 = str2 + "," + temp2[j];
//                                }
//                            }
//                        } catch (Exception e) {
//                            // TODO: handle exception
//                        }
//
//                        if (str1.length() > 1) {
//                            data2.put("elements", str1.substring(1));
//                            elementss = elementss + "," + data2.get("elements");
//                        }
//                        if (str2.length() > 1) {
//                            data2.put("elements2", str2.substring(1));
//                            elements2 = elements2 + "," + data2.get("elements2");
//                        }
//                    }
//                    dataclassid = dataclassid + " LEFT JOIN " + data2.get("dataclassid");
//                    dataclassid2 = dataclassid2 + "," + data2.get("dataclassid");
//                    joinon = joinon + "," + data2.get("elements3");
//                    comrescatalogid = comrescatalogid + "," + data2.get("comrescatalogid") + "";
//                }
//                dataclassid = dataclassid.substring(11);
//                dataclassid2 = dataclassid2.substring(1);
//                joinon = joinon.substring(1);// 拼接left join on
//                StringBuffer sb = new StringBuffer();
//                String[] temp = joinon.split(",");
//                for (int i = 0; i < temp.length / 2; i++) {
//                    if (!"".equals(temp[i]) && temp[i] != null)
//                        sb.append(temp[i] + "=" + temp[i + temp.length / 2] + " and ");
//                }
//                String result = sb.toString().substring(0, sb.toString().length() - 4);
//                comrescatalogid = comrescatalogid.substring(1);
//                elements2 = elements2.substring(1);
//                elementss = elementss.substring(1);
//                m.put("dataCode", dataclassid + " ON " + result);
//                m.put("dataCode2", dataclassid2);// 检查权限
//                m.put("apply_type", apply_type);
//                m.put("elements2", elements2);
//                m.put("elements", elementss);
//                if (elements5.length() > 1) {
//                    m.put("elements5", elements5.substring(1));
//                } else {
//                    m.put("elements5", elements5);
//                }
//                m.put("cid", comrescatalogid);
//                m.put("sid", service_id);
//            }
//        }
//
//        // System.out.println("elements--------------------------------------------"+m.get("elements"));
//        if (keys != null && !"".equals(keys)) {
//            // 所有的参数字段，并赋值
//            keys = new String(request.getParameter("keys").getBytes("ISO8859-1"), "UTF-8");
//            String[] zds = keys.split(",");
//            for (int i = 0; i < zds.length; i++) {
//                m.put(zds[i], replace(new String(request.getParameter(zds[i]).getBytes("ISO8859-1"), "UTF-8")));
//            }
//            for (int needNum = 0; needNum < listNeed.size(); needNum++) {
//                Map mapNeed = (Map) listNeed.get(needNum);
//                if ("dataCode".equals(mapNeed.get("dataitemcode")) || "elements".equals(mapNeed.get("dataitemcode"))) {// 资料集ID
//                    continue;
//                } else {
//                    keys += "," + mapNeed.get("dataitemcode");
//                }
//            }
//            m.put("keys", keys);
//        } else if (urlQuery != null && !"".equals(urlQuery) && !"isRest=T&".equals(urlQuery)) {// get提交
//            // url参数键值对
//            keys = "";
//            try {
//                String[] keyValues = urlQuery.split("&");
//                for (int i = 0; i < keyValues.length; i++) {
//                    String[] key_value = keyValues[i].split("=");
//                    // 基础字段不需要再传入解析
//                    if (key_value.length == 2 && "dataFormat,interfaceId,dataCode,userId,pwd,elements,limitCnt,isMore".indexOf(key_value[0]) < 0) {
//                        if (keys.equals("")) {
//                            keys = key_value[0];
//                        } else {
//                            keys += "," + key_value[0];
//                        }
//                        // 放参数字段和值
//                        m.put(key_value[0], key_value[1]);
//                    }
//                }
//                for (int needNum = 0; needNum < listNeed.size(); needNum++) {
//                    Map mapNeed = (Map) listNeed.get(needNum);
//                    if ("dataCode".equals(mapNeed.get("dataitemcode")) || "elements".equals(mapNeed.get("dataitemcode"))) {// 资料集ID
//                        continue;
//                    } else {
//                        keys += "," + mapNeed.get("dataitemcode");
//                    }
//                }
//            } catch (Exception e) {
//                // TODO: handle exception
//            }
//            m.put("keys", keys);
//        } else {// post提交
//            keys = "";
//            for (String key : returnMap.keySet()) {
//                if ("dataFormat,interfaceId,dataCode,userId,pwd,elements,limitCnt,isMore".indexOf(key) < 0) {
//                    if (keys.equals("")) {
//                        keys = key;
//                    } else {
//                        keys += "," + key;
//                    }
//                    m.put(key, returnMap.get(key));
//                }
//            }
//            for (int needNum = 0; needNum < listNeed.size(); needNum++) {
//                Map mapNeed = (Map) listNeed.get(needNum);
//                if ("dataCode".equals(mapNeed.get("dataitemcode")) || "elements".equals(mapNeed.get("dataitemcode"))) {// 资料集ID
//                    continue;
//                } else {
//                    keys += "," + mapNeed.get("dataitemcode");
//                }
//            }
//            m.put("keys", keys);
//        }
//        m.put("orderBy", orderBy);
//        // sql信息拼装加传递
//        List<Map<String, Object>> listType = too.getList();
//        String sql = "";
//        try {
//            sql = new MethodResolution(m, listType).sql;
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//        if (sql != null && !"".equals(sql)) {
//            if (limitCnt != null && !"".equals(limitCnt)) {
//                if (!dbType.equals("oracle")) {
//                    sql += " limit " + limitCnt;
//                } else {
//                    sql = "select * from (" + sql + ") where rownum<=" + limitCnt;
//                }
//            }
//            m.put("sql", sql);
//            // 是否是服务测试
//            m.put("isTest", isTest);
//            Map retMap = new HashMap();
//            if (isRest.equals("F")) {
//                WebServiceCall wsc = new WebServiceCall();
//                retMap = wsc.call(m);
//            } else {
//                retMap = new RestCall().call(m);
//            }
//            // 服务测试返回参数
//            if (isTest.equals("T")) {
//                if (retMap.get("returnCode").equals("R000")) {
//                    retMap.put("sql", sql);
//                    try {
//                        pw.print(JSONObject.fromObject(retMap));
//                    } catch (Exception e) {
//                        // TODO: handle exception
//                        Map retMap2 = new HashMap();
//                        retMap2.put("content", "服务器端异常,请联系管理员！");
//                        pw.print(JSONObject.fromObject(retMap2));
//                    }
//                } else {
//                    pw.print(JSONObject.fromObject(retMap));
//                }
//            } else {
//                // 服务调用成功的返回参数
//                if (retMap.get("returnCode").equals("R000")) {
//                    pw.print(retMap.get("content").toString());
//                } else {
//                    // Map retMap2=new HashMap();
//                    // retMap2.put("content",retMap.get("content"));
//                    if (retMap.get("content") != null && !"".equals(retMap.get("content"))) {
//                        pw.print(retMap.get("content").toString()); // 改动前是返回的retMap2
//                    } else {
//                        pw.print(JSONObject.fromObject(retMap));
//                    }
//                }
//            }
//        } else {
//            RtContent rtc = new RtContent();
//            Method met;
//            Map retMap = new HashMap();
//            retMap.put("returnCode", "-3001");
//            retMap.put("returnMessage", "参数输入值有误，若无误请联系管理员修改接口配置！");
//            Calendar c2 = Calendar.getInstance();
//            c2.set(Calendar.HOUR_OF_DAY, c2.get(Calendar.HOUR_OF_DAY) - 8);
//            long endTime = System.currentTimeMillis();// 执行SQL结束时间
//            float seconds = (endTime - Long.valueOf(m.get("startTime").toString())) / 1000F;// 执行SQL耗时，单位为秒
//            retMap.put("requestTime", m.get("startDate"));
//            retMap.put("rowCount", "0");
//            retMap.put("responseTime", sdf.format(c2.getTime()));
//            retMap.put("takeTime", seconds);
//            if (!"HTML".equals(dataFormat.toUpperCase()) && !"XML".equals(dataFormat.toUpperCase())) {
//                retMap.put("requestParams", m.get("requestParams"));
//            }
//            try {
//                met = rtc.getClass().getDeclaredMethod("get" + dataFormat.toUpperCase(), Map.class);
//                String content1 = (String) met.invoke(rtc.getClass().newInstance(), retMap);
//                retMap.put("content", content1);
//            } catch (Exception e) {
//                retMap.put("content", e.getMessage());
//            }
//            if ("T".equals(isTest)) {
//                pw.print(JSONObject.fromObject(retMap));
//            } else {
//                pw.print(retMap.get("content").toString());
//            }
//        }
//        pw.flush();
//        pw.close();
//    }
//
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        doGet(req, resp);
//    }
//
//    // 获取固定值
//    public static List getServiceNeed(String serviceid) {
//        List m = new ArrayList();
//        List<Map<String, Object>> listServiceNeed = TheNewWebService.getListServiceNeed();
//        for (int i = 0; i < listServiceNeed.size(); i++) {
//            Map<String, Object> maps = listServiceNeed.get(i);
//            if (serviceid.equals(maps.get("servicecode"))) {// 是固定值
//                m.add(maps);
//            }
//        }
//        return m;
//    }
//
//    // 查询是否是非结构化
//    public static boolean getFileCatalog(String dataCode) {
//        List<Map<String, Object>> listcatalog = TheNewWebService.getListFileCatalog();
//        for (int i = 0; i < listcatalog.size(); i++) {
//            Map<String, Object> maps = listcatalog.get(i);
//            if (!ValidateUtil.isEmpty(dataCode) && (dataCode.equals(maps.get("rescatacode")) || dataCode.equals(maps.get("rescataen")))) {// 是
//                return true;
//            }
//        }
//        return false;
//    }
//
//    // 获取单数据集的资料集表名、服务类型
//    public static List getDataCode(String serviceid, String tableEn, String[] eles, String[] counteles, DataTypeTo too) {
//        List<Map<String, Object>> listType = new ArrayList<Map<String, Object>>();
//        Map<String, Object> datas = new HashMap<String, Object>();
//        /*
//         * 查询是否是虚拟资料集
//         */
//        List m = new ArrayList();
//        Map m1 = new HashMap();
//        m1.put("count", 0);
//        List<Map<String, Object>> listInvented = TheNewWebService.getListInvented();
//        for (int i = 0; i < listInvented.size(); i++) {
//            Map<String, Object> maps = listInvented.get(i);
//            if (serviceid.equals(maps.get("servicecode")) && tableEn.equals(maps.get("catalogen"))) {// 是虚拟资料集
//                m1 = maps;
//                datas.put("invented_catalog_id", m1.get("invented_catalog_id"));
//                break;
//            }
//        }
//        if (Integer.parseInt(m1.get("count").toString()) > 0) {// 是虚拟资料集
//            String elements = "";
//            String elements2 = "";
//            String elements3 = "";
//            String elements5 = "";// 中文名
//            String comrescatalogid = "";
//            List<Map<String, Object>> listCatalog = TheNewWebService.getListInventedAll();// 匹配用
//            List<Map<String, Object>> listCatalogSeq = TheNewWebService.getListInventedAllSeq();// 排序用
//            List<Map<String, Object>> listTypeSeq = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> listInventedItme = TheNewWebService.getListInventedItem();
//            for (int i = 0; i < listCatalog.size(); i++) {// 添加listtype
//                Map<String, Object> maps = listCatalog.get(i);
//                if (m1.get("invented_catalog_id").toString().equals(maps.get("invented_catalog_id")) && serviceid.equals(maps.get("servicecode"))) {// 满足条件
//                    listType.add(maps);
//                }
//            }
//            for (int i = 0; i < listCatalogSeq.size(); i++) {// 添加listtype排序用
//                Map<String, Object> maps = listCatalogSeq.get(i);
//                if (m1.get("invented_catalog_id").toString().equals(maps.get("invented_catalog_id")) && serviceid.equals(maps.get("servicecode"))) {// 满足条件
//                    listTypeSeq.add(maps);
//                }
//            }
//            for (int i = 0; i < listTypeSeq.size(); i++) {
//                Map<String, Object> maps = listTypeSeq.get(i);
//                comrescatalogid = maps.get("comrescatalogid").toString();
//                // 拼接elements几个参数
//                for (int j = 0; j < eles.length; j++) {// 英文名等于list中的英文名，则进行字段的拼装
//                    if (eles[j].toUpperCase().equals(maps.get("name_en").toString().toUpperCase())) {
//                        elements = elements + "," + maps.get("dataclassid") + "." + maps.get("name_item");
//                        elements2 = elements2 + "," + maps.get("dataclassid") + "." + maps.get("name_item") + " as " + maps.get("name_en");
//                        elements5 = elements5 + "," + maps.get("name_en") + " as " + maps.get("name");
//                    }
//                }
//                for (int k = 0; k < listInventedItme.size(); k++) {// 单独拼接elements3
//                    if (maps.get("invented_catalog_id").toString().equals(listInventedItme.get(k).get("invented_catalog_id"))
//                            && comrescatalogid.equals(listInventedItme.get(k).get("rescatalogid"))
//                            && maps.get("name_item").toString().toUpperCase().equals(listInventedItme.get(k).get("dataitemcode"))) {
//                        elements3 = elements3 + "," + maps.get("dataclassid") + "." + maps.get("name_item");
//                    }
//                }
//                if ((i < listTypeSeq.size() - 1 && !comrescatalogid.equals(listTypeSeq.get(i + 1).get("comrescatalogid")))
//                        || i == listTypeSeq.size() - 1) {// 每个资料集最后一个进行拼接全部参数
//                    Map m2 = new HashMap();
//                    m2.put("comrescatalogid", maps.get("comrescatalogid"));
//                    m2.put("service_id", maps.get("service_id"));
//                    m2.put("servicecode", maps.get("servicecode"));
//                    m2.put("dataclassid", maps.get("dataclassid"));
//                    m2.put("apply_type", maps.get("apply_type"));
//                    if (elements.length() > 1) {
//                        elements = elements.substring(1);
//                    }
//                    if (elements2.length() > 1) {
//                        elements2 = elements2.substring(1);
//                    }
//                    if (elements3.length() > 1) {
//                        elements3 = elements3.substring(1);
//                    }
//                    if (elements5.length() > 1) {
//                        elements5 = elements5.substring(1);
//                    }
//                    m2.put("elements", elements);
//                    m2.put("elements2", elements2);
//                    m2.put("elements3", elements3);
//                    m2.put("elements5", elements5);
//                    m.add(m2);
//                    elements = "";
//                    elements2 = "";
//                    elements3 = "";
//                    elements5 = "";
//                }
//            }
//        } else {
//            /*
//             * 不是虚拟资料集
//             */
//            String elements = "";
//            String elements2 = "";
//            String elements5 = "";// 中文名
//            String elements4 = "";// 统计字段的字段名
//            Map m2 = new HashMap();
//            int isFirst = 0;// 判断是否第一个
//            List<Map<String, Object>> listCatalog = TheNewWebService.getListCatalogAll();
//            for (int i = 0; i < listCatalog.size(); i++) {
//                Map<String, Object> maps = listCatalog.get(i);
//                if (serviceid.equals(maps.get("servicecode")) && tableEn.equals(maps.get("rescataen"))) {// 满足条件
//                    if (isFirst == 0) {// 拼接相同参数
//                        m2.put("comrescatalogid", maps.get("comrescatalogid"));
//                        m2.put("service_id", maps.get("service_id"));
//                        m2.put("servicecode", maps.get("servicecode"));
//                        m2.put("dataclassid", maps.get("dataclassid"));
//                        m2.put("apply_type", maps.get("apply_type"));
//                        m2.put("rescatacode", maps.get("rescatacode"));
//                        m2.put("countonly", maps.get("countonly"));
//                        isFirst++;
//                    }
//                    listType.add(maps);
//                }
//            }
//            for (int j = 0; j < eles.length; j++) {// 英文名等于list中的英文名，则进行字段的拼装
//                for (int i = 0; i < listType.size(); i++) {
//                    Map<String, Object> maps = listType.get(i);
//                    if (eles[j].toUpperCase().equals(maps.get("name_en").toString().toUpperCase())) {
//                        elements = elements + "," + maps.get("name_item");
//                        elements2 = elements2 + "," + maps.get("name_item") + " as " + maps.get("name_en");
//                        elements5 = elements5 + "," + maps.get("name_en") + " as " + maps.get("name");
//                        eles[j] = "#";
//                    }
//                }
//            }
//            for (int j = 0; j < counteles.length; j++) {// 英文名等于list中的英文名，则进行字段的拼装
//                for (int i = 0; i < listType.size(); i++) {
//                    Map<String, Object> maps = listType.get(i);
//                    if (counteles[j].substring(counteles[j].indexOf("_") + 1).toUpperCase().equals(maps.get("name_en").toString().toUpperCase())) {
//                        elements5 = elements5 + "," + counteles[j].substring(0, counteles[j].indexOf("_") + 1) + maps.get("name_en") + " as "
//                                + counteles[j].substring(0, counteles[j].indexOf("_") + 1) + maps.get("name");
//                        elements4 = elements4 + "," + maps.get("name_item").toString();
//                    }
//                }
//            }
//            for (int i = 0; i < eles.length; i++) {
//                if (!"#".equals(eles[i])) {
//                    elements = elements + "," + eles[i];
//                    elements2 = elements2 + "," + eles[i] + " as " + eles[i];
//                    elements5 = elements5 + "," + eles[i] + " as " + eles[i];
//                }
//            }
//            if (elements.length() > 1 && elements2.length() > 1) {
//                m2.put("elements", elements.substring(1));
//                m2.put("elements2", elements2.substring(1));
//                m2.put("elements5", elements5.substring(1));
//            } else {
//                m2.put("elements", elements);
//                m2.put("elements2", elements2);
//                m2.put("elements5", elements5);
//            }
//            if (elements4.length() > 1) {
//                m2.put("elements4", elements4.substring(1));
//            } else {
//                m2.put("elements4", elements4);
//            }
//            m.add(m2);
//        }
//        too.setList(listType);// 设置字段到转换
//        return m;
//
//    }
//
//    // 获取多数据集的dataCode,elements,服务id,服务类型
//    public static Map getDataCodeByMore(String serviceid, DataTypeTo too) {
//        List<Map<String, Object>> listType = new ArrayList<Map<String, Object>>();
//        Map m1 = new HashMap();// 查询是否有虚拟资料集
//        List<Map<String, Object>> listInvented = TheNewManyService.getListInvented();
//        for (int i = 0; i < listInvented.size(); i++) {
//            Map<String, Object> maps = listInvented.get(i);
//            if (serviceid.equals(maps.get("servicecode"))) {// 满足条件
//                m1 = maps;
//                break;
//            }
//        }
//        Map m = new HashMap();
//        Map m2 = new HashMap();
//        List<Map<String, Object>> listCatalog = TheNewManyService.getListCatalogService();
//        for (int i = 0; i < listCatalog.size(); i++) {
//            Map<String, Object> maps = listCatalog.get(i);
//            if (serviceid.equals(maps.get("servicecode"))) {// 满足条件
//                m2 = maps;
//                break;
//            }
//        }
//        if (m1 != null && m1.size() > 0) {// 有虚拟资料集
//            if (m2 != null && m2.size() > 0) {// 也有普通资料集
//                m.putAll(m2);
//                m.put("dataCode3", m1.get("dataCode3"));
//                m.put("dataCode2", m2.get("dataCode") + "," + m1.get("dataCode2"));
//            } else {
//                m.putAll(m1);
//            }
//        } else {
//            m2.put("dataCode2", m2.get("dataCode"));
//            m.putAll(m2);
//            ;
//        }
//        String[] temp = m.get("dataCode2").toString().split(",");
//        List<Map<String, Object>> listCatalogItem = TheNewIUDService.getListCatalogItem();
//        for (int i = 0; i < listCatalogItem.size(); i++) {
//            Map<String, Object> maps = listCatalogItem.get(i);
//            for (int j = 0; j < temp.length; j++) {
//                if (temp[j].equals(maps.get("rescataen"))) {// 满足条件
//                    listType.add(maps);
//                }
//            }
//        }
//        too.setList(listType);// 设置字段到转换
//        return m;
//
//    }
//
//    public static String replace(String data) {
//        if (data != null && !"".equals(data)) {
//            data = data.replace(" ", "");
//        }
//        return data;
//    }
//}
