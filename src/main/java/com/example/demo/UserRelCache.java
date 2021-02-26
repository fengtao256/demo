package com.example.demo;

import java.util.*;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName UserRelCache.java
 * @Description TODO
 * @createTime 2021年02月24日 14:17:00
 */
public class UserRelCache implements UserRelOperation {
    //采取二维数组存储数据
    private ArrayList<List<Integer>> concernList;
    private ArrayList<List<Integer>> fansList;

    //构造方法
    public UserRelCache(int capacity) {
        concernList = new ArrayList<>();
        concernList.ensureCapacity(capacity);
        for (int i = 0; i < capacity; i++) {
            concernList.add(new ArrayList<>());
        }
        fansList = new ArrayList<>();
        fansList.ensureCapacity(capacity);
        for (int i = 0; i < capacity; i++) {
            fansList.add(new ArrayList<>());
        }
    }

    //添加关注
    public void addConcern(int userId, int concernId) {
        if (userId == concernId || concernList.get(userId).contains(concernId)) {
            return;
        }
        concernList.get(userId).add(concernId);
        fansList.get(concernId).add(userId);
    }

    //取消关注
    public void removeConcern(int userId, int concernId) {
        if (!concernList.get(userId).contains(concernId)) {
            return;
        }
        concernList.get(userId).remove((Object) concernId);
        fansList.get(concernId).remove((Object) userId);
    }

    //获取关注列表
    public List<Integer> getConcernList(int userId) {
        return concernList.get(userId);
    }

    //获取粉丝列表
    public List<Integer> getFansList(int userId) {
        return fansList.get(userId);
    }
}
