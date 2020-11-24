package com.sicnu.sta.service.impl;

import com.sicnu.sta.dao.AuthDao;
import com.sicnu.sta.entity.Auth;
import com.sicnu.sta.service.AuthService;
import com.sicnu.sta.utils.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private AuthDao authDao;

    private ResponseUtil res;

    @Override
    public ResponseUtil queryAllAuthInfo() {
        List<Auth> auths = authDao.queryAllAuthInfo();
        List<Object> data = new ArrayList<>();

        for (Auth i : auths) {
            List<Auth> tmp = new ArrayList<>();
            for (Auth j : auths) {
                // i 是 j 的父亲
                if (i.getpAuthId() == 0 || i.getAuthId() == j.getpAuthId()) {
                    tmp.add(j);
                }
            }
            data.add(tmp);
        }

        System.out.println(data);

        res = new ResponseUtil(1, "success", auths);
        return res;
    }
}
