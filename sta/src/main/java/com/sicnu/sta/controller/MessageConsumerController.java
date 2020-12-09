package com.sicnu.sta.controller;

import com.alibaba.fastjson.JSON;
import com.sicnu.sta.dao.ProblemDao;
import com.sicnu.sta.entity.JudgeResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 监听topic_hello主题产生的消息
 */
@Slf4j
@Component
public class MessageConsumerController {

    @Resource
    ProblemDao problemDao;

    Map<Integer, String> resultMsg = new HashMap<>();

    @KafkaListener(topics = "result")
    public void listen(ConsumerRecord<String, String> record) {
        Optional<String> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            try {
                String message = kafkaMessage.get();
                JudgeResult judgeResult = JSON.parseObject(message, JudgeResult.class);
                System.out.println(judgeResult);
                problemDao.saveSubmitResult(judgeResult.getSubmitId(), JSON.toJSONString(judgeResult));
                System.out.println(judgeResult);
                System.out.println(JSON.toJSONString(judgeResult));
            } catch (Exception e) {
               log.error("接收判题结果异常", e);
            }
        }
    }
}