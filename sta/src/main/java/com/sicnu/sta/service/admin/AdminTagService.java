package com.sicnu.sta.service.admin;

import com.sicnu.sta.entity.Tag;
import com.sicnu.sta.utils.ResultUtils;

import java.util.List;

public interface AdminTagService {

    /**
     * 创建一个标签
     * @param tag 标签实体类
     * @return 标签 id
     */
    int createTag(Tag tag);

    /**
     * 获取标签
     * @param tagList 标签名集合
     * @param problemId 题目 id
     * @return 标签 id 构成的字符串
     */
    String getTagStr(List<String> tagList, int problemId);

    /**
     * 根据标签名来模糊查询标签
     * @param tagName 标签名
     * @return ResultUtils
     */
    ResultUtils<Object> vagueQueryTags(String tagName);
}
