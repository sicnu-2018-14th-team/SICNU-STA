package com.sicnu.sta.commons;

import com.sicnu.sta.commons.utils.RedisUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CommonsRunner {

    @Bean
    public RedisUtils redisUtils() {
        return new RedisUtils();
    }
}
