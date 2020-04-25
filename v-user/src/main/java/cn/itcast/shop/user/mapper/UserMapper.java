package cn.itcast.shop.user.mapper;

import cn.itcast.shop.pojo.User;

import java.util.List;

public interface UserMapper {

    List<User> getUserByUsername(String username);

    List<User> getUserByEmail(String email);

    int deleteUserByEmail(String email);

    int enableByEmail(String email);

    int addUser(User user);
}
