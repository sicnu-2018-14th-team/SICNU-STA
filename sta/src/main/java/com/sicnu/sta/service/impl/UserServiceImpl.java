package com.sicnu.sta.service.impl;

import com.sicnu.sta.dao.UserDao;
import com.sicnu.sta.entity.User;
import com.sicnu.sta.service.UserService;
import com.sicnu.sta.utils.ResponseUtil;
import com.sicnu.sta.utils.TokenUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private TokenUtil tokenUtil;

    private ResponseUtil res;

    @Override
    public ResponseUtil judgeUserLogin(String email, String password) {

        User user = userDao.judgeUserLogin(email, password);
        if (user == null) {
            res = new ResponseUtil<String>(0, "failure", "邮箱和密码不匹配，请重新尝试！");
        } else {
            String token = tokenUtil.createToken(user);
            res = new ResponseUtil<String>(1, "success", token);
        }
        return res;
    }

    @Override
    public ResponseUtil insertNewUser(User user) {

        User user1 = userDao.findUserByEmail(user.getEmail());
        if(user1 != null) {
            System.out.println(user);
            res = new ResponseUtil<String>(0, "failure", "该邮箱已经被注册！");
            return res;
        }
        userDao.insertNewUser(user);
        int userId = user.getUserId();
        if(userId > 0) {
            res = new ResponseUtil(1, "success");
        } else {
            res = new ResponseUtil(0, "failure");
        }
        return res;
    }


    @Override
    public ResponseUtil updateUserPassword(String email, String password) {
        User user = userDao.findUserByEmail(email);
        if(user == null) {
            res = new ResponseUtil<String>(0, "failure", "该邮箱尚未被注册！");
            return res;
        }
        int flag = userDao.updateUserPassword(email, password);
        if(flag == 1) {
            res = new ResponseUtil(1, "success");
        } else {
            res = new ResponseUtil(0, "failure");
        }
        return res;
    }

}
