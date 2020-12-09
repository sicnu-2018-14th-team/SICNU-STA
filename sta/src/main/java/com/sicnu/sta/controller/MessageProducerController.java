package com.sicnu.sta.controller;

import com.sicnu.sta.entity.JudgeResult;
import com.sicnu.sta.entity.JudgeTask;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;


@Slf4j
@RestController
public class MessageProducerController {

    @Resource
    KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/judge.do")
    public Object judge(@RequestBody JudgeTask task) {
        log.info("发送任务成功");
        System.out.println(task);
        kafkaTemplate.send("judge", JSON.toJSONString(task));
        return "OK";
    }

    //@PutMapping("/result.do")
    //public String result(String key, Long submitId, @RequestBody JudgeResult result) {
    //    System.out.println("hh");
    //    log.info("\n*****************" + "\n" +
    //            "\tkey: " + key + "\n" +
    //            "\tsubmitId: " + submitId + "\n" +
    //            "\tresult: " + result + "\n" +
    //            "*****************");
    //    return "OK";
    //}
}