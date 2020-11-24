package com.sicnu.sta.service.impl;

import com.sicnu.sta.dao.ContestDao;
import com.sicnu.sta.entity.Contest;
import com.sicnu.sta.service.ContestService;
import com.sicnu.sta.utils.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContestServiceImpl implements ContestService {

    @Resource
    ContestDao contestDao;

    private ResponseUtil res;

    @Override
    public ResponseUtil createContest(Contest contest) {
        contestDao.createContest(contest);
        int id = contest.getContestId();
        if (id > 0) {
            res = new ResponseUtil(1, "success", id);
        } else {
            res = new ResponseUtil(0, "failure", "创建失败");
        }
        return res;
    }

    @Override
    public ResponseUtil queryContestListInfo(int pageSize, int page) {
        List<Map<String, Object>> contests = contestDao.queryContestListInfo((page - 1) * pageSize, pageSize);
        List<Object> data = new ArrayList<>();
        Map<String, Integer> cnt = new HashMap<>();
        cnt.put("total", contestDao.queryContestCnt());
        data.add(cnt);
        data.add(contests);
        res = new ResponseUtil(1, "success", data);
        return res;
    }
}
