package com.sicnu.sta.zuul;

import com.sicnu.sta.commons.utils.RedisUtils;
import com.sicnu.sta.zuul.filter.MyFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableZuulProxy
public class ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

    @Bean
    public RedisUtils redisUtils() {
        return new RedisUtils();
    }

    @Bean
    public MyFilter myFilter() {
        return new MyFilter();
    }



}
