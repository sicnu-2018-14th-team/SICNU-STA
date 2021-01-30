package com.sicnu.sta.core.server;

import com.sicnu.sta.commons.entity.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("DATA")
public interface TagServer {

    // 增加一个标签
    @PostMapping("/tag-dao/add-tag")
    void addTag(@RequestBody Tag tag);

    // 查询标签 id
    @GetMapping("/tag-dao/query/tagid")
    Integer queryTagId(@RequestParam("tagName") String tagName);

    // 根据标签名来模糊查询标签
    @GetMapping("/tag-dao/query/vague-tag")
    List<Map<String, Object>> vagueQueryTag(@RequestParam("tagName") String tagName) ;

    // 根据标签 id 来查找含有此标签的题目
    @GetMapping("/tag-dao/query/problem/by-tag")
    String queryProblemByTag(@RequestParam("tagId") int tagId);

    // 根据标签来查找标签名
    @GetMapping("/tag-dao/query/tagname/by-tagid")
    String queryTagNameByTagId(@RequestParam("tagId") Integer tagId);
    @GetMapping("/tag-dao/update/tag-problem")
    void updateTagProblems(@RequestParam("tagId") Integer tagId,
                           @RequestParam("problem") String problem);
}
