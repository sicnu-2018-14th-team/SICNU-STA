package com.sicnu.sta.data.dao;

import com.sicnu.sta.commons.entity.LoginLog;
import com.sicnu.sta.commons.entity.LoginUser;
import com.sicnu.sta.commons.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {

    // 判断用户登录是否正确
    User judgeUserLogin(@Param("email") String email, @Param("password") String password);

    // 插入新注册的用户
    void insertNewUser(@Param("user") User user);

    // 根据邮箱查找用户
    User findUserByEmail(@Param("email") String email);

    // 更改用户密码
    int updateUserPassword(@Param("email") String email, @Param("password") String password);

    // 通过 userId 查找用户名
    String queryUserNameByUserId(@Param("userId") Integer userId);

    // 通过用户名来查找用户
    User queryUserByUserName(@Param("userName") String userName);

    // 模糊查找用户名
    List<Map<String, Object>> vagueQueryUserName(@Param("userName") String userName);

    // 插入用户登录日志
    void insertLoginLog(@Param("loginLog")LoginLog loginLog);

    // 根据 userId 和 userName 来判断用户是否存在
    User queryUserByUserIdAndUserName(@Param("loginUser") LoginUser loginUser);

    // 根据userId 获取用户信息
    Map<String, Object> queryUserInfo(@Param("userId") Integer userId);

    // 给新建的用户默认分配一个学生角色
    void addStudentToNewUser(@Param("userId") Integer userId,
                             @Param("roleId") Integer roleId);

    // 根据邀请码来查找班级列表
    List<Map<String, Object>> queryClassListByCode(@Param("code") String code);

    // 用户加入班级
    int studentJoinTheClass(@Param("classId") Integer classId,
                            @Param("studentId") Integer studentId);

    // 修改用户信息
    void updateUserInfo(@Param("sex") String sex,
                        @Param("birthday") String birthday,
                        @Param("realName") String realName,
                        @Param("address") String address,
                        @Param("phone") String phone,
                        @Param("postCode") String postCode,
                        @Param("userId") Integer userId);

    // 获取用户班级信息
    List<Map<String, Object>> getClassInfo(@Param("userId") Integer userId);

    // 查询班级下的学生列表
    List<Integer> queryClassStudents(@Param("classId") Integer classId);

    // 退出班级
    void exitClass(@Param("classId") Integer classId,
                   @Param("userId") Integer userId);

    // 查询老师数量
    Integer queryTeacherCnt();

    // 查询学生数量
    Integer queryStudentCnt();

    // 查询题目数量
    Integer queryProblemCnt();

    // 查询比赛数量
    Integer queryContestCnt();

    // 查询班级数量
    Integer queryClassCnt();

    // 查询比赛是否在我的题目集下
    Map<String, Object> queryContestIsMyContest(@Param("userId") Integer userId,
                                                @Param("contestId") Integer contestId);

    // 根据用户 id 来查询角色名
    String queryRoleNameByUserId(@Param("userId") Integer userId);
}
