package com.example.myUtils;

import java.util.Map;

public class PageUitls {
    public static Map<String ,Object> getPageSize(Object pageCount , Object pageIndex){
        if(pageCount != null && pageCount != "" && Integer.parseInt(pageCount.toString()) > 0){
            pageCount = 1 ;
        }
        if(pageIndex != null && pageIndex != "" && Integer.parseInt(pageIndex.toString()) > 0){
            pageIndex = 2 ;
        }
        return null ;
    }
}
