package com.sicnu.sta.dao;

import com.sicnu.sta.entity.Auth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthDao {

    /**
     * 查询所有的权限信息
     * @return
     */
    List<Auth> queryAllAuthInfo();
}
