package com.sicnu.sta.service.admin;

import com.sicnu.sta.entity.JudgeProblem;
import com.sicnu.sta.utils.ResultUtils;

public interface AdminJudgeProblemService {

    /**
     * 添加判断题
     * @param judgeProblem 判断题实体类
     * @return ResultUtils
     */
    ResultUtils<Object> createJudgeProblem(JudgeProblem judgeProblem);
}
