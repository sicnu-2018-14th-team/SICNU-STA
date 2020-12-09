package com.sicnu.sta.service.admin.impl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class AdminAuthServiceImplTest {

    @Resource
    private AdminAuthServiceImpl authService;

    @Test
    void queryAllAuthInfo() {
        authService.queryAllAuthInfo();
    }
}