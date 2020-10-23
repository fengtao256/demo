package com.example.MyMaths;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Q1Q3Math {
    //求四分位数
    public static void fourDivsion(List<Integer> param){
        if(param == null || param.size() < 1) return;
        // 转成BigDecimal类型，避免失去精度
        BigDecimal[] datas = new BigDecimal[param.size()];
        for(int i=0; i<param.size(); i++){
            datas[i] = BigDecimal.valueOf(param.get(i));
        }
        int len = datas.length;// 数组长度
        Arrays.sort(datas);    // 数组排序，从小到大
        BigDecimal q1 = null;  // 第一四分位
        BigDecimal q2 = null;  // 第二四分位
        BigDecimal q3 = null;  // 第三四分位
        int index = 0; // 记录下标
        // n代表项数，因为下标是从0开始所以这里理解为：len = n+1
        if(len%2 == 0){ // 偶数
            index = new BigDecimal(len).divide(new BigDecimal("4")).intValue();
            q1 = datas[index-1].multiply(new BigDecimal("0.25")).add(datas[index].multiply(new BigDecimal("0.75")));
            q2 = datas[len/2].add(datas[len/2-1]).divide(new BigDecimal("2"));
            index = new BigDecimal(3*(len+1)).divide(new BigDecimal("4")).intValue();
            q3 = datas[index-1].multiply(new BigDecimal("0.75")).add(datas[index].multiply(new BigDecimal("0.25")));
        }else{ // 奇数
            q1 = datas[new BigDecimal(len).multiply(new BigDecimal("0.25")).intValue()];
            q2 = datas[new BigDecimal(len).multiply(new BigDecimal("0.5")).intValue()];
            q3 = datas[new BigDecimal(len).multiply(new BigDecimal("0.75")).intValue()];
        }
        // 保留两位小数（四舍五入），输出到控制台
        System.out.println(q1.setScale(2, BigDecimal.ROUND_HALF_UP)+" "+
                q2.setScale(2, BigDecimal.ROUND_HALF_UP)+" "+
                q3.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    public static void main(String[] args) {
        double[] temp = new double[]{6.0, 47.0, 49.0, 15.0, 42.0, 41.0, 7.0, 39.0, 43.0, 40.0, 36.0};
        List<Integer> temp1 = new ArrayList<>();
        temp1.add(1) ;
        temp1.add(1) ;
        temp1.add(49) ;
        temp1.add(15) ;
        temp1.add(42) ;
        temp1.add(41) ;
        temp1.add(7) ;
        temp1.add(39) ;
        temp1.add(43) ;
        temp1.add(40) ;
        temp1.add(36) ;
        Q1Q3Math.fourDivsion(temp1);
    }
}
