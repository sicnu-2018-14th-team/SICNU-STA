package com.sicnu.sta.data.controller;

import com.sicnu.sta.commons.entity.OpLog;
import com.sicnu.sta.commons.entity.User;
import com.sicnu.sta.commons.entity.UserRole;
import com.sicnu.sta.commons.entity.Class;
import com.sicnu.sta.data.dao.AdminDao;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin-dao")
public class AdminDaoController {
    
    @Resource
    private AdminDao adminDao;

    // 查询所有用户
    @GetMapping("/query/all-user")
    List<User> queryAllUser() {
        return adminDao.queryAllUser();
    }

    // 查询所有用户的角色
    @GetMapping("/query/all-user-role")
    List<UserRole> queryAllUserRole() {
        return adminDao.queryAllUserRole();
    }

    // 更新用户的角色
    @GetMapping("/update/user-role")
    void updateUserRole(@RequestParam("userId") int userId, @RequestParam("roleId") int roleId) {
        adminDao.updateUserRole(userId, roleId);
    }

    // 查询登录日志
    @GetMapping("/query/logs")
    List<Map<String, Object>> queryLogs(@RequestParam("cnt") Integer cnt,
                                        @RequestParam("pageSize") Integer pageSize,
                                        @RequestParam("tableName") String tableName) {
        return adminDao.queryLogs(cnt, pageSize, tableName);
    }

    // 查询日志的总条数
    @GetMapping("/query/total-log")
    Integer queryTotalLog(@RequestParam("tableName") String tableName) {
        return adminDao.queryTotalLog(tableName);
    }

    // 存储登录日志
    @PostMapping("/save/op-log")
    void saveOpLog(@RequestBody OpLog opLog) {
        adminDao.saveOpLog(opLog);
    }

    // 创建一个新班级
    @PostMapping("/create/class")
    void createClass(@RequestBody Class newClass) {
        adminDao.createClass(newClass);
    }

    // 查询班级列表
    @GetMapping("/query/class-list")
    List<Map<String, Object> > queryClassList(@RequestParam("cnt") Integer cnt,
                                              @RequestParam("pageSize") Integer pageSize) {
        return adminDao.queryClassList(cnt, pageSize);
    }

    // 删除班级
    @GetMapping("/delete/class-by-classid")
    int deleteClassByClassId(@RequestParam("classId") Integer classId) {
        return adminDao.deleteClassByClassId(classId);
    }

    // 根据班级 id 来查询班级
    @GetMapping("/query/class/by-classid")
    Map<String, Object> queryClassByClassId(@RequestParam("classId") Integer classId) {
        return adminDao.queryClassByClassId(classId);
    }

    // 更新班级信息
    @PostMapping("/update/class-info")
    void updateClassInfo(@RequestBody Class newClassInfo) {
        adminDao.updateClassInfo(newClassInfo);
    }

    // 查询该角色的用户
    @GetMapping("/query/admin-list")
    List<Map<String, Object>> queryAdminList(@RequestParam("roleId") Integer roleId) {
        return adminDao.queryAdminList(roleId);
    }

    // 查询我创建的题目 id，且题目类型为 typeId
    @GetMapping("/query/my-create-problem")
    List<Integer> queryMyCreateProblemIds(@RequestParam("userId") Integer userId,
                                          @RequestParam("typeId") Integer typeId,
                                          @RequestParam("pageSize") Integer pageSize,
                                          @RequestParam("cnt") Integer cnt) {
        return adminDao.queryMyCreateProblemIds(userId, typeId, pageSize, cnt);
    }

    // 查询我创建的题目的总数，分类型
    @GetMapping("/query/my-create/problem-cnt")
    Integer queryMyCreateProblemCnt(@RequestParam("userId") Integer userId,
                                    @RequestParam("typeId") Integer typeId) {
        return adminDao.queryMyCreateProblemCnt(userId, typeId);
    }

    // 查询我创建的比赛
    @GetMapping("/query/my-create/contest")
    List<Map<String, Object>> queryMyCreateContest(@RequestParam("userId") Integer userId,
                                                   @RequestParam("pageSize") Integer pageSize,
                                                   @RequestParam("cnt") Integer cnt) {
        return adminDao.queryMyCreateContest(userId, pageSize, cnt);
    }
    // 查询我创建的比赛的数量
    @GetMapping("/query/my-create/contest-cnt")
    Integer queryMyCreateContestCnt(@RequestParam("userId") Integer userId) {
        return adminDao.queryMyCreateContestCnt(userId);
    }

    // 查询我创建的班级
    @GetMapping("/query/my-create/class")
    List<Map<String, Object>> queryMyCreateClass(@RequestParam("userId") Integer userId,
                                                 @RequestParam("pageSize") Integer pageSize,
                                                 @RequestParam("cnt") Integer cnt) {
        return adminDao.queryMyCreateClass(userId, pageSize, cnt);
    }

    // 查询我创建的班级数量
    @GetMapping("/query/my-create/class-cnt")
    Integer queryMyCreateClassCnt(@RequestParam("userId") Integer userId) {
        return adminDao.queryMyCreateClassCnt(userId);
    }

    // 查询所有班级数量
    @GetMapping("/query/class-cnt")
    Integer queryClassCnt() {
        return adminDao.queryClassCnt();
    }

    // 查询班级下的比赛
    @GetMapping("/query/class/contest")
    List<Map<String, Object>> queryClassContest(@RequestParam("classId") Integer classId) {
        return adminDao.queryClassContest(classId);
    }
}
