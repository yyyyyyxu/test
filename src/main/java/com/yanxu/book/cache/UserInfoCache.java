package com.yanxu.book.cache;

import lombok.Data;
import sun.nio.cs.ext.MacArabic;

import java.util.HashMap;
import java.util.Map;
@Data
public class UserInfoCache {

    public static Map<String,String> map=new HashMap<>();

    public static void setMap(String s){
        map.put("name",s);
    }
}
