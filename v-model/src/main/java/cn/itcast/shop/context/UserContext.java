package cn.itcast.shop.context;

import cn.itcast.shop.pojo.User;

public class UserContext {

    private static final ThreadLocal<User> tl=new ThreadLocal<>();

    public static User getUser(){
        return tl.get();
    }

    public static void setUser(User user){
        tl.set(user);
    }

    public static void removeUser(){
        tl.remove();
    }
}
