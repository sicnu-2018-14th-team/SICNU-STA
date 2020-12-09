package com.sicnu.sta.service.admin.impl;

import com.sicnu.sta.dao.ContestDao;
import com.sicnu.sta.dao.UserDao;
import com.sicnu.sta.entity.Contest;
import com.sicnu.sta.service.admin.AdminContestService;
import com.sicnu.sta.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AdminContestServiceImpl implements AdminContestService {

    @Resource
    ContestDao contestDao;

    @Resource
    UserDao userDao;


    /**
     * 创建比赛
     *
     * @param contest 比赛实体类
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> createContest(Contest contest) {
        try {
            contestDao.createContest(contest);
            Integer id = contest.getContestId();
            return ResultUtils.success(id);
        } catch (Exception e) {
            log.error("创建题目集失败", e);
            return ResultUtils.error();
        }
    }

    /**
     * 查询所有题目集列表的 id 和 title
     *
     * @param page 当前页
     * @param pageSize 页面大小
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> queryContestListInfo(int pageSize, int page) {
        try {
            List<Map<String, Object>> contests = contestDao.queryContestListInfo((page - 1) * pageSize, pageSize);
            for (Map<String, Object> it : contests) {
                String userName = userDao.queryUserNameByUserId(Math.toIntExact((Long) it.get("user_id")));
                it.put("user_name", userName);
            }
            List<Object> data = new ArrayList<>();
            Map<String, Integer> cnt = new HashMap<>();
            cnt.put("total", contestDao.queryContestCnt());
            data.add(cnt);
            data.add(contests);
            return ResultUtils.success(data);
        } catch (Exception e) {
            log.error("查询题目集列表失败", e);
            return ResultUtils.error();
        }
    }
}
