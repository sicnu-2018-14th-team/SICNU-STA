package com.sicnu.sta.core.service.impl;

import com.sicnu.sta.commons.entity.Tag;
import com.sicnu.sta.commons.utils.ResultUtils;
import com.sicnu.sta.core.server.TagServer;
import com.sicnu.sta.core.service.AdminTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AdminTagServiceImpl implements AdminTagService {

    @Resource
    private TagServer tagDao;

    @Resource
    AdminProblemServiceImpl problemService;

    public static final Integer LEN = 1000;

    /**
     * 添加一个标签
     *
     * @param tag 标签实体类
     * @return 标签 id
     */
    @Override
    public int createTag(Tag tag) {
        return 0;
    }

    /**
     * 获取标签
     *
     * @param tagList 标签名集合
     * @param problemId 题目 id
     * @return 标签 id 构成的字符串
     */
    @Override
    public String getTagStr(List<String> tagList, int problemId) {
        try {
            StringBuilder res = new StringBuilder("");
            for (int i = 0; i < LEN; i ++) res.append("0");
            for (String tag : tagList) {
                Integer tagId = tagDao.queryTagId(tag);
                // 数据库中不存在该标签
                if (tagId == null) {
                    String problemStr = problemService.getProblemStr(problemId);
                    Tag newTag = new Tag(tag, problemStr);
                    tagDao.addTag(newTag);
                    tagId = newTag.getTagId();
                } else {
                    // 存在那么就更新
                    String tmpPro = tagDao.queryProblemByTag(tagId);
                    StringBuilder stringBuilder = new StringBuilder(tmpPro);
                    stringBuilder.setCharAt(problemId - 1, '1');
                    tagDao.updateTagProblems(tagId, stringBuilder.toString());
                }
                res.setCharAt(tagId - 1, '1');
            }
            return res.toString();
        } catch (Exception e) {
            log.error("生成标签字符串失败", e);
            return null;
        }
    }

    /**
     * 根据标签名来模糊查询标签
     *
     * @param tagName 标签名
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> vagueQueryTags(String tagName) {
        try {
            List<Map<String, Object>> maps = tagDao.vagueQueryTag(tagName);
            return ResultUtils.success(maps);
        } catch (Exception e) {
            log.error("模糊查询标签失败", e);
            return ResultUtils.error();
        }
    }
}
