package com.kd.nvoap.service.impl;

import javax.annotation.Resource;  
import org.springframework.stereotype.Service;

import com.kd.nvoap.dao.IUserDao;
import com.kd.nvoap.model.User;
import com.kd.nvoap.service.IUserService;  

  
@Service("userService")  
public class UserServiceImpl implements IUserService {  
    @Resource  
    private IUserDao userDao;  
    
    public User getUserById(int userId) {  
        // TODO Auto-generated method stub  
        return this.userDao.selectByPrimaryKey(userId);  
    }  
  
}  
