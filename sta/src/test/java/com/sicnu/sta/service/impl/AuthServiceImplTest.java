package com.sicnu.sta.service.impl;

import com.sicnu.sta.entity.Auth;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class AuthServiceImplTest {

    @Resource
    private AuthServiceImpl authService;

    @Test
    void queryAllAuthInfo() {
        authService.queryAllAuthInfo();
    }
}