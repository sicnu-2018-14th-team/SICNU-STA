package com.sicnu.pta.service.impl;

import com.sicnu.pta.dao.UserDao;
import com.sicnu.pta.entity.po.User;
import com.sicnu.pta.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public User judgeUserLogin(String email, String password) {
        return userDao.judgeUserLogin(email, password);
    }

    @Override
    public int insertNewUser(User user) {
        return userDao.insertNewUser(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public int updateUserPassword(String email, String password) {
        return userDao.updateUserPassword(email, password);
    }
}
