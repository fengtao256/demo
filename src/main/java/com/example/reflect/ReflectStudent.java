package com.example.reflect;

import java.lang.reflect.Method;

public class ReflectStudent {
    //反射实例
    public static void main(String[] args) throws Exception {
        //1、获取Class
        Student student = new Student() ;
        Class stuClass1 = student.getClass() ;
        //2、获取Class
        Class stuClass2 = Student.class ;
        //3、获取Class
        Class stuClass3 = Class.forName("com.example.reflect.Student");

        System.out.println("当前运行时类："+stuClass1.getName());
        //一个类只能产生一个Class对象，但是可以生成多个实例
        System.out.println("获取的是否是同一个Class：" + (stuClass1==stuClass2));
        System.out.println("获取的是否是同一个Class：" + (stuClass1==stuClass3));
        for (Method method :stuClass1.getDeclaredMethods()
             ) {
            //System.out.println(method);
            String string = method.toString()
                    .split("\\(")[method.toString().split("\\(").length - 2];
            String stringMethod = string
                    .split("\\.")[string.split("\\.").length - 1];
            System.out.println(stringMethod);
//            //从Class获取方法
//            Method method1 = stuClass1.getDeclaredMethod(stringMethod);
//            Object object = stuClass1.getConstructor().newInstance();
//            Object result = method1.invoke(object);
        }

    }
}
class Student {
    String name = "fengtao";
    String age = "20";
    String school = "30";
    public Student(){

    }
    public Student(String name ){
        System.out.println("name = "+ name );
        this.name = name ;
    }
    public String getName() {
        System.out.println("name = "+ name );
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}