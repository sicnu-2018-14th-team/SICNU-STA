package com.sicnu.pta.utils;

import java.security.SecureRandom;
import java.util.Random;

public class VerifyCodeUtil {

    private static final String SYMBOLS = "0123456789";
    private static final Random RANDOM = new SecureRandom();

    /*
     * 生成 6 位随机验证码
     */
    public static String getVerifyCode() {
        char [] res = new char[6];
        for(int i = 0; i < res.length; i ++) {
            res[i] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(res);
    }
}
