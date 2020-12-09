package com.sicnu.sta.service.admin;

import com.sicnu.sta.entity.ChoiceProblem;
import com.sicnu.sta.entity.FindProblem;
import com.sicnu.sta.entity.ProgramProblem;
import com.sicnu.sta.entity.SubmitProgram;
import com.sicnu.sta.utils.ResultUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminProblemService {

    /**
     * 获取标签-题目字符串
     * @param problemId 题目 id
     * @return 由 problemId 构成的字符串
     */
    String getProblemStr(int problemId);

    /**
     * 查找题目信息
     * @param findProblem 查找题目实体类
     * @return ResultUtils
     */
    ResultUtils<Object> queryProblemInfoByFindProblemClass(FindProblem findProblem);

    /**
     * 根据标签 id 来查找含有此标签的题目
     * @param tagId 标签 id
     * @return 含有此标签的题目 id 集
     */
    List<Integer> queryProblemIdByTag(int tagId);


    /**
     * 创建选择题
     * @param choiceProblem 选择题实体类
     * @return ResultUtils
     */
    ResultUtils<Object> createChoiceProblem(ChoiceProblem choiceProblem);

    /**
     * 解析上传的测试文件压缩包
     * @param file 文件
     * @return ResultUtil
     */
    ResultUtils<Object> unTestCase(MultipartFile file);

    /**
     * 校验测试文件是否合法
     * @param path 测试文件路径
     * @param UUID 测试文件路径
     * @param deletePath 测试文件路径
     * @return ResultUtils
     */
    ResultUtils<Object> validTestCase(String path, String UUID, String deletePath);

    /**
     * 创建编程题
     * @param programProblem 编程题实体类
     * @return ResultUtils
     */
    ResultUtils<Object> createProgramProblem(ProgramProblem programProblem);

}
