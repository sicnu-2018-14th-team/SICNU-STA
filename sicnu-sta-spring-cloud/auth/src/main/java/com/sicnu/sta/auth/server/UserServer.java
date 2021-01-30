package com.sicnu.sta.auth.server;

import com.sicnu.sta.commons.entity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient("DATA")
public interface UserServer {

    // 用户登录查询
    @GetMapping("/user-dao/login")
    User judgeUserLogin(@RequestParam("email") String email, @RequestParam("password") String password);

    // 根据 userId 和 userName 来判断用户是否存在
    @PostMapping("/user-dao/query-user-by-id-and-name")
    User queryUserByUserIdAndUserName(@RequestBody LoginUser loginUser);

    // 根据用户 id 来查询角色名
    @GetMapping("/user-dao/query-role-name-by-id")
    String queryRoleNameByUserId(@RequestParam ("userId") Integer userId);

    // 根据邮箱查找用户
    @GetMapping("/user-dao/find-user-by-email")
    User findUserByEmail(@RequestParam("email") String email);

    // 通过用户名来查找用户
    @GetMapping("/user-dao/query-user-by-name")
    User queryUserByUserName(@RequestParam("userName") String userName);

    // 插入新注册的用户
    @PostMapping("/user-dao/insert-newuser")
    void insertNewUser(@RequestBody User user);

    // 给新建的用户默认分配一个学生角色
    @GetMapping("/user-dao/add-stu-to-newuser")
    void addStudentToNewUser(@RequestParam("userId") Integer userId,
                             @RequestParam("roleId") Integer roleId);

    // 更改用户密码
    @GetMapping("/user-dao/update-user-psw")
    int updateUserPassword(@RequestParam("email") String email, @RequestParam("password") String password);

    // 模糊查找用户名
    @GetMapping("/user-dao/vague-query-username")
    List<Map<String, Object>> vagueQueryUserName(@RequestParam("userName") String userName);

    // 根据userId 获取用户信息
    @GetMapping("/user-dao/query-user-info")
    Map<String, Object> queryUserInfo(@RequestParam("userId") Integer userId);

    // 根据邀请码来查找班级列表
    @GetMapping("/user-dao/query/class-list/by-code")
    List<Map<String, Object>> queryClassListByCode(@RequestParam("code") String code);

    // 用户加入班级
    @GetMapping("/user-dao/stu-join-class")
    int studentJoinTheClass(@RequestParam("classId") Integer classId,
                            @RequestParam("studentId") Integer studentId);

    // 修改用户信息
    @GetMapping("/user-dao/update/user-info")
    void updateUserInfo(@RequestParam("sex") String sex,
                        @RequestParam("birthday") String birthday,
                        @RequestParam("realName") String realName,
                        @RequestParam("address") String address,
                        @RequestParam("phone") String phone,
                        @RequestParam("postCode") String postCode,
                        @RequestParam("userId") Integer userId);

    // 获取用户班级信息
    @GetMapping("/user-dao/get/class-info")
    List<Map<String, Object>> getClassInfo(@RequestParam("userId") Integer userId);

    // 退出班级
    @GetMapping("/user-dao/exit-class")
    void exitClass(@RequestParam("classId") Integer classId,
                   @RequestParam("userId") Integer userId);

    // 查询老师数量
    @GetMapping("/user-dao/query/teacher-cnt")
    Integer queryTeacherCnt();

    // 查询学生数量
    @GetMapping("/user-dao/query/stu-cnt")
    Integer queryStudentCnt();

    // 查询题目数量
    @GetMapping("/user-dao/query/problem-cnt")
    Integer queryProblemCnt();

    // 查询比赛数量
    @GetMapping("/user-dao/query/contest-cnt")
    Integer queryContestCnt();

    // 查询班级数量
    @GetMapping("/user-dao/query/class-cnt")
    Integer queryClassCnt();

    // 查询比赛是否在我的题目集下
    @GetMapping("/user-dao/query/contest/is-my-contest")
    Map<String, Object> queryContestIsMyContest(@RequestParam("userId") Integer userId,
                                                @RequestParam("contestId") Integer contestId);

    // 插入用户登录日志
    @PostMapping("/user-dao/insert/login-log")
    void insertLoginLog(@RequestBody LoginLog loginLog);

    // 根据用户 id 来查询用户拥有的角色
    @GetMapping("/auth-dao/query/user-role")
    Role queryUserRoleByUserId(@RequestParam("userId") int userId);

    // 根据角色 id 来查询角色权限
    @GetMapping("/auth-dao/query/role-auth")
    List<Auth> queryRoleAuthByRoleId(@RequestParam("roleId") int roleId);

    // 查询班级下的比赛 id
    @GetMapping("/contest-dao/query/class-contest-id")
    List<Integer> queryClassContestIds(@RequestParam("classId") Integer classId);

    // 查询所有角色列表
    @GetMapping("/role-dao/query/all-role")
    List<Role> queryAllRoleList();

    // 添加一个角色
    @PostMapping ("/role-dao/add/role")
    void addRole(@RequestBody Role role);

    // 根据用户角色删除对应的用户角色
    @GetMapping("/role-dao/delete/user-role")
    void deleteUserRoleByRoleId(@RequestParam("roleId") int roleId);

    // 根据用户角色删除对应的角色权限
    @GetMapping("/role-dao/delete/role-auth")
    void deleteRoleAuthByRoleId(@RequestParam("roleId") int roleId);

    // 根据角色 id 删除角色
    @GetMapping("/role-dao/delete/role")
    int deleteRoleByRoleId(@RequestParam("roleId") int roleId);

    // 添加角色权限
    @GetMapping("/role-dao/add/role-auth")
    void addRoleAuthByRoleId(@RequestParam("roleId") int roleId, @RequestParam("authId") int authId);

}
