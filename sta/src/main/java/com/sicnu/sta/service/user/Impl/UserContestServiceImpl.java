package com.sicnu.sta.service.user.Impl;

import com.sicnu.sta.dao.ContestDao;
import com.sicnu.sta.service.admin.impl.AdminContestProblemServiceImpl;
import com.sicnu.sta.service.user.UserContestService;
import com.sicnu.sta.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserContestServiceImpl implements UserContestService {


    @Resource
    AdminContestProblemServiceImpl adminContestProblemService;

    @Resource
    ContestDao contestDao;

    /**
     * 通过比赛 id 来查询比赛的信息
     *
     * @param contestId 比赛 id
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> queryContestInfoByContestId(int contestId) {
        try {
            Map<String, Object> map = contestDao.queryContestInfoByContestId(contestId);
            return ResultUtils.success(map);
        } catch (Exception e) {
            log.error("查询指定比赛信息失败", e);
            return ResultUtils.error();
        }
    }

    /**
     * 查询比赛下对应类型的题目
     *
     * @param contestId 比赛 id
     * @param typeId    类型 id
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> queryProblemList(int contestId, int typeId) {
        try {
            ResultUtils<Object> tmp = adminContestProblemService.queryProblemList(contestId, typeId);
           Object data = tmp.getData();
           Map<String, Object> map = contestDao.queryContestTime(contestId);
           List<Object> resData = new ArrayList<>();
           resData.add(map);
           resData.add(data);
           return ResultUtils.success(resData);
        } catch (Exception e) {
            log.error("查询比赛下对应类型的题目失败", e);
            return ResultUtils.error();
        }
    }
}
