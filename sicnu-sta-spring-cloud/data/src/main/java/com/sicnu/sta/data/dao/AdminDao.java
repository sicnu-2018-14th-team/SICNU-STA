package com.sicnu.sta.data.dao;

import com.sicnu.sta.commons.entity.OpLog;
import com.sicnu.sta.commons.entity.User;
import com.sicnu.sta.commons.entity.UserRole;
import com.sicnu.sta.commons.entity.Class;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminDao {

    // 查询所有用户
    List<User> queryAllUser();

    // 查询所有用户的角色
    List<UserRole> queryAllUserRole();

    // 更新用户的角色
    void updateUserRole(@Param("userId") int userId, @Param("roleId") int roleId);

    // 查询登录日志
    List<Map<String, Object>> queryLogs(@Param("cnt") Integer cnt,
                                             @Param("pageSize") Integer pageSize,
                                             @Param("tableName") String tableName);

    // 查询日志的总条数
    Integer queryTotalLog(@Param("tableName") String tableName);

    // 存储登录日志
    void saveOpLog(@Param("opLog") OpLog opLog);

    // 创建一个新班级
    void createClass(@Param("class") Class newClass);

    // 查询班级列表
    List<Map<String, Object> > queryClassList(@Param("cnt") Integer cnt,
                                              @Param("pageSize") Integer pageSize);

    // 删除班级
    int deleteClassByClassId(@Param("classId") Integer classId);

    // 根据班级 id 来查询班级
    Map<String, Object> queryClassByClassId(@Param("classId") Integer classId);

    // 更新班级信息
    void updateClassInfo(@Param("class") Class newClassInfo);

    // 查询该角色的用户
    List<Map<String, Object>> queryAdminList(@Param("roleId") Integer roleId);

    // 查询我创建的题目 id，且题目类型为 typeId
    List<Integer> queryMyCreateProblemIds(@Param("userId") Integer userId,
                                          @Param("typeId") Integer typeId,
                                          @Param("pageSize") Integer pageSize,
                                          @Param("cnt") Integer cnt);

    // 查询我创建的题目的总数，分类型
    Integer queryMyCreateProblemCnt(@Param("userId") Integer userId,
                                    @Param("typeId") Integer typeId);

    // 查询我创建的比赛
    List<Map<String, Object>> queryMyCreateContest(@Param("userId") Integer userId,
                                                   @Param("pageSize") Integer pageSize,
                                                   @Param("cnt") Integer cnt);
    // 查询我创建的比赛的数量
    Integer queryMyCreateContestCnt(@Param("userId") Integer userId);

    // 查询我创建的班级
    List<Map<String, Object>> queryMyCreateClass(@Param("userId") Integer userId,
                                                 @Param("pageSize") Integer pageSize,
                                                 @Param("cnt") Integer cnt);

    // 查询我创建的班级数量
    Integer queryMyCreateClassCnt(@Param("userId") Integer userId);

    // 查询所有班级数量
    Integer queryClassCnt();

    // 查询班级下的比赛
    List<Map<String, Object>> queryClassContest(@Param("classId") Integer classId);

}
