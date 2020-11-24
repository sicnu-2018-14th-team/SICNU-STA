package com.sicnu.sta.controller;

import com.sicnu.sta.entity.Contest;
import com.sicnu.sta.service.impl.ContestServiceImpl;
import com.sicnu.sta.utils.ResponseUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ContestController {

    @Resource
    private ContestServiceImpl contestService;

    @PostMapping("/create/contest")
    ResponseUtil createContest(@RequestBody Contest contest) {
        return contestService.createContest(contest);
    }

    /**
     * 查询所有题目集列表信息
     * @return
     */
    @PostMapping("/query/contest/list")
    ResponseUtil queryContestListInfo(@Param(value = "pageSize") int pageSize, @Param(value = "page") int page) {
        return contestService.queryContestListInfo(pageSize, page);
    }
}
