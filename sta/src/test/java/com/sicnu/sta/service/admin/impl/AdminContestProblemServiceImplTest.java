package com.sicnu.sta.service.admin.impl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class AdminContestProblemServiceImplTest {

    @Resource
    AdminContestProblemServiceImpl adminContestProblemService;

    @Test
    void queryProblemListPaging() {
        adminContestProblemService.queryProblemListPaging(15, 3, 1, 3);
    }
}