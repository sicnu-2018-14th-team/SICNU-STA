package com.sicnu.sta.data.controller;

import com.sicnu.sta.commons.entity.Tag;
import com.sicnu.sta.data.dao.TagDao;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tag-dao")
public class TaoDaoController {
    
    @Resource
    private TagDao tagDao;

    // 增加一个标签
    @PostMapping("/add-tag")
    void addTag(@RequestBody Tag tag) {
        tagDao.addTag(tag);
    }

    // 查询标签 id
    @GetMapping("/query/tagid")
    Integer queryTagId(@RequestParam("tagName") String tagName) {
        return tagDao.queryTagId(tagName);
    }

    // 根据标签名来模糊查询标签
    @GetMapping("/query/vague-tag")
    List<Map<String, Object>> vagueQueryTag(@RequestParam("tagName") String tagName) {
        return tagDao.vagueQueryTag(tagName);
    }

    // 根据标签 id 来查找含有此标签的题目
    @GetMapping("/query/problem/by-tag")
    String queryProblemByTag(@RequestParam("tagId") int tagId) {
        return tagDao.queryProblemByTag(tagId);
    }

    // 根据标签来查找标签名
    @GetMapping("/query/tagname/by-tagid")
    String queryTagNameByTagId(@RequestParam("tagId") Integer tagId) {
        return tagDao.queryTagNameByTagId(tagId);
    }

    @GetMapping("/update/tag-problem")
    void updateTagProblems(@RequestParam("tagId") Integer tagId,
                           @RequestParam("problem") String problem) {
        tagDao.updateTagProblems(tagId, problem);
    }
}
