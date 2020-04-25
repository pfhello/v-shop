package cn.itcast.shop.user.service.impl;

import cn.itcast.shop.pojo.User;
import cn.itcast.shop.user.mapper.UserMapper;
import cn.itcast.shop.user.service.MailService;
import cn.itcast.shop.user.service.UserService;
import cn.itcast.shop.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailService mailService;

    @Override
    public User getUserByUsername(String username) {
        List<User> userList = userMapper.getUserByUsername(username);
        if (userList!=null&&userList.size()>0){
            return userList.get(0);
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        List<User> userList = userMapper.getUserByEmail(email);
        if (userList!=null&&userList.size()>0){
            return userList.get(0);
        }
        return null;
    }

    @Transactional
    @Override
    public int deleteUserByEmail(String email) {
        return userMapper.deleteUserByEmail(email);
    }

    @Transactional
    @Override
    public int enable(String email) {
        return userMapper.enableByEmail(email);
    }

    @Transactional
    @Override
    public int addUser(User user) {
        user.setUid(UUIDUtil.getUUID());
        int row = userMapper.addUser(user);
        mailService.sendRegister(user.getEmail());
        return row;
    }
}
