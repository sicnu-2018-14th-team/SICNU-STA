package com.sicnu.sta.dao;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import javax.annotation.Resource;
import java.util.Map;

@SpringBootTest
class UserDaoTest {

    @Resource
    UserDao userDao;

    @Test
    void testUserLogin() {

        Map<String, Object> map = userDao.queryUserInfo(1);
        if (map.get("phone") == null) {
            System.out.println(1);
        }
    }
}