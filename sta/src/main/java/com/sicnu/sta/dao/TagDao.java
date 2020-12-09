package com.sicnu.sta.dao;

import com.sicnu.sta.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TagDao {

    // 增加一个标签
    void addTag(@Param("tag")Tag tag);

    // 查询标签 id
    Integer queryTagId(@Param("tagName") String tagName);

    // 根据标签名来模糊查询标签
    List<Map<String, Object>> vagueQueryTag(@Param("tagName") String tagName);

    // 根据标签 id 来查找含有此标签的题目
    String queryProblemByTag(@Param("tagId") int tagId);
}
