package com.sicnu.sta.service.user.impl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class UserContestServiceImplTest {

    @Resource
    UserContestServiceImpl userContestService;
    @Test
    void getMsg() {
        String str = "{\"globalMsg\":\"Cannot run program \\\"g++\\\": error=2, No such file or directory\",\"submitId\":35}";

        System.out.println(userContestService.getMsg(str));
    }
}