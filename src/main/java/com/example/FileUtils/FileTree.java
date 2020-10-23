package com.example.FileUtils;

import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileTree {
    //递归获取文件目录信息
    public static void genDirTree(String path, Integer level , String dir, NodeUtil tree) {
        level++ ;
        File file = new File(path);
        File[] files = file.listFiles();
        if (!file.exists()) {
            System.out.println("文件不存在");
            return;
        }
        if (files.length != 0) {
            int i = 0 ; //同级目录下第几个节点信息
            List<NodeUtil> nodes = new ArrayList<NodeUtil>() ;
            for (File f : files) {
                //当前节点信息
                NodeUtil node = new NodeUtil() ;
                node.setTitle(f.getName());
                node.setKey(f.getName());
                node.setChildren(new ArrayList<NodeUtil>() );
                //添加同级节点信息
                nodes.add(node) ;
                tree.setChildren(nodes);
                if (f.isDirectory()) { //是文件，则继续往下递归
                    dir = f.getName();
                    genDirTree(f.getAbsolutePath(), level, dir ,tree.getChildren().get(i));
                }
                i ++ ;
            }
        }
    }
    //文件层级信息
    private static String levelSign(int level) {
        StringBuilder sb = new StringBuilder();
        sb.append(" |--");
        for (int x = 0; x < level; x++) {
            sb.insert(0, " |   ");
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        NodeUtil tree = new NodeUtil() ;
        genDirTree("F:\\HK-workspace\\JBOSS\\JBoss4.2-hkks\\server\\default\\deploy\\HKKnowledgeStudio" +
                ".war\\TaskFile\\QuickLabels\\02144924bf474e7b9d3578fcae172278\\source",0,"" , tree )  ;
        System.out.println( JSONObject.toJSONString(tree.getChildren()).substring(1,JSONObject.toJSONString(tree.getChildren()).length()-1) );
    }
//    public static void  prinlTree(List<NodeUtil> tree){
//
//        if(tree.getChildren() != null){
//            System.out.println("这是父级信息"+tree.getTitle());
//            prinlTree(tree.getChildren()) ;
//        }else{
//            System.out.println("这是子级信息"+tree.getTitle());
//        }
//    }
}
