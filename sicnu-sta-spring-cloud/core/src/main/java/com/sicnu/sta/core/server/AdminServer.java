package com.sicnu.sta.core.server;

import com.sicnu.sta.commons.entity.Class;
import com.sicnu.sta.commons.entity.OpLog;
import com.sicnu.sta.commons.entity.User;
import com.sicnu.sta.commons.entity.UserRole;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("DATA")
public interface AdminServer {

    // 查询所有用户
    @GetMapping("/admin-dao/query/all-user")
    List<User> queryAllUser();

    // 查询所有用户的角色
    @GetMapping("/admin-dao/query/all-user-role")
    List<UserRole> queryAllUserRole();

    // 更新用户的角色
    @GetMapping("/admin-dao/update/user-role")
    void updateUserRole(@RequestParam("userId") int userId, @RequestParam("roleId") int roleId);

    // 查询登录日志
    @GetMapping("/admin-dao/query/logs")
    List<Map<String, Object>> queryLogs(@RequestParam("cnt") Integer cnt,
                                        @RequestParam("pageSize") Integer pageSize,
                                        @RequestParam("tableName") String tableName);

    // 查询日志的总条数
    @GetMapping("/admin-dao/query/total-log")
    Integer queryTotalLog(@RequestParam("tableName") String tableName);

    // 存储登录日志
    @PostMapping("/admin-dao/save/op-log")
    void saveOpLog(@RequestBody OpLog opLog);

    // 创建一个新班级
    @PostMapping("/admin-dao/create/class")
    void createClass(@RequestBody Class newClass);

    // 查询班级列表
    @GetMapping("/admin-dao/query/class-list")
    List<Map<String, Object> > queryClassList(@RequestParam("cnt") Integer cnt,
                                              @RequestParam("pageSize") Integer pageSize);

    // 删除班级
    @GetMapping("/admin-dao/delete/class-by-classid")
    int deleteClassByClassId(@RequestParam("classId") Integer classId);

    // 根据班级 id 来查询班级
    @GetMapping("/admin-dao/query/class/by-classid")
    Map<String, Object> queryClassByClassId(@RequestParam("classId") Integer classId);

    // 更新班级信息
    @PostMapping("/admin-dao/update/class-info")
    void updateClassInfo(@RequestBody Class newClassInfo);

    // 查询该角色的用户
    @GetMapping("/admin-dao/query/admin-list")
    List<Map<String, Object>> queryAdminList(@RequestParam("roleId") Integer roleId);

    // 查询我创建的题目 id，且题目类型为 typeId
    @GetMapping("/admin-dao/query/my-create-problem")
    List<Integer> queryMyCreateProblemIds(@RequestParam("userId") Integer userId,
                                          @RequestParam("typeId") Integer typeId,
                                          @RequestParam("pageSize") Integer pageSize,
                                          @RequestParam("cnt") Integer cnt) ;

    // 查询我创建的题目的总数，分类型
    @GetMapping("/admin-dao/query/my-create/problem-cnt")
    Integer queryMyCreateProblemCnt(@RequestParam("userId") Integer userId,
                                    @RequestParam("typeId") Integer typeId);

    // 查询我创建的比赛
    @GetMapping("/admin-dao/query/my-create/contest")
    List<Map<String, Object>> queryMyCreateContest(@RequestParam("userId") Integer userId,
                                                   @RequestParam("pageSize") Integer pageSize,
                                                   @RequestParam("cnt") Integer cnt);
    // 查询我创建的比赛的数量
    @GetMapping("/admin-dao/query/my-create/contest-cnt")
    Integer queryMyCreateContestCnt(@RequestParam("userId") Integer userId);

    // 查询我创建的班级
    @GetMapping("/admin-dao/query/my-create/class")
    List<Map<String, Object>> queryMyCreateClass(@RequestParam("userId") Integer userId,
                                                 @RequestParam("pageSize") Integer pageSize,
                                                 @RequestParam("cnt") Integer cnt);

    // 查询我创建的班级数量
    @GetMapping("/admin-dao/query/my-create/class-cnt")
    Integer queryMyCreateClassCnt(@RequestParam("userId") Integer userId);

    // 查询所有班级数量
    @GetMapping("/admin-dao/query/class-cnt")
    Integer queryClassCnt();

    // 查询班级下的比赛
    @GetMapping("/admin-dao/query/class/contest")
    List<Map<String, Object>> queryClassContest(@RequestParam("classId") Integer classId);
}
