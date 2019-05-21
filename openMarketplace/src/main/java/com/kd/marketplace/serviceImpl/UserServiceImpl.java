package com.kd.marketplace.serviceImpl;

import com.kd.marketplace.mapper.UserMapper;
import com.kd.marketplace.model.User;
import com.kd.marketplace.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("UserService")
public class UserServiceImpl implements UserService{
    @Resource
    private UserMapper userMapper;
    @Override
    public User findUser() {
        User user = userMapper.queryUser();
        return user;
    }
}
