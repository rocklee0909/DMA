package cn.monitoring.common.kafka.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.monitoring.common.kafka.config.KafkaTopicConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * Kafka 生产者 数据模拟发送
 * @author liru
 */
@RestController
@RequestMapping("/kafka")
@Slf4j
public class KafkaProducerController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @ResponseBody
    @PostMapping(value = "/produce", produces = "application/json")
    public String produce(@RequestBody Object obj) {
        try {
            String obj2String = JSONUtil.toJsonStr(obj);


            JSON json = JSONUtil.parse(obj2String);
            if(json instanceof JSONObject){
                String topic = ((JSONObject)json).getStr("topic");
                kafkaTemplate.send(topic, obj2String);
            }else{
                kafkaTemplate.send("dma_device_1", obj2String);
            }



//            kafkaTemplate.send(KafkaTopicConfig.DMA_COLLECTION_TOPIC_TEST, obj2String);
            return "success";
        } catch (Exception e) {
            e.getMessage();
        }
        return "success";
    }
}