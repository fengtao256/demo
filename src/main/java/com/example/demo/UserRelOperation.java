package com.example.demo;

import java.util.List ;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName UserRelOperation.java
 * @Description TODO
 * @createTime 2021年02月25日 16:52:00
 */
public interface UserRelOperation {
    /**
     * 添加关注
     */
    void addConcern(int userId , int concernId) ;
    /**
     * 取消关注
     */
    void  removeConcern(int userId , int concernId) ;
    /**
     * 获取粉丝列表
     * @return
     */
    List<Integer> getFansList(int userId) ;

    /**
     * 获取关注列表
     * @return
     */
    List<Integer> getConcernList(int userId) ;

}

