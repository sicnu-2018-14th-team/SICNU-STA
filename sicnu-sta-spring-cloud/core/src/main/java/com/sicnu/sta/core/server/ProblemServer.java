package com.sicnu.sta.core.server;

import com.sicnu.sta.commons.entity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("DATA")
public interface ProblemServer {

    // 添加题目
    @PostMapping("/problem-dao/add/problem")
    void addProblem(@RequestBody Problem problem);

    // 设置题目标签
    @GetMapping("/problem-dao/set/problem-tag")
    void setProblemTag(@RequestParam("tag") String tag, @RequestParam("problemId") int problemId);

    // 查询题目对应题目表下的 id 和 类型 id
    @GetMapping("/problem-dao/query/problem-realid")
    Map<String, Integer> queryProblemRealId(@RequestParam("problemId") int problemId);

    // 查询所有题目类型
    @GetMapping("/problem-dao/query/all-problem-type")
    List<Map<String, Object>> queryAllProblemType();

    // 根据 typeId 来查询对应的表名
    @GetMapping("/problem-dao/query/tablename-by-typeid")
    String queryTableNameByTypeId(@RequestParam("typeId") Integer typeId);

    // 根据 题目 id 和对应的表名查询题目信息
    @GetMapping("/problem-dao/query/problem-info")
    Map<String, Object> queryProblemInfo(@RequestParam("problemId") Integer problemId,
                                         @RequestParam("tableName") String tableName);

    // 根据作者 id 和 题目难度 来查找题目
    @GetMapping("/problem-dao/query/problemid/authorid-and-difficulty-and-typeid")
    List<Integer> queryProblemIdByAuthorIdAndDifficultyAndTypeId(@RequestParam("authorId") Integer authorId,
                                                                 @RequestParam("difficulty") Integer difficulty,
                                                                 @RequestParam("typeId") Integer typeId);

    // 通过 problem id 来查询真实 id 和题目类型 id
    @GetMapping("/problem-dao/query/realid-and-typeid/by-problemid")
    Map<String, Object> queryRealIdAndTypeIdByProblemId(@RequestParam("problemId") Integer problemId);

    // 查询指定类型下的题目真实 id
    @GetMapping("/problem-dao/query/realid-by-typeid")
    List<Integer> queryRealIdByTypeId(@RequestParam("typeId") Integer typeId);


    // 根据 realId 和 typeId 查找 problemId
    @GetMapping("/problem-dao/query/problemid/by-realid-and-typeid")
    Integer queryProblemIdByRealIdAndTypeId(@RequestParam("realId") Integer realId,
                                            @RequestParam("typeId") Integer typeId);


    // 查询题目的答案和分数
    @GetMapping("/problem-dao/query/answer-and-score")
    Map<String, Object> queryAnswerAndScore(@RequestParam("realId") Integer realId,
                                            @RequestParam("tableName") String tableName);

    // 创建选择题
    @PostMapping("/problem-dao/create/choice-problem")
    void createChoiceProblem(@RequestBody ChoiceProblem choice);

    // 上传了一个新的测试文件，插入一条新路径
    @PostMapping("/problem-dao/insert/new-path")
    void insertNewPath(@RequestBody TestCasePath path);

    // 创建编程题
    @PostMapping("/problem-dao/create/program-problem")
    void createProgramProblem(@RequestBody ProgramProblem programProblem) ;

    // 设置题目的对应路径
    @GetMapping("/problem-dao/update/problemid-from-test.casepath")
    void updateProblemIdFromTestCasePath(@RequestParam("problemId") Integer problemId,
                                         @RequestParam("pathId") Integer pathId);

    // 插入提交的编程题信息
    @PostMapping("/problem-dao/insert/submit-problem")
    void insertSubmitProgram(@RequestBody SubmitProgram submitProgram);

    // 查找最近一次的提交记录
    @GetMapping("/problem-dao/query/last/submit")
    Integer queryLastSubmit(@RequestParam("userId") Integer userId,
                            @RequestParam("contestId") Integer contestId,
                            @RequestParam("problemId") Integer problemId) ;

    // 更新最近一次的提交记录
    @GetMapping("/problem-dao/update/last/submit")
    void updateLastSubmit(@RequestParam("userId") Integer userId,
                          @RequestParam("contestId") Integer contestId,
                          @RequestParam("problemId") Integer problemId,
                          @RequestParam("submitId") Integer submitId) ;

    // 插入最近一次的提交记录
    @GetMapping("/problem-dao/insert/last/submit")
    void insertLastSubmit(@RequestParam("userId") Integer userId,
                          @RequestParam("contestId") Integer contestId,
                          @RequestParam("problemId") Integer problemId,
                          @RequestParam("submitId") Integer submitId) ;

    // 通过 problemId 来查询编程题的时间限制和空间限制
    @GetMapping("/problem-dao/query/limit/form/program")
    Map<String, Integer> queryLimitFormProgram(@RequestParam("problemId") Integer problemId);

    // 根据 problemId 查询路径
    @GetMapping("/problem-dao/query/path/by/problem/id")
    String queryPathByProblemId(@RequestParam("problemId") Integer problemId);

    // 查询编程题判题结果
    @GetMapping("/problem-dao/query/submit/result")
    SubmitProgram querySubmitResult(@RequestParam("submitId") Integer submitId);

    // 保存判题结果
    @GetMapping("/problem-dao/save/submit/result")
    void saveSubmitResult(@RequestParam("submitId") Integer submitId,
                          @RequestParam("result") String result);

    // 查询编程题信息
    @GetMapping("/problem-dao/query/program/problem")
    Map<String, Object> queryProgramProblem(@RequestParam("problemId") Integer problemId);

    // 查询所有题目类型的 typeId
    @GetMapping("/problem-dao/query/all/type/id/of/problem")
    List<Integer> queryAllTypeIdOfProblem();

    // 通过比赛 id 和题目类型来查找题目
    @GetMapping("/problem-dao/query/problem/id/by/contest/id/and/type/id")
    List<Integer> queryProblemIdsByContestIdAndTypeId(@RequestParam("contestId") Integer contestId,
                                                      @RequestParam("typeId") Integer typeId);

    // 查询题目的标签字符串
    @GetMapping("/problem-dao/query/tags/by/problem/id")
    String queryTagsByProblemId(@RequestParam("problemId") Integer problemId);

    // 查询所有的题目 id
    @GetMapping("/problem-dao/query/all/problem/id")
    List<Integer> queryAllProblemId();

    // 根据 problemId 和 typeId 来查询 realId
    @GetMapping("/problem-dao/query/real/id/by/problem/id/and/type/id")
    Integer queryRealIdByProblemIdAndTypeId(@RequestParam("problemId") Integer problemId,
                                            @RequestParam("typeId")  Integer typeId);

    // 修改选择题信息
    @PostMapping("/problem-dao/update/choice/problem")
    void updateChoiceProblem(@RequestBody ChoiceProblem choiceProblem);

    // 根据 problemId 来查询 TestCasePath 实体类
    @GetMapping("/problem-dao/query/test/case/path/by/path/id")
    TestCasePath queryTestCasePathByPathId(@RequestParam("problemId") Integer problemId);

    // 更新编程题信息
    @PostMapping("/problem-dao/update/program/problem")
    void updateProgramProblem(@RequestBody ProgramProblem programProblem) ;

    // 查询指定类型下的题目真实 id 分页
    @GetMapping("/problem-dao/query/real/id/by/type/id/paging")
    List<Integer> queryRealIdByTypeIdPaging(@RequestParam("typeId") Integer typeId,
                                            @RequestParam("cnt") Integer cnt,
                                            @RequestParam("pageSize") Integer pageSize);

    // 删除作废的路径映射
    @GetMapping("/problem-dao/delete/path/by/problem/id")
    void deletePathByProblemId(@RequestParam("problemId") Integer problemId);

    // 根据 problemId 来查询题目名
    @GetMapping("/problem-dao/query/program/title/by/problem/id")
    String queryProgramTitleByProblemId(@RequestParam("problemId") Integer problemId);

    // 根据 problemId 来查询题目类型
    @GetMapping("/problem-dao/query/type/id/by/problem/id")
    Integer queryTypeIdByProblemId(@RequestParam("problemId") Integer problemId);

    // 添加判断题
    @PostMapping("/problem-dao/add/judge/problem")
    void addJudgeProblem(@RequestBody JudgeProblem judgeProblem);

    // 修改判断题
    @PostMapping("/problem-dao/update/judge/problem")
    void updateJudgeProblem(@RequestBody JudgeProblem judgeProblem);


}
