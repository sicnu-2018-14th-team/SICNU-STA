package com.sicnu.sta.core.service;

import com.sicnu.sta.commons.entity.*;
import com.sicnu.sta.commons.entity.Class;
import com.sicnu.sta.commons.utils.ResultUtils;

import java.util.List;

public interface AdminService {

    /**
     * 查询所有用户
     * @return ResultUtils
     */
    ResultUtils<Object> queryAllUser();

    /**
     * 查询所有用户的角色
     * @return ResultUtils
     */
    ResultUtils<Object> queryAllUserRole();

    /**
     * 更改用户角色
     * @return ResultUtils
     */
    ResultUtils<Object> updateUserRole(int userId, int roleId);

    /**
     * 查询用户登录日志
     * @param page 当前页
     * @param pageSize 页面大小
     * @param type 日志类型
     * @return ResultUtils
     */
    ResultUtils<Object> queryLogs(int page, int pageSize, int type);

    /**
     * 存储操作日志
     * @param opLog 操作日志实体类
     */
    void saveOpLog(OpLog opLog);

    /**
     * 创建一个新班级
     * @param newClass 班级实体类
     * @return Result
     */
    ResultUtils<Object> createClass(Class newClass);

    /**
     * 查询班级列表
     * @return Result
     */
    ResultUtils<Object> queryClassList(Integer page, Integer pageSize);

    /**
     * 删除班级
     * @param classId 班级 id
     * @return Result
     */
    ResultUtils<Object> deleteClassByClassId(Integer classId);

    /**
     * 根据班级 id 来查找班级
     * @param classId 班级 id
     * @return class
     */
    ResultUtils<Object> queryClassByClassId(Integer classId);

    /**
     * 修改班级信息
     * @param newClassInfo class
     * @return result
     */
    ResultUtils<Object> updateClassInfo(Class newClassInfo);

    /**
     * 查询用户列表根据角色
     * @return result
     */
    ResultUtils<Object> queryUserListByRoleId(Integer roleId);

    /**
     * 查询我创建的题目
     * @param userId 用户 id
     * @return result
     */
    ResultUtils<Object> queryProblemMyCreate(Integer userId, Integer typeId, Integer page, Integer pageSize);

    /**
     * 查询我创建的比赛
     * @param userId 用户 id
     * @return result
     */
    ResultUtils<Object> queryContestMyCreate(Integer userId, Integer page, Integer pageSize);

    /**
     * 查询我创建的班级
     * @param userId 用户 id
     * @return result
     */
    ResultUtils<Object> queryClassMyCreate(Integer userId, Integer page, Integer pageSize);

    /**
     * 根据 problemId 来查询题目信息
     * @param problemId 题目 id
     * @return result
     */
    ResultUtils<Object> queryProblemInfoByProblemId(Integer problemId);

    /**
     * 修改判断题信息
     * @param judgeProblem 判断题实体类
     * @return result
     */
    ResultUtils<Object> updateJudgeProblem(JudgeProblem judgeProblem);

    /**
     * 修改选择题信息
     * @param choiceProblem 选择题实体类
     * @return result
     */
    ResultUtils<Object> updateChoiceProblem(ChoiceProblem choiceProblem);

    /**
     * 修改题目标签
     * @param problemId 题目 id
     * @param tags 标签名列表
     */
    void updateProblemTag(Integer problemId, List<String> tags);

    /**
     * 查询编程题测试文件的信息
     * @param problemId 题目 id
     * @return 文件名信息
     */
    ResultUtils<Object> queryProgramTestFileInfo(Integer problemId);

    /**
     * 修改编程题信息
     * @param programProblem 编程题实体类
     * @return result
     */
    ResultUtils<Object> updateProgramProblem(ProgramProblem programProblem);

    /**
     * 将学生踢出班级
     * @param classId 班级 id
     * @param userId 学生 id
     * @return result
     */
    ResultUtils<Object> kickStudentOutClass(Integer classId, Integer userId);

    /**
     * 查询班级下的学生
     * @param classId 班级 id
     * @return 学生信息
     */
    ResultUtils<Object> queryClassStudentList(Integer classId);

    /**
     * 查询班级下的比赛
     * @param classId 班级 id
     * @return result
     */
    ResultUtils<Object> queryClassContest(Integer classId);



}
