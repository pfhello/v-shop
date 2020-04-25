package cn.itcast.shop.util;

import java.util.UUID;

public class UUIDUtil {

    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","")
                .substring(0,32);
    }
}
