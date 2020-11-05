package com.sicnu.pta.service;

public interface EmailService {
    void sendEmailVerCode(String receiver, String verifyCode);
}
