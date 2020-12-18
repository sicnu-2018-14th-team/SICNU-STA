package com.sicnu.sta.service.admin.impl;

import com.sicnu.sta.dao.ProblemDao;
import com.sicnu.sta.dao.TagDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class AdminAuthServiceImplTest {

    @Resource
    private AdminAuthServiceImpl authService;

    @Resource
    private ProblemDao problemDao;

    @Resource
    private TagDao tagDao;

    @Test
    void queryAllAuthInfo() {
        List<Integer> problemIds = problemDao.queryAllProblemId();
        for (Integer it : problemIds) {
            // 获取还未更新前的标签字符串
            String curTag = problemDao.queryTagsByProblemId(it);
            for (int i = 0; i < curTag.length(); i ++) {
                if (curTag.charAt(i) == '1') {
                    String tmpPro = tagDao.queryProblemByTag(i + 1);
                    StringBuilder stringBuilder = new StringBuilder(tmpPro);
                    stringBuilder.setCharAt(it - 1, '1');
                    tagDao.updateTagProblems(i + 1, stringBuilder.toString());
                }
            }
        }

    }
}