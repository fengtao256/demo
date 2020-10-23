package com.example.FileUtils;

import java.util.List;

public class NodeUtil {
    String title ;
    String key ;
    List<NodeUtil> children ;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<NodeUtil> getChildren() {
        return children;
    }

    public void setChildren(List<NodeUtil> children) {
        this.children = children;
    }


}
