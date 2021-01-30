package com.sicnu.sta.core;

import com.sicnu.sta.commons.utils.RedisUtils;
import org.apache.shiro.spring.boot.autoconfigure.ShiroAnnotationProcessorAutoConfiguration;
import org.apache.shiro.spring.boot.autoconfigure.ShiroAutoConfiguration;
import org.apache.shiro.spring.boot.autoconfigure.ShiroBeanAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {ShiroAnnotationProcessorAutoConfiguration.class, ShiroAutoConfiguration.class, ShiroBeanAutoConfiguration.class, DataSourceAutoConfiguration.class})
@EnableFeignClients(basePackages = "com.sicnu.sta.core")
@EnableEurekaClient
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }

    @Bean
    public RedisUtils redisUtils() {
        return new RedisUtils();
    }

}
