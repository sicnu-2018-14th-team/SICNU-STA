package com.sicnu.sta.dao;

import com.sicnu.sta.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class UserDaoTest {

    @Resource
    UserDao userDao;

    @Test
    void testUserLogin() {

        List<User> users = userDao.sqltest1("1 OR 1=1");
        for (User user : users) {
            System.out.println(user);
        }

    }
}