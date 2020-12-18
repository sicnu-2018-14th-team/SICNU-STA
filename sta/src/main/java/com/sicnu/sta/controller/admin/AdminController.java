package com.sicnu.sta.controller.admin;

import com.sicnu.sta.entity.*;
import com.sicnu.sta.entity.Class;
import com.sicnu.sta.log.MyOpLog;
import com.sicnu.sta.service.admin.impl.AdminServiceImpl;
import com.sicnu.sta.utils.ResultUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class AdminController {

    @Resource
    private AdminServiceImpl adminService;

    // 查询所有用户
    @MyOpLog(value = "查询所有用户")
    @PostMapping("/query/all-user")
    public ResultUtils<Object> queryAllUser() {
        return adminService.queryAllUser();
    }


    // 查询所有用户对应的角色
    @MyOpLog(value = "查看用户的角色")
    @PostMapping("/query/all-user-role")
    public ResultUtils<Object> queryAllUserRole() {
        return adminService.queryAllUserRole();
    }


    // 修改用户的角色
    @MyOpLog(value = "修改用户的角色")
    @GetMapping("/update/user-role/{userId}/{roleId}")
    public ResultUtils<Object> updateUserRole(@PathVariable(value = "userId") int userId,
                                              @PathVariable(value = "roleId") int roleId) {
        return adminService.updateUserRole(userId, roleId);
    }

    // 查询日志信息
    @MyOpLog(value = "查询日志信息")
    @PostMapping("/query/logs")
    public ResultUtils<Object> queryContestListInfo(@RequestParam(value = "pageSize") int pageSize,
                                                    @RequestParam(value = "page") int page,
                                                    @RequestParam(value = "type") int type) {
        return adminService.queryLogs(page, pageSize, type);
    }

    // 创建班级
    @MyOpLog(value = "创建班级")
    @PostMapping("/create/class")
    public ResultUtils<Object> createClass(@RequestBody Class newClass) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        newClass.setTeacherId(loginUser.getUserId());
        newClass.setTeacherName(loginUser.getUserName());
        return adminService.createClass(newClass);
    }

    // 查询班级列表
    @MyOpLog(value = "查询班级列表")
    @PostMapping("/query/class-list")
    public ResultUtils<Object> queryClassList(@RequestParam(value = "page") Integer page,
                                              @RequestParam(value = "pageSize") Integer pageSize) {
        return adminService.queryClassList(page, pageSize);
    }

    // 删除班级
    @MyOpLog(value = "删除班级")
    @PostMapping("/delete/class")
    public ResultUtils<Object> deleteClassByClassId(@RequestParam(value = "classId") Integer classId) {
        return adminService.deleteClassByClassId(classId);
    }

    // 根据班级 id 来查找班级
    @MyOpLog(value = "查找班级信息")
    @PostMapping("/query/class-info")
    public ResultUtils<Object> queryClassByClassId(@RequestParam(value = "classId") Integer classId) {
        return adminService.queryClassByClassId(classId);
    }

    // 修改班级信息
    @MyOpLog(value = "修改班级信息")
    @PostMapping("/update/class-info")
    public ResultUtils<Object> updateClassInfo(@RequestBody Class newClassInfo) {
        return adminService.updateClassInfo(newClassInfo);
    }

    // 查询所有的管理员
    @MyOpLog(value = "查询所有的管理员")
    @PostMapping("/query/admin-list")
    public ResultUtils<Object> queryAdminList() {
        return adminService.queryUserListByRoleId(2);
    }

    // 查询所有的管理员
    @MyOpLog(value = "查询所有的老师")
    @PostMapping("/query/teacher-list")
    public ResultUtils<Object> queryTeacherList() {
        return adminService.queryUserListByRoleId(3);
    }

    // 查询所有的管理员
    @MyOpLog(value = "查询所有的学生")
    @PostMapping("/query/student-list")
    public ResultUtils<Object> queryStudentList() {
        return adminService.queryUserListByRoleId(4);
    }

    // 查询我创建的题目
    @MyOpLog(value = "查询我创建的题目")
    @PostMapping("/query/problem/my-create")
    public ResultUtils<Object> queryMyCreateProblem(@Param("typeId") Integer typeId,
                                                    @Param("pageSize") Integer pageSize,
                                                    @Param("page") Integer page) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        return adminService.queryProblemMyCreate(loginUser.getUserId(), typeId, page, pageSize);
    }

    // 查询我创建的比赛
    @MyOpLog(value = "查询我创建的比赛")
    @PostMapping("/query/contest/my-create")
    public ResultUtils<Object> queryMyCreateContest(@Param("pageSize") Integer pageSize,
                                                    @Param("page") Integer page) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        return adminService.queryContestMyCreate(loginUser.getUserId(), page, pageSize);
    }

    // 查询我创建的班级
    @MyOpLog(value = "查询我创建的班级")
    @PostMapping("/query/class/my-create")
    public ResultUtils<Object> queryMyCreateClass(@Param("pageSize") Integer pageSize,
                                                  @Param("page") Integer page) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        return adminService.queryClassMyCreate(loginUser.getUserId(), page, pageSize);
    }

    // 查询题目信息
    @MyOpLog(value = "查询题目信息")
    @PostMapping("/query/problem-info")
    public ResultUtils<Object> queryProblemInfo(@Param("problemId") Integer problemId) {
        return adminService.queryProblemInfoByProblemId(problemId);
    }

    // 修改判断题
    @MyOpLog(value = "修改判断题")
    @PostMapping("/update/judge-problem")
    public ResultUtils<Object> updateJudgeProblem(@RequestBody JudgeProblem judgeProblem) {
        return adminService.updateJudgeProblem(judgeProblem);
    }

    // 修改选择题
    @MyOpLog(value = "修改选择题")
    @PostMapping("/update/choice-problem")
    public ResultUtils<Object> updateChoiceProblem(@RequestBody ChoiceProblem choiceProblem) {
        return adminService.updateChoiceProblem(choiceProblem);
    }

    // 查询编程题测试文件的信息
    @MyOpLog(value = "查询编程题测试文件的信息")
    @PostMapping("/query/program-test-file-info")
    public ResultUtils<Object> queryProgramTestFileInfo(@RequestParam(value = "problemId") Integer problemId) {
        return adminService.queryProgramTestFileInfo(problemId);
    }

    // 修改编程题信息
    @MyOpLog(value = "修改编程题信息")
    @PostMapping("/update/program-problem")
    public ResultUtils<Object> updateProgramProblem(@RequestBody ProgramProblem programProblem) {
        return adminService.updateProgramProblem(programProblem);
    }

    // 将学生踢出班级
    @MyOpLog(value = "将学生踢出班级")
    @PostMapping("/kick-student-out-class")
    public ResultUtils<Object> kickStudentOutClass(@RequestParam(value = "classId") Integer classId,
                                                   @RequestParam(value = "userId") Integer userId) {
        return adminService.kickStudentOutClass(classId, userId);
    }

    // 查询班级下的学生信息
    @MyOpLog(value = "查询班级下的学生信息")
    @PostMapping("/query/class-student-info")
    public ResultUtils<Object> queryClassStudentInfo(@RequestParam(value = "classId") Integer classId) {
        return adminService.queryClassStudentList(classId);
    }

    // 查询当前班级下的比赛 id
    @MyOpLog(value = "查询当前班级下的比赛 id")
    @PostMapping("/query/class-contest")
    public ResultUtils<Object> queryClassContest(@RequestParam(value = "classId") Integer classId) {
        return adminService.queryClassContest(classId);
    }

}
