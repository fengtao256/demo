package com.example.MyMaths;

import java.util.*;

public class FTMaths {


    public static void sortListMapByKey(List<Map<String , Object>> mapList , String key , String type){
        for (Map<String,Object> map : mapList) {
            System.out.println(map);
        }
        Collections.sort(mapList, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                if(type.equalsIgnoreCase("double"))
                    return CompareDouble(o1.get(key), (o2.get(key)));
                else
                    return (o1.get(key).toString()).compareTo(o2.get(key).toString());

            }
        });
        for (Map<String,Object> map : mapList) {
            map.put("key","2.001") ;
        }
        System.out.println("之后\n");
        for (Map<String,Object> map : mapList) {
            System.out.println(map);
        }
    }


    public static int CompareDouble(Object o1 ,Object o2){
        //相等0
        //大1
        //小-1
        Double num01 = Double.valueOf(o1.toString()) ;
        Double num02 = Double.valueOf(o2.toString()) ;
        if(num01 > num02) return 1 ;
        else if(num01 == num02) return 0 ;
        else if(num01 < num02) return -1 ;
        return 1 ;
    }

    public static void main(String[] args) {
        List<Map<String , Object>> newMapList = new ArrayList<Map<String , Object>>() ;
        for (int i = 0; i < 5; i++) {
            Map<String , Object> map = new HashMap<String , Object>() ;
            map.clear();
            map.put("key","20202"+i);
            map.put("rankRate",String.valueOf(new Random().nextDouble()*10));
            newMapList.add(map) ;
        }

        System.out.println(CompareDouble("1.0833333","1.08222"));
        sortListMapByKey(newMapList,"rankRate","String");
    }

}
