package com.sicnu.sta.service;

import com.sicnu.sta.utils.ResponseUtil;

public interface EmailService {
    ResponseUtil sendEmailVerCode(String receiver);
}
