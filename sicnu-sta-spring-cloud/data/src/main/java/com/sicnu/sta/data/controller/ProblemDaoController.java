package com.sicnu.sta.data.controller;

import com.sicnu.sta.commons.entity.*;
import com.sicnu.sta.data.dao.JudgeProblemDao;
import com.sicnu.sta.data.dao.ProblemDao;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/problem-dao")
public class ProblemDaoController {

    @Resource
    private ProblemDao problemDao;

    @Resource
    private JudgeProblemDao judgeProblemDao;

    // 添加题目
    @PostMapping("/add/problem")
    void addProblem(@RequestBody Problem problem) {
        problemDao.addProblem(problem);
    }

    // 设置题目标签
    @GetMapping("/set/problem-tag")
    void setProblemTag(@RequestParam("tag") String tag, @RequestParam("problemId") int problemId) {
        problemDao.setProblemTag(tag, problemId);
    }

    // 查询题目对应题目表下的 id 和 类型 id
    @GetMapping("/query/problem-realid")
    Map<String, Integer> queryProblemRealId(@RequestParam("problemId") int problemId) {
        return problemDao.queryProblemRealId(problemId);
    }

    // 查询所有题目类型
    @GetMapping("/query/all-problem-type")
    List<Map<String, Object>> queryAllProblemType() {
        return problemDao.queryAllProblemType();
    }

    // 根据 typeId 来查询对应的表名
    @GetMapping("/query/tablename-by-typeid")
    String queryTableNameByTypeId(@RequestParam("typeId") Integer typeId) {
        return problemDao.queryTableNameByTypeId(typeId);
    }

    // 根据 题目 id 和对应的表名查询题目信息
    @GetMapping("/query/problem-info")
    Map<String, Object> queryProblemInfo(@RequestParam("problemId") Integer problemId,
                                         @RequestParam("tableName") String tableName) {
        return problemDao.queryProblemInfo(problemId, tableName);
    }

    // 根据作者 id 和 题目难度 来查找题目
    @GetMapping("/query/problemid/authorid-and-difficulty-and-typeid")
    List<Integer> queryProblemIdByAuthorIdAndDifficultyAndTypeId(@RequestParam("authorId") Integer authorId,
                                                                 @RequestParam("difficulty") Integer difficulty,
                                                                 @RequestParam("typeId") Integer typeId) {
        return problemDao.queryProblemIdByAuthorIdAndDifficultyAndTypeId(authorId, difficulty, typeId);
    }

    // 通过 problem id 来查询真实 id 和题目类型 id
    @GetMapping("/query/realid-and-typeid/by-problemid")
    Map<String, Object> queryRealIdAndTypeIdByProblemId(@RequestParam("problemId") Integer problemId) {
        return problemDao.queryRealIdAndTypeIdByProblemId(problemId);
    }

    // 查询指定类型下的题目真实 id
    @GetMapping("/query/realid-by-typeid")
    List<Integer> queryRealIdByTypeId(@RequestParam("typeId") Integer typeId) {
        return problemDao.queryRealIdByTypeId(typeId);
    }


    // 根据 realId 和 typeId 查找 problemId
    @GetMapping("/query/problemid/by-realid-and-typeid")
    Integer queryProblemIdByRealIdAndTypeId(@RequestParam("realId") Integer realId,
                                            @RequestParam("typeId") Integer typeId) {
        return problemDao.queryProblemIdByRealIdAndTypeId(realId, typeId);
    }


    // 查询题目的答案和分数
    @GetMapping("/query/answer-and-score")
    Map<String, Object> queryAnswerAndScore(@RequestParam("realId") Integer realId,
                                            @RequestParam("tableName") String tableName) {
        return problemDao.queryAnswerAndScore(realId, tableName);
    }

    // 创建选择题
    @PostMapping("/create/choice-problem")
    void createChoiceProblem(@RequestBody ChoiceProblem choice) {
        problemDao.createChoiceProblem(choice);
    }

    // 上传了一个新的测试文件，插入一条新路径
    @GetMapping("/insert/new-path")
    void insertNewPath(@RequestParam("path") TestCasePath path) {
        problemDao.insertNewPath(path);
    }

    // 创建编程题
    @PostMapping("/create/program-problem")
    void createProgramProblem(@RequestBody ProgramProblem programProblem) {
        problemDao.createProgramProblem(programProblem);
    }

    // 设置题目的对应路径
    @GetMapping("/update/problemid-from-test.casepath")
    void updateProblemIdFromTestCasePath(@RequestParam("problemId") Integer problemId,
                                         @RequestParam("pathId") Integer pathId) {
        problemDao.updateProblemIdFromTestCasePath(problemId, pathId);
    }

    // 插入提交的编程题信息
    @PostMapping("/insert/submit-problem")
    void insertSubmitProgram(@RequestBody SubmitProgram submitProgram) {
        problemDao.insertSubmitProgram(submitProgram);
    }

    // 查找最近一次的提交记录
    @GetMapping("/query/last/submit")
    Integer queryLastSubmit(@RequestParam("userId") Integer userId,
                            @RequestParam("contestId") Integer contestId,
                            @RequestParam("problemId") Integer problemId) {
        return problemDao.queryLastSubmit(userId, contestId, problemId);
    }

    // 更新最近一次的提交记录
    @GetMapping("/update/last/submit")
    void updateLastSubmit(@RequestParam("userId") Integer userId,
                          @RequestParam("contestId") Integer contestId,
                          @RequestParam("problemId") Integer problemId,
                          @RequestParam("submitId") Integer submitId) {
        problemDao.updateLastSubmit(userId, contestId, problemId, submitId);
    }

    // 插入最近一次的提交记录
    @GetMapping("/insert/last/submit")
    void insertLastSubmit(@RequestParam("userId") Integer userId,
                          @RequestParam("contestId") Integer contestId,
                          @RequestParam("problemId") Integer problemId,
                          @RequestParam("submitId") Integer submitId) {
        problemDao.insertLastSubmit(userId, contestId, problemId, submitId);
    }

    // 通过 problemId 来查询编程题的时间限制和空间限制
    @GetMapping("/query/limit/form/program")
    Map<String, Integer> queryLimitFormProgram(@RequestParam("problemId") Integer problemId) {
        return problemDao.queryLimitFormProgram(problemId);
    }

    // 根据 problemId 查询路径
    @GetMapping("/query/path/by/problem/id")
    String queryPathByProblemId(@RequestParam("problemId") Integer problemId) {
        return problemDao.queryPathByProblemId(problemId);
    }

    // 查询编程题判题结果
    @GetMapping("/query/submit/result")
    SubmitProgram querySubmitResult(@RequestParam("submitId") Integer submitId) {
        return problemDao.querySubmitResult(submitId);
    }

    // 保存判题结果
    @GetMapping("/save/submit/result")
    void saveSubmitResult(@RequestParam("submitId") Integer submitId,
                          @RequestParam("result") String result) {
        problemDao.saveSubmitResult(submitId, result);
    }

    // 查询编程题信息
    @GetMapping("/query/program/problem")
    Map<String, Object> queryProgramProblem(@RequestParam("problemId") Integer problemId) {
        return problemDao.queryProgramProblem(problemId);
    }

    // 查询所有题目类型的 typeId
    @GetMapping("/query/all/type/id/of/problem")
    List<Integer> queryAllTypeIdOfProblem() {
        return problemDao.queryAllTypeIdOfProblem();
    }

    // 通过比赛 id 和题目类型来查找题目
    @GetMapping("/query/problem/id/by/contest/id/and/type/id")
    List<Integer> queryProblemIdsByContestIdAndTypeId(@RequestParam("contestId") Integer contestId,
                                                      @RequestParam("typeId") Integer typeId) {
        return problemDao.queryProblemIdsByContestIdAndTypeId(contestId, typeId);
    }

    // 查询题目的标签字符串
    @GetMapping("/query/tags/by/problem/id")
    String queryTagsByProblemId(@RequestParam("problemId") Integer problemId) {
        return problemDao.queryTagsByProblemId(problemId);
    }

    // 查询所有的题目 id
    @GetMapping("/query/all/problem/id")
    List<Integer> queryAllProblemId() {
        return problemDao.queryAllProblemId();
    }

    // 根据 problemId 和 typeId 来查询 realId
    @GetMapping("/query/real/id/by/problem/id/and/type/id")
    Integer queryRealIdByProblemIdAndTypeId(@RequestParam("problemId") Integer problemId,
                                            @RequestParam("typeId")  Integer typeId) {
        return problemDao.queryRealIdByProblemIdAndTypeId(problemId, typeId);
    }

    // 修改选择题信息
    @PostMapping("/update/choice/problem")
    void updateChoiceProblem(@RequestBody ChoiceProblem choiceProblem) {
        problemDao.updateChoiceProblem(choiceProblem);
    }

    // 根据 problemId 来查询 TestCasePath 实体类
    @GetMapping("/query/test/case/path/by/path/id")
    TestCasePath queryTestCasePathByPathId(@RequestParam("problemId") Integer problemId) {
        return problemDao.queryTestCasePathByPathId(problemId);
    }

    // 更新编程题信息
    @PostMapping("/update/program/problem")
    void updateProgramProblem(@RequestBody ProgramProblem programProblem) {
        problemDao.updateProgramProblem(programProblem);
    }

    // 查询指定类型下的题目真实 id 分页
    @GetMapping("/query/real/id/by/type/id/paging")
    List<Integer> queryRealIdByTypeIdPaging(@RequestParam("typeId") Integer typeId,
                                            @RequestParam("cnt") Integer cnt,
                                            @RequestParam("pageSize") Integer pageSize) {
        return problemDao.queryRealIdByTypeIdPaging(typeId, cnt, pageSize);
    }

    // 删除作废的路径映射
    @GetMapping("/delete/path/by/problem/id")
    void deletePathByProblemId(@RequestParam("problemId") Integer problemId) {
        problemDao.deletePathByProblemId(problemId);
    }

    // 根据 problemId 来查询题目名
    @GetMapping("/query/program/title/by/problem/id")
    String queryProgramTitleByProblemId(@RequestParam("problemId") Integer problemId) {
        return problemDao.queryProgramTitleByProblemId(problemId);
    }

    // 根据 problemId 来查询题目类型
    @GetMapping("/query/type/id/by/problem/id")
    Integer queryTypeIdByProblemId(@RequestParam("problemId") Integer problemId) {
        return problemDao.queryTypeIdByProblemId(problemId);
    }

    // 添加判断题
    @PostMapping("/add/judge/problem")
    void addJudgeProblem(@RequestBody JudgeProblem judgeProblem) {
        judgeProblemDao.addJudgeProblem(judgeProblem);
    }

    // 修改判断题
    @PostMapping("/update/judge/problem")
    void updateJudgeProblem(@RequestBody JudgeProblem judgeProblem) {
        judgeProblemDao.updateJudgeProblem(judgeProblem);
    }

}
