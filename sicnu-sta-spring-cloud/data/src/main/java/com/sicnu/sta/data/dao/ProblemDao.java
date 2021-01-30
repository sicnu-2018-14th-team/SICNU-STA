package com.sicnu.sta.data.dao;

import com.sicnu.sta.commons.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProblemDao {

    // 添加题目
    void addProblem(@Param("problem") Problem problem);

    // 设置题目标签
    void setProblemTag(@Param("tag") String tag, @Param("problemId") int problemId);

    // 查询题目对应题目表下的 id 和 类型 id
    Map<String, Integer> queryProblemRealId(@Param("problemId") int problemId);

    // 查询所有题目类型
    List<Map<String, Object>> queryAllProblemType();

    // 根据 typeId 来查询对应的表名
    String queryTableNameByTypeId(@Param("typeId") Integer typeId);

    // 根据 题目 id 和对应的表名查询题目信息
    Map<String, Object> queryProblemInfo(@Param("problemId") Integer problemId,
                                         @Param("tableName") String tableName);

    // 根据作者 id 和 题目难度 来查找题目
    List<Integer> queryProblemIdByAuthorIdAndDifficultyAndTypeId(@Param("authorId") Integer authorId,
                                                                 @Param("difficulty") Integer difficulty,
                                                                 @Param("typeId") Integer typeId);

    // 通过 problem id 来查询真实 id 和题目类型 id
    Map<String, Object> queryRealIdAndTypeIdByProblemId(@Param("problemId") Integer problemId);

    // 查询指定类型下的题目真实 id
    List<Integer> queryRealIdByTypeId(@Param("typeId") Integer typeId);


    // 根据 realId 和 typeId 查找 problemId
    Integer queryProblemIdByRealIdAndTypeId(@Param("realId") Integer realId,
                                            @Param("typeId") Integer typeId);


    // 查询题目的答案和分数
    Map<String, Object> queryAnswerAndScore(@Param("realId") Integer realId,
                                            @Param("tableName") String tableName);

    // 创建选择题
    void createChoiceProblem(@Param("choice")ChoiceProblem choice);

    // 上传了一个新的测试文件，插入一条新路径
    void insertNewPath(@Param("path") TestCasePath path);

    // 创建编程题
    void createProgramProblem(@Param("programProblem") ProgramProblem programProblem);

    // 设置题目的对应路径
    void updateProblemIdFromTestCasePath(@Param("problemId") Integer problemId,
                                         @Param("pathId") Integer pathId);

    // 插入提交的编程题信息
    void insertSubmitProgram(@Param("submitProgram") SubmitProgram submitProgram);

    // 查找最近一次的提交记录
    Integer queryLastSubmit(@Param("userId") Integer userId,
                            @Param("contestId") Integer contestId,
                            @Param("problemId") Integer problemId);

    // 更新最近一次的提交记录
    void updateLastSubmit(@Param("userId") Integer userId,
                          @Param("contestId") Integer contestId,
                          @Param("problemId") Integer problemId,
                          @Param("submitId") Integer submitId);

    // 插入最近一次的提交记录
    void insertLastSubmit(@Param("userId") Integer userId,
                          @Param("contestId") Integer contestId,
                          @Param("problemId") Integer problemId,
                          @Param("submitId") Integer submitId);

    // 通过 problemId 来查询编程题的时间限制和空间限制
    Map<String, Integer> queryLimitFormProgram(@Param("problemId") Integer problemId);

    // 根据 problemId 查询路径
    String queryPathByProblemId(@Param("problemId") Integer problemId);

    // 查询编程题判题结果
    SubmitProgram querySubmitResult(@Param("submitId") Integer submitId);

    // 保存判题结果
    void saveSubmitResult(@Param("submitId") Integer submitId,
                          @Param("result") String result);

    // 查询编程题信息
    Map<String, Object> queryProgramProblem(@Param("problemId") Integer problemId);

    // 查询所有题目类型的 typeId
    List<Integer> queryAllTypeIdOfProblem();

    // 通过比赛 id 和题目类型来查找题目
    List<Integer> queryProblemIdsByContestIdAndTypeId(@Param("contestId") Integer contestId,
                                                      @Param("typeId") Integer typeId);

    // 查询题目的标签字符串
    String queryTagsByProblemId(@Param("problemId") Integer problemId);

    // 查询所有的题目 id
    List<Integer> queryAllProblemId();

    // 根据 problemId 和 typeId 来查询 realId
    Integer queryRealIdByProblemIdAndTypeId(@Param("problemId") Integer problemId,
                                            @Param("typeId")  Integer typeId);

    // 修改选择题信息
    void updateChoiceProblem(@Param("choiceProblem") ChoiceProblem choiceProblem);

    // 根据 problemId 来查询 TestCasePath 实体类
    TestCasePath queryTestCasePathByPathId(@Param("problemId") Integer problemId);

    // 更新编程题信息
    void updateProgramProblem(@Param("programProblem") ProgramProblem programProblem);

    // 查询指定类型下的题目真实 id 分页
    List<Integer> queryRealIdByTypeIdPaging(@Param("typeId") Integer typeId,
                                            @Param("cnt") Integer cnt,
                                            @Param("pageSize") Integer pageSize);

    // 删除作废的路径映射
    void deletePathByProblemId(@Param("problemId") Integer problemId);

    // 根据 problemId 来查询题目名
    String queryProgramTitleByProblemId(@Param("problemId") Integer problemId);

    // 根据 problemId 来查询题目类型
    Integer queryTypeIdByProblemId(@Param("problemId") Integer problemId);

}
