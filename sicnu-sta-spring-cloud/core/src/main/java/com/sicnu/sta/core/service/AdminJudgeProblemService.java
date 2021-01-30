package com.sicnu.sta.core.service;

import com.sicnu.sta.commons.entity.JudgeProblem;
import com.sicnu.sta.commons.utils.ResultUtils;

public interface AdminJudgeProblemService {

    /**
     * 添加判断题
     * @param judgeProblem 判断题实体类
     * @return ResultUtils
     */
    ResultUtils<Object> createJudgeProblem(JudgeProblem judgeProblem);
}
