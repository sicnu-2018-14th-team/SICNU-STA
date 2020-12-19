package com.sicnu.sta.service.user.Impl;

import com.sicnu.sta.dao.*;
import com.sicnu.sta.entity.*;
import com.sicnu.sta.service.user.UserService;
import com.sicnu.sta.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserDao userDao;

    @Resource
    private AuthDao authDao;

    @Resource
    private ContestDao contestDao;

    @Resource
    private AnswerDao answerDao;

    @Resource
    private ContestProblemDao contestProblemDao;

    @Resource
    private UserJudgeServiceImpl userJudgeService;

    @Resource
    RedisUtils redisUtils;


    /**
     * 判断用户登录
     *
     * @param email 用户登录邮箱
     * @param password 用户登录密码
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> judgeUserLogin(String email, String password) {

        try {
            User user = userDao.judgeUserLogin(email, password);
            LoginLog loginLog;
            HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
            String ip = IpUtils.getIpAddr(request);
            if (user == null) {
                loginLog = LoginLog.fail(ip, getDateTime());
                userDao.insertLoginLog(loginLog);
                return ResultUtils.fail("邮箱和密码不匹配，请重新尝试！");
            } else {
                loginLog = LoginLog.success(ip, user, getDateTime());
                userDao.insertLoginLog(loginLog);
                long current = System.currentTimeMillis();
                LoginUser loginUser = new LoginUser();
                loginUser.setUserName(user.getUserName());
                loginUser.setUserId(user.getUserId());
                loginUser.setRole(userDao.queryRoleNameByUserId(user.getUserId()));
                String token = TokenUtils.createToken(loginUser, current);
                redisUtils.set(user.getUserId().toString(), current, 30 * 60 * 8);
                return ResultUtils.success(token);
            }
        } catch (Exception e) {
            logger.error("用户登录异常：", e);
            return ResultUtils.error();
        }
    }

    /**
     * 新注册的用户
     *
     * @param user 用户
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> insertNewUser(User user) {

        try {
            User user1 = userDao.findUserByEmail(user.getEmail());
            if(user1 != null) {
                return ResultUtils.fail("该邮箱已经被注册!");
            }
            User user2 = userDao.queryUserByUserName(user.getUserName());
            if (user2 != null) {
                return ResultUtils.fail("该用户名已被注册!");
            }
            userDao.insertNewUser(user);
            int userId = user.getUserId();
            if(userId > 0) {
                // 给刚注册的用户分配一个默认角色学生
                userDao.addStudentToNewUser(userId, 4);
                return ResultUtils.success();
            } else {
                return ResultUtils.fail();
            }
        } catch (Exception e) {
            logger.error("注册用户异常：", e);
            return ResultUtils.error();
        }
    }


    /**
     * 更新用户密码
     *
     * @param email 用户邮箱
     * @param password 用户密码
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> updateUserPassword(String email, String password) {

        try {
            User user = userDao.findUserByEmail(email);
            if(user == null) {
                return ResultUtils.fail("该邮箱尚未被注册！");
            }
            int flag = userDao.updateUserPassword(email, password);
            if(flag == 1) {
                redisUtils.del(user.getUserId().toString());
                return ResultUtils.success();
            } else {
                return ResultUtils.fail("更改密码失败！");
            }
        } catch (Exception e) {
            logger.error("更新用户密码异常：", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 模糊查找用户名
     *
     * @param userName 用户名
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> vagueQueryUserName(String userName) {
        try {
            List<Map<String, Object>> maps = userDao.vagueQueryUserName(userName);
            return ResultUtils.success(maps);
        } catch (Exception e) {
            logger.error("模糊查找用户名异常：", e);
            return ResultUtils.error();
        }
    }

    /**
     * 获取标准时间
     *
     * @return String
     */
    @Override
    public String getDateTime() {
        try {
            //中国科学院国家授时中心
            String webUrl = "http://www.ntsc.ac.cn";
            URL url = new URL(webUrl);
            // 生成连接对象
            URLConnection uc = url.openConnection();
            // 发出连接
            uc.connect();
            // 读取网站日期时间
            long ld = uc.getDate();
            // 转换为标准时间对象
            Date date = new Date(ld);
            // 输出北京时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            return sdf.format(date);
        } catch (Exception e) {
            logger.error("获取时间失败", e);
            return null;
        }
    }

    /**
     * 退出登录
     *
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> logout(Integer userId) {
        try {
            redisUtils.del(userId.toString());
            return ResultUtils.success();
        } catch (Exception e) {
            logger.error("退出登录失败", e);
            return ResultUtils.error();
        }
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户 id
     * @return result
     */
    @Override
    public ResultUtils<Object> queryUserInfo(Integer userId) {
        try {
            Map<String, Object> resData = userDao.queryUserInfo(userId);
            Role role = authDao.queryUserRoleByUserId(userId);
            List<Auth> auths = authDao.queryRoleAuthByRoleId(role.getRoleId());
            if (auths.size() == 0) {
                resData.put("manage", 0);
            } else {
                resData.put("manage", 1);
            }
            resData.putIfAbsent("sex", "");
            resData.putIfAbsent("birthday","");
            resData.putIfAbsent("real_name", "");
            resData.putIfAbsent("address", "");
            resData.putIfAbsent("phone", "");
            resData.putIfAbsent("post_code", "");
            return ResultUtils.success(resData);
        } catch (Exception e) {
            logger.error("获取用户信息失败", e);
            return ResultUtils.error();
        }
    }

    /**
     * 查询班级列表
     *
     * @param code 邀请码
     * @return result
     */
    @Override
    public ResultUtils<Object> queryClassListByCode(String code) {
        try {
            List<Map<String, Object>> mapList = userDao.queryClassListByCode(code);
            return ResultUtils.success(mapList);
        } catch (Exception e) {
            logger.error("查询班级列表失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 学生加入班级
     *
     * @param classId   班级 id
     * @param studentId 学生 id
     * @return result
     */
    @Override
    public ResultUtils<Object> studentJoinTheClass(Integer classId, Integer studentId) {
        try {
            userDao.studentJoinTheClass(classId, studentId);
            List<Integer> contestIds = contestDao.queryClassContestIds(classId);
            for (Integer contestId : contestIds)
                userJudgeService.queryContestIsMyContest(studentId, contestId);
            return ResultUtils.success();
        } catch (Exception e) {
            logger.error("学生加入班级失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 更新用户信息
     *
     * @param sex      性别
     * @param birthday 生日
     * @param realName 真实姓名
     * @param address  地址
     * @param phone    手机号
     * @param postCode 邮编
     * @param userId   用户 id
     * @return result
     */
    @Override
    public ResultUtils<Object> updateUserInfo(String sex, String birthday, String realName, String address, String phone, String postCode, Integer userId) {
        try {
            userDao.updateUserInfo(sex, birthday, realName, address, phone, postCode, userId);
            return ResultUtils.success();
        } catch (Exception e) {
            logger.error("更新用户信息失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户 id
     * @return result
     */
    @Override
    public ResultUtils<Object> getClassInfo(Integer userId) {
        try {
            List<Map<String, Object>> map = userDao.getClassInfo(userId);
            return ResultUtils.success(map);
        } catch (Exception e) {
            logger.error("获取用户信息", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 退出班级
     *
     * @param classId 班级 id
     * @param userId  用户 id
     * @return result
     */
    @Override
    public ResultUtils<Object> exitClass(Integer classId, Integer userId) {
        try {
            userDao.exitClass(classId, userId);
            return ResultUtils.success();
        } catch (Exception e) {
            logger.error("退出班级失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 查询系统人力资源和题目、比赛资源的数量
     *
     * @return 数量
     */
    @Override
    public ResultUtils<Object> queryResourceCnt() {
        try {
            List<Object> data  = new ArrayList<>();
            List<Integer> data1 = new ArrayList<>();
            List<Integer> data2 = new ArrayList<>();
            Integer teacherCnt = userDao.queryTeacherCnt();
            Integer studentCnt = userDao.queryStudentCnt();
            Integer problemCnt = userDao.queryProblemCnt();
            Integer classCnt = userDao.queryClassCnt();
            data1.add(teacherCnt);
            data1.add(problemCnt);
            data2.add(classCnt);
            data2.add(studentCnt);
            data.add(data1);
            data.add(data2);
            return ResultUtils.success(data);
        } catch (Exception e) {
            logger.error("查询系统资源数量失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }
}
