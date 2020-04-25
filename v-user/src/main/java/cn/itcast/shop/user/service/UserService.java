package cn.itcast.shop.user.service;

import cn.itcast.shop.pojo.User;

public interface UserService {
    User getUserByUsername(String username);

    User getUserByEmail(String email);

    int deleteUserByEmail(String email);

    int enable(String email);

    int addUser(User user);
}
