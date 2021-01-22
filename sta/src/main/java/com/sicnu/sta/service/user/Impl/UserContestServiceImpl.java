package com.sicnu.sta.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sicnu.sta.dao.ContestDao;
import com.sicnu.sta.dao.ContestProblemDao;
import com.sicnu.sta.dao.ProblemDao;
import com.sicnu.sta.dao.UserDao;
import com.sicnu.sta.service.admin.impl.AdminContestProblemServiceImpl;
import com.sicnu.sta.service.user.UserContestService;
import com.sicnu.sta.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service
public class UserContestServiceImpl implements UserContestService {


    @Resource
    AdminContestProblemServiceImpl adminContestProblemService;

    @Resource
    ContestDao contestDao;

    @Resource
    ProblemDao problemDao;

    @Resource
    UserDao userDao;

    @Resource
    ContestProblemDao contestProblemDao;

    /**
     * 通过比赛 id 来查询比赛的信息
     *
     * @param contestId 比赛 id
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> queryContestInfoByContestId(int contestId, Integer userId) {
        try {
            Map<String, Object> map = contestDao.queryContestInfoByContestId(contestId);
            Integer score = contestDao.queryContestUserSumScore(contestId, userId);
            if (score == null) score = 0;
            map.put("score", score);
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

    /**
     * 查询公开的题目集
     *
     * @return 公开题目集信息
     */
    @Override
    public ResultUtils<Object> queryOpenContest(Integer page, Integer pageSize, Integer userId) {
        try {
            Map<String, Object> data = new HashMap<>();
            int total = contestDao.queryContestCnt();
            List<Map<String, Object>> mapList = contestDao.queryOpenContest((page - 1) * pageSize, pageSize);
            for (Map<String, Object> it : mapList) {
                Integer contestId = Math.toIntExact((Long)it.get("contest_id"));
                Integer score = contestDao.queryContestUserSumScore(contestId, userId);
                if (score == null) score = 0;
                it.put("score", score);
            }
            data.put("data", mapList);
            data.put("total", total);
            return ResultUtils.success(data);
        } catch (Exception e) {
            log.error("查询公开题目集信息失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 查询我的题目集信息
     *
     * @param userId 用户 id
     * @return 我的题目集的信息
     */
    @Override
    public ResultUtils<Object> queryMyContest(Integer userId, Integer page, Integer pageSize) {
        try {
            Map<String, Object> data = new HashMap<>();
            int total = contestDao.queryMyContestCnt(userId);
            List<Map<String, Object>> mapList = contestDao.queryMyContest(userId, (page - 1) * pageSize, pageSize);
            for (Map<String, Object> it : mapList) {
                Integer contestId = Math.toIntExact((Long)it.get("contest_id"));
                Integer score = contestDao.queryContestUserSumScore(contestId, userId);
                it.put("score", score);
             }
            data.put("total", total);
            data.put("data", mapList);
            return ResultUtils.success(data);
        } catch (Exception e) {
            log.error("查询我的题目集信息失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 查看我的提交列表
     * @param contestId 比赛 id
     * @param userId 用户 id
     * @param page     页码
     * @param pageSize 大小
     * @return 提交信息
     */
    @Override
    public ResultUtils<Object> queryMySubmit(Integer contestId, Integer userId, Integer page, Integer pageSize) {

        try {
            List<Map<String, Object>> submitList = contestDao.queryContestMySubmit(contestId, userId, (page - 1) * pageSize, pageSize);
            List<Map<String, Object>> resData = new ArrayList<>();
            for (Map<String, Object> map : submitList) {
                Integer problemId = Math.toIntExact((Long) map.get("problem_id"));
                String title = problemDao.queryProgramTitleByProblemId(problemId);
                map.put("title", title);
                map.put("msg", getMsg((String) map.get("result")));
                Integer type = (Integer) map.get("type");
                map.put("compiler", getCompiler(type));
                map.put("user_name",  userDao.queryUserNameByUserId(userId));
                resData.add(map);
            }
            int total = contestDao.queryContestMySubmitCnt(contestId, userId);
            Map<String, Object> data = new HashMap<>();
            data.put("data", resData);
            data.put("total", total);
            return ResultUtils.success(data);
        } catch (Exception e) {
            log.error("查询我的提交列表信息失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 查询所有提交列表
     *
     * @param contestId 比赛 id
     * @param page      页码
     * @param pageSize  页大小
     * @return 提交信息
     */
    @Override
    public ResultUtils<Object> queryAllSubmit(Integer contestId, Integer page, Integer pageSize) {
        try {
            List<Map<String, Object>> submitList = contestDao.queryContestAllSubmit(contestId, (page - 1) * pageSize, pageSize);
            List<Map<String, Object>> data = new ArrayList<>();
            for (Map<String, Object> map : submitList) {
                Integer problemId = Math.toIntExact((Long) map.get("problem_id"));
                String title = problemDao.queryProgramTitleByProblemId(problemId);
                map.put("title", title);
                if (map.get("result") == null) continue;
                map.put("msg", getMsg((String) map.get("result")));
                Integer type = (Integer) map.get("type");
                map.put("compiler", getCompiler(type));
                Integer userId = Math.toIntExact((Long) map.get("user_id"));
                map.put("user_name", userDao.queryUserNameByUserId(userId));
                data.add(map);
            }
            Map<String, Object> resData = new HashMap<>();
            resData.put("data", data);
            int total = contestDao.queryContestAllSubmitCnt(contestId);
            resData.put("total", total);
            return ResultUtils.success(resData);
        } catch (Exception e) {
            log.error("查询所有的提交信息失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 查看排名列表
     *
     * @param contestId contestId
     * @param page      页码
     * @param pageSize  页面大小
     * @return 排名列表
     */
    @Override
    public ResultUtils<Object> queryContestRank(Integer contestId, Integer page, Integer pageSize, Integer userId) {
        try {
            // 查看当前比赛是否可以查看排名
            Integer flag = contestDao.queryContestIsRealRank(contestId);
            int total = 0;
            List<Integer> userIds;
            // 为 1 支持看榜
            if (flag == 1) {
                userIds = contestDao.queryContestUser(contestId, (page - 1) * pageSize, pageSize);
                total = contestDao.queryContestUserCnt(contestId).size();
            } else {
                userIds = new ArrayList<>();
                userIds.add(userId);
                total = 1;
            }
            List<Map<String, Integer>> mapList = new ArrayList<>();
            List<Map<String, Object>> data = new ArrayList<>();
            for (Integer uid : userIds) {
                int sumScore = contestDao.queryContestUserSumScore(contestId, uid);
                Map<String, Integer> tmp = new HashMap<>();
                tmp.put("userId", uid);
                tmp.put("score", sumScore);
                mapList.add(tmp);
                // 把自己放在最前面
                if (uid.equals(userId) && flag == 1) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("score", sumScore);
                    map.put("userName", userDao.queryUserNameByUserId(userId));
                    data.add(map);
                }
            }
            mapList.sort((o1, o2) -> {
                Integer a = o1.get("score");
                Integer b = o2.get("score");
                return b.compareTo(a);
            });
            int rank = 0;
            int lastScore = -1;
            int myRank = 0;
            for (Map<String, Integer> it : mapList) {
                Map<String, Object> map = new HashMap<>();
                int curScore = it.get("score");
                if (curScore != lastScore) rank += 1;
                map.put("rank", rank);
                map.put("score", curScore);
                lastScore = curScore;
                map.put("userName", userDao.queryUserNameByUserId(it.get("userId")));
                if (flag == 1 && it.get("userId").equals(userId)) myRank = rank;
                data.add(map);
            }
            if (flag == 1) {
                for (Map<String, Object> it : data) {
                    it.put("rank", myRank);
                    break;
                }
            }
            Map<String, Object> resData = new HashMap<>();
            resData.put("total", total);
            resData.put("data", data);
            return ResultUtils.success(resData);
        } catch (Exception e) {
            log.error("查询排名列表失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    // 根据编程题返回结果获取信息
    public String getMsg(String result) {

        String AC = "答案正确";
        String PC = "部分正确";
        String WA = "答案错误";
        String CE = "编译错误";
        String RE = "运行错误";
        String PE = "格式错误";
        String TLE = "运行超时";
        String MLE = "空间超限";

        JSONObject map = JSON.parseObject(result);
        if (map.get("globalMsg") != null) return CE;
        Object mapList =  map.get("result");
        int ac = 0, wa = 0, re = 0, pe = 0, tle = 0, mle = 0;
        List<?> obj = (List<?>) mapList;
        for (Object it : obj) {
            Map<?, ?> map1 = (Map<?, ?>) it;
            Integer status = (Integer) map1.get("status");
            if (status == 0) ac = 1;
            else if (status == 1) pe = 1;
            else if (status == 2) tle = 1;
            else if (status == 3) mle = 1;
            else if (status == 4) wa = 1;
            else if (status == 5) re = 1;
        }
        if (wa == 1 && ac == 1) return PC;
        else if (wa == 1) return WA;
        else if (re == 1) return RE;
        else if (mle == 1) return MLE;
        else if (tle == 1) return TLE;
        else if (pe == 1) return PE;
        else return AC;
    }

    // 根据提交的编译类型返回对应的编译器
    public String getCompiler(Integer type) {
        Map<Integer, String> compiler = new HashMap<>();
        compiler.put(1, "C");
        compiler.put(2, "C");
        compiler.put(3, "C");
        compiler.put(4, "C++");
        compiler.put(5, "C++");
        compiler.put(6, "C++");
        compiler.put(7, "C++");
        compiler.put(8, "Java");
        compiler.put(9, "Python2");
        compiler.put(10, "Python3");
        return compiler.get(type);
    }

}
