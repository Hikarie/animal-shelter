package com.animal.user;

public class Id {
    public static String getId(String prefix){
        long mills = System.currentTimeMillis();
        String id = mills + "";
        id = id.substring(id.length()-6);  // 截取后六位
        id = prefix + id;
        return id;
    }

//    public static void main(String[]args){
//        System.out.println(getId("test"));
//    }
}
