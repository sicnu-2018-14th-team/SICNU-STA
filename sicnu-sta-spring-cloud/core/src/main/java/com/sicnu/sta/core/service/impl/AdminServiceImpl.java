package com.sicnu.sta.core.service.impl;

import com.sicnu.sta.commons.entity.*;
import com.sicnu.sta.commons.entity.Class;
import com.sicnu.sta.commons.utils.ResultUtils;
import com.sicnu.sta.core.server.AdminServer;
import com.sicnu.sta.core.server.ProblemServer;
import com.sicnu.sta.core.server.TagServer;
import com.sicnu.sta.core.server.UsersServer;
import com.sicnu.sta.core.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminServer adminDao;

    @Resource
    private ProblemServer problemDao;

    @Resource
    private TagServer tagDao;

    @Resource
    private UsersServer userDao;

    @Resource
    private AdminTagServiceImpl adminTagService;


    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;

    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    /**
     * 查询所有用户
     *
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> queryAllUser() {
        try {
            List<User> userList = adminDao.queryAllUser();
            return ResultUtils.success(userList);
        } catch (Exception e) {
            log.error("查询所有用户失败", e);
            return ResultUtils.error();
        }
    }

    /**
     * 查询用户所有角色
     *
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> queryAllUserRole() {
        try {
            List<UserRole> userRoles = adminDao.queryAllUserRole();
            return ResultUtils.success(userRoles);
        } catch (Exception e) {
            log.error("查询用户角色失败", e);
            return ResultUtils.error();
        }
    }

    /**
     * 更改用户角色
     *
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> updateUserRole(int userId, int roleId) {
        try {
            adminDao.updateUserRole(userId, roleId);
            return ResultUtils.success();
        } catch (Exception e) {
            log.error("更新用户角色失败", e);
            return ResultUtils.error();
        }
    }

    /**
     * 查询日志
     *
     * @param page     当前页
     * @param pageSize 页面大小
     * @param type 日志类型
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> queryLogs(int page, int pageSize, int type) {
        try {
            List<Object> data = new ArrayList<>();
            String tableName = null;
            if (type == 1) tableName = "login_log";
            else if (type == 2) tableName = "sys_log";
            else if (type == 3) tableName = "op_log";
            int total = adminDao.queryTotalLog(tableName);
            List<Map<String, Object>> mapList = adminDao.queryLogs((page - 1) * pageSize, pageSize, tableName);
            data.add(total);
            data.add(mapList);
            return ResultUtils.success(data);
        } catch (Exception e) {
            log.error("查询日志失败", e);
            return ResultUtils.error();
        }
    }

    /**
     * 存储操作日志
     *
     * @param opLog 操作日志实体类
     */
    @Override
    public void saveOpLog(OpLog opLog) {
        try {
            adminDao.saveOpLog(opLog);
        } catch (Exception e) {
            log.error("存储操作日志失败", e);
        }
    }

    /**
     * 创建一个新班级
     *
     * @param newClass 班级实体类
     * @return Result
     */
    @Override
    public ResultUtils<Object> createClass(Class newClass) {
        try {
            StringBuilder shortBuffer = new StringBuilder();
            String uuid = UUID.randomUUID().toString().replace("-", "");
            for (int i = 0; i < 8; i++) {
                String str = uuid.substring(i * 4, i * 4 + 4);
                int x = Integer.parseInt(str, 16);
                shortBuffer.append(chars[x % 0x3E]);
            }
            newClass.setCode(shortBuffer.toString());
            adminDao.createClass(newClass);
            return ResultUtils.success();
        } catch (Exception e) {
            log.error("创建一个新班级失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 查询班级列表
     *
     * @return Result
     */
    @Override
    public ResultUtils<Object> queryClassList(Integer page, Integer pageSize) {
        try {
            Map<String, Object> data = new HashMap<>();
            List<Map<String, Object>> mapList = adminDao.queryClassList((page - 1) * pageSize, pageSize);
            data.put("data", mapList);
            int total = adminDao.queryClassCnt();
            data.put("total", total);
            return ResultUtils.success(data);
        } catch (Exception e) {
            log.error("查询班级列表失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 删除班级
     *
     * @param classId 班级 id
     * @return Result
     */
    @Override
    public ResultUtils<Object> deleteClassByClassId(Integer classId) {
        try {
            int cnt = adminDao.deleteClassByClassId(classId);
            if (cnt == 1) {
                return ResultUtils.success();
            } else {
                return ResultUtils.fail("删除失败");
            }
        } catch (Exception e) {
            log.error("删除班级失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 根据班级列表来查找班级
     *
     * @param classId 班级 id
     * @return class
     */
    @Override
    public ResultUtils<Object> queryClassByClassId(Integer classId) {
        try {
            Map<String, Object> map = adminDao.queryClassByClassId(classId);
            return ResultUtils.success(map);
        } catch (Exception e) {
            log.error("根据班级 id 查找班级失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 修改班级信息
     *
     * @param newClassInfo class
     * @return result
     */
    @Override
    public ResultUtils<Object> updateClassInfo(Class newClassInfo) {
        try {
            adminDao.updateClassInfo(newClassInfo);
            return ResultUtils.success();
        } catch (Exception e) {
            log.error("修改班级信息失败：", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 查询管理员列表
     *
     * @return 管理员列表数据
     */
    @Override
    public ResultUtils<Object> queryUserListByRoleId(Integer roleId) {
        try {
            return ResultUtils.success(adminDao.queryAdminList(roleId));
        } catch (Exception e) {
            log.error("查询数据失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 查询我创建的题目
     *
     * @param userId 用户 id
     * @return result
     */
    @Override
    public ResultUtils<Object> queryProblemMyCreate(Integer userId, Integer typeId, Integer page, Integer pageSize) {
        try {
            Map<String, Object> resData = new HashMap<>();
            List<Integer> problemIds = adminDao.queryMyCreateProblemIds(userId, typeId, pageSize, (page - 1) * pageSize);
            int total = adminDao.queryMyCreateProblemCnt(userId, typeId);
            resData.put("total", total);
            String tableName = problemDao.queryTableNameByTypeId(typeId);
            List<Map<String, Object>> data = new ArrayList<>();
            for (Integer it : problemIds) {
                Map<String, Object> map = problemDao.queryProblemInfo(it, tableName);
                map.put("problem_id", problemDao.queryProblemIdByRealIdAndTypeId(it, typeId));
                data.add(map);
            }
            resData.put("data", data);
            return ResultUtils.success(resData);
        } catch (Exception e) {
            log.error("查询我创建的题目失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 查询我创建的比赛
     *
     * @param userId 用户 id
     * @return result
     */
    @Override
    public ResultUtils<Object> queryContestMyCreate(Integer userId, Integer page, Integer pageSize) {
       try {
           Map<String, Object> data = new HashMap<>();
           List<Map<String, Object>> mapList = adminDao.queryMyCreateContest(userId, pageSize, (page - 1) * pageSize);
           int total = adminDao.queryMyCreateContestCnt(userId);
           data.put("total", total);
           data.put("data", mapList);
           return ResultUtils.success(data);
       } catch (Exception e) {
           log.error("查询我创建的比赛失败", e);
           return ResultUtils.error(e.getMessage());
       }
    }

    /**
     * 查询我创建的班级
     *
     * @param userId 用户 id
     * @return result
     */
    @Override
    public ResultUtils<Object> queryClassMyCreate(Integer userId, Integer page, Integer pageSize) {
        try {
            Map<String, Object> data = new HashMap<>();
            int total = adminDao.queryMyCreateClassCnt(userId);
            List<Map<String, Object>> mapList = adminDao.queryMyCreateClass(userId, pageSize, (page - 1) * pageSize);
            data.put("total", total);
            data.put("data", mapList);
            return ResultUtils.success(data);
        } catch (Exception e) {
            log.error("查询我创建的班级失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 根据 problemId 来查询题目信息
     *
     * @param problemId 题目 id
     * @return result
     */
    @Override
    public ResultUtils<Object> queryProblemInfoByProblemId(Integer problemId) {
        try {
            Map<String, Object> map = problemDao.queryRealIdAndTypeIdByProblemId(problemId);
            Integer typeId = (Integer) map.get("type_id");
            String tableName = problemDao.queryTableNameByTypeId(typeId);
            Integer realId = (Integer) map.get("real_id");
            Map<String, Object> data = problemDao.queryProblemInfo(realId, tableName);
            String tag = problemDao.queryTagsByProblemId(problemId);
            List<String> tagNames = new ArrayList<>();
            for (int i = 0; i < tag.length(); i ++) {
                // 含有此标签
                if (tag.charAt(i) == '1') {
                    String tagName = tagDao.queryTagNameByTagId(i + 1);
                    tagNames.add(tagName);
                }
            }
            data.put("tag", tagNames);
            return ResultUtils.success(data);
        } catch (Exception e) {
            log.error("查询题目信息失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 修改判断题信息
     *
     * @param judgeProblem 判断题实体类
     * @return result
     */
    @Override
    public ResultUtils<Object> updateJudgeProblem(JudgeProblem judgeProblem) {
        try {
            Integer problemId = judgeProblem.getProblemId();
            Integer realId = problemDao.queryRealIdByProblemIdAndTypeId(problemId, 1);
            judgeProblem.setProblemId(realId);
            problemDao.updateJudgeProblem(judgeProblem);
            updateProblemTag(problemId, judgeProblem.getTagList());
            return ResultUtils.success();
        } catch (Exception e) {
            log.error("修改判断题失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 修改选择题信息
     *
     * @param choiceProblem 选择题实体类
     * @return result
     */
    @Override
    public ResultUtils<Object> updateChoiceProblem(ChoiceProblem choiceProblem) {
        try {
            Integer problemId = choiceProblem.getProblemId();
            Integer realId = problemDao.queryRealIdByProblemIdAndTypeId(problemId, 2);
            choiceProblem.setProblemId(realId);
            problemDao.updateChoiceProblem(choiceProblem);
            updateProblemTag(problemId, choiceProblem.getTagList());
            return ResultUtils.success();
        } catch (Exception e) {
            log.error("修改选择题信息失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 修改题目标签
     *
     * @param problemId 题目 id
     * @param tags      标签名列表
     */
    @Override
    public void updateProblemTag(Integer problemId, List<String> tags) {
        // 获取还未更新前的标签字符串
        String curTag = problemDao.queryTagsByProblemId(problemId);
        for (int i = 0; i < curTag.length(); i ++) {
            if (curTag.charAt(i) == '1') {
                String tmpPro = tagDao.queryProblemByTag(i + 1);
                StringBuilder stringBuilder = new StringBuilder(tmpPro);
                stringBuilder.setCharAt(problemId - 1, '0');
                tagDao.updateTagProblems(i + 1, stringBuilder.toString());
            }
        }
        String tagStr = adminTagService.getTagStr(tags, problemId);
        problemDao.setProblemTag(tagStr, problemId);
    }

    /**
     * 查询编程题测试文件的信息
     *
     * @param problemId 题目 id
     * @return 文件名信息
     */
    @Override
    public ResultUtils<Object> queryProgramTestFileInfo(Integer problemId) {
        try {
            String uuid = problemDao.queryPathByProblemId(problemId);
            String path = UPLOAD_FOLDER + uuid + "/1/";
            List<String> data = new ArrayList<>();
            String IN_FILE_TYPE = ".in";
            // 遍历文件夹 验证是否合法
            File[] fs = new File(path).listFiles();
            if (fs == null) return ResultUtils.fail("测试文件为空");
            for(File f : fs){
                String fileName = f.getName();
                //获取最后一个.的位置
                int lastIndexOf = fileName.lastIndexOf(".");
                String suffix = fileName.substring(lastIndexOf);
                String prefix = fileName.substring(0, lastIndexOf);
                if (IN_FILE_TYPE.equals(suffix)) {
                    data.add(prefix);
                }
            }
            return ResultUtils.success(data);
        } catch (Exception e) {
            log.error("查询编程题测试文件信息失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 修改编程题信息
     *
     * @param programProblem 编程题实体类
     * @return result
     */
    @Override
    public ResultUtils<Object> updateProgramProblem(ProgramProblem programProblem) {
        try {
            Integer problemId = programProblem.getProblemId();
            TestCasePath testCasePath = problemDao.queryTestCasePathByPathId(problemId);
            // 不为空并且 pathId > 0 则说明上传了新的测试文件
            if (testCasePath != null && programProblem.getPathId() != null && programProblem.getPathId() > 0) {
                String path = UPLOAD_FOLDER + testCasePath.getUuid();
                // 删除数据库中的记录
                problemDao.deletePathByProblemId(problemId);
                // 将之前的测试文件删除
                AdminProblemServiceImpl.deleteDir(path);
                // 设置新的路径映射关系
                problemDao.updateProblemIdFromTestCasePath(problemId, programProblem.getPathId());
            }
            Integer realId = problemDao.queryRealIdByProblemIdAndTypeId(problemId, 3);
            programProblem.setProblemId(realId);
            problemDao.updateProgramProblem(programProblem);
            updateProblemTag(problemId, programProblem.getTagList());
            return ResultUtils.success();
        } catch (Exception e) {
            log.error("修改编程题信息失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 将学生踢出班级
     *
     * @param classId 班级 id
     * @param userId  学生 id
     * @return result
     */
    @Override
    public ResultUtils<Object> kickStudentOutClass(Integer classId, Integer userId) {
        try {
            userDao.exitClass(classId, userId);
            return ResultUtils.success();
        } catch (Exception e) {
            log.error("将学生踢出班级", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 查询班级下的学生
     *
     * @param classId 班级 id
     * @return 学生信息
     */
    @Override
    public ResultUtils<Object> queryClassStudentList(Integer classId) {
        try {
            List<Integer> userIds = userDao.queryClassStudents(classId);
            List<Object> data = new ArrayList<>();
            for (Integer userId : userIds) {
                data.add(userDao.queryUserInfo(userId));
            }
            return ResultUtils.success(data);
        } catch (Exception e) {
            log.error("查询班级下的学生信息失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 查询班级下的比赛
     *
     * @param classId 班级 id
     * @return result
     */
    @Override
    public ResultUtils<Object> queryClassContest(Integer classId) {
        try {
            List<Map<String, Object>> data = adminDao.queryClassContest(classId);
            return ResultUtils.success(data);
        } catch (Exception e) {
            log.error("查询班级下的比赛失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

}
