package com.sicnu.sta.service.user.Impl;

import com.sicnu.sta.dao.UserDao;
import com.sicnu.sta.entity.LoginLog;
import com.sicnu.sta.entity.User;
import com.sicnu.sta.service.user.UserService;
import com.sicnu.sta.utils.HttpContextUtils;
import com.sicnu.sta.utils.IpUtils;
import com.sicnu.sta.utils.ResultUtils;
import com.sicnu.sta.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserDao userDao;


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
                String token = TokenUtils.createToken(user);
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
                return ResultUtils.success();
            } else {
                return ResultUtils.fail();
            }
        } catch (Exception e) {
            logger.error("更新用户密码异常：", e);
            return ResultUtils.error();
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
}
