//package com.wangge.buzmgt.monthTarget.web;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
///**
// * Created by joe on 16-7-5.
// */
//public class WTF {
//    public static void main(String[] args) {.
//        zqsx zq = new zqsx();
//        zq.setAge(25);
//        zq.setName("zhangqing1");
//
//        zqsx zqs = new zqsx();
//        zqs.setAge(26);
//        zqs.setName("zhangqing2");
//
//        zqsx zqsx = new zqsx();
//        zqsx.setAge(21);
//        zqsx.setName("fuck");
//
//        List<zqsx> l = new ArrayList<>();
//        l.add(zq);
//        l.add(zqs);
//        l.add(zqsx);
//
//        l.forEach(x -> {
//            System.out.println(x.getName());
//        });
//
//        Collections.sort(l);
//        System.out.println("-===========");
//
//        l.forEach(x->{
//            System.out.println(x.getName());
//        });
//
//    }
//}
//
//
//class zqsx implements Comparable<zqsx> {
//    private String name;
//    private Integer age;
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
//
//    @Override
//    public int compareTo(zqsx o) {
//        return this.age - o.age;
//    }
//}