package com.sicnu.sta.data.controller;

import com.sicnu.sta.commons.entity.LoginLog;
import com.sicnu.sta.commons.entity.LoginUser;
import com.sicnu.sta.commons.entity.User;
import com.sicnu.sta.data.dao.UserDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user-dao")
public class UserDaoController {

    @Resource
    private UserDao userDao;

    // 判断用户登录是否正确
    @GetMapping("/login")
    public User judgeUserLogin(@RequestParam ("email") String email, @RequestParam ("password") String password) {
        return userDao.judgeUserLogin(email, password);
    }

    // 根据 userId 和 userName 来判断用户是否存在
    @PostMapping("/query-user-by-id-and-name")
    public User queryUserByUserIdAndUserName(@RequestBody LoginUser loginUser) {
        return userDao.queryUserByUserIdAndUserName(loginUser);
    }

    // 根据用户 id 来查询角色名
    @GetMapping("/query-role-name-by-id")
    String queryRoleNameByUserId(@RequestParam ("userId") Integer userId) {
        return userDao.queryRoleNameByUserId(userId);
    }

    // 根据邮箱查找用户
    @GetMapping("/find-user-by-email")
    User findUserByEmail(@RequestParam("email") String email) {
        return userDao.findUserByEmail(email);
    }

    // 通过用户名来查找用户
    @GetMapping("/query-user-by-name")
    User queryUserByUserName(@RequestParam("userName") String userName) {
        return userDao.queryUserByUserName(userName);
    }

    // 插入新注册的用户
    @PostMapping("/insert-newuser")
    void insertNewUser(@RequestBody User user) {
        userDao.insertNewUser(user);
    }

    // 给新建的用户默认分配一个学生角色
    @GetMapping("/add-stu-to-newuser")
    void addStudentToNewUser(@RequestParam("userId") Integer userId,
                             @RequestParam("roleId") Integer roleId) {
        userDao.addStudentToNewUser(userId, roleId);
    }

    // 更改用户密码
    @GetMapping("/update-user-psw")
    int updateUserPassword(@RequestParam("email") String email, @RequestParam("password") String password) {
        return userDao.updateUserPassword(email, password);
    }

    // 模糊查找用户名
    @GetMapping("/vague-query-username")
    List<Map<String, Object>> vagueQueryUserName(@RequestParam("userName") String userName) {
        return userDao.vagueQueryUserName(userName);
    }

    // 根据userId 获取用户信息
    @GetMapping("/query-user-info")
    Map<String, Object> queryUserInfo(@RequestParam("userId") Integer userId) {
        return userDao.queryUserInfo(userId);
    }

    // 根据邀请码来查找班级列表
    @GetMapping("/query/class-list/by-code")
    List<Map<String, Object>> queryClassListByCode(@RequestParam("code") String code) {
        return userDao.queryClassListByCode(code);
    }

    // 用户加入班级
    @GetMapping("/stu-join-class")
    int studentJoinTheClass(@RequestParam("classId") Integer classId,
                            @RequestParam("studentId") Integer studentId) {
        return userDao.studentJoinTheClass(classId, studentId);
    }

    // 修改用户信息
    @GetMapping("/update/user-info")
    void updateUserInfo(@RequestParam("sex") String sex,
                        @RequestParam("birthday") String birthday,
                        @RequestParam("realName") String realName,
                        @RequestParam("address") String address,
                        @RequestParam("phone") String phone,
                        @RequestParam("postCode") String postCode,
                        @RequestParam("userId") Integer userId) {
        userDao.updateUserInfo(sex, birthday, realName, address, phone, postCode, userId);
    }

    // 获取用户班级信息
    @GetMapping("/get/class-info")
    List<Map<String, Object>> getClassInfo(@RequestParam("userId") Integer userId) {
        return userDao.getClassInfo(userId);
    }

    // 退出班级
    @GetMapping("/exit-class")
    void exitClass(@RequestParam("classId") Integer classId,
                   @RequestParam("userId") Integer userId) {
        userDao.exitClass(classId, userId);
    }

    // 查询老师数量
    @GetMapping("/query/teacher-cnt")
    Integer queryTeacherCnt() {
        return  userDao.queryTeacherCnt();
    }

    // 查询学生数量
    @GetMapping("/query/stu-cnt")
    Integer queryStudentCnt() {
        return userDao.queryStudentCnt();
    }

    // 查询题目数量
    @GetMapping("/query/problem-cnt")
    Integer queryProblemCnt() {
        return userDao.queryProblemCnt();
    }

    // 查询比赛数量
    @GetMapping("/query/contest-cnt")
    Integer queryContestCnt() {
        return userDao.queryContestCnt();
    }

    // 查询班级数量
    @GetMapping("/query/class-cnt")
    Integer queryClassCnt() {
        return userDao.queryClassCnt();
    }

    // 查询比赛是否在我的题目集下
    @GetMapping("/query/contest/is-my-contest")
    Map<String, Object> queryContestIsMyContest(@RequestParam("userId") Integer userId,
                                                @RequestParam("contestId") Integer contestId) {
        return userDao.queryContestIsMyContest(userId, contestId);
    }

    // 插入用户登录日志
    @PostMapping("/insert/login-log")
    void insertLoginLog(@RequestBody LoginLog loginLog) {
        userDao.insertLoginLog(loginLog);
    }

    // 查询班级下的学生列表
    @GetMapping("/query/class-stu")
    List<Integer> queryClassStudents(@RequestParam("classId") Integer classId) {
        return userDao.queryClassStudents(classId);
    }

    // 通过 userId 查找用户名
    @GetMapping("/query/username-by-userid")
    String queryUserNameByUserId(@RequestParam("userId") Integer userId) {
        return userDao.queryUserNameByUserId(userId);
    }
    
}
