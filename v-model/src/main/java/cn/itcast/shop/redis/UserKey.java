package cn.itcast.shop.redis;

public interface UserKey {

    String USER_TOKEN="user_token";

    //单位小时
    Long USER_EXPIRE=24L;

    String USER_CART="user_cart";

    //单位天
    Long USER_CART_EXPIRE=30L;
}
