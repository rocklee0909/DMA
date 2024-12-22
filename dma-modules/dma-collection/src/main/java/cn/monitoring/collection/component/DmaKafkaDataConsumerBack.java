package cn.monitoring.collection.component;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import cn.monitoring.common.kafka.config.KafkaTopicConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * DMA Kafka 消费者 数据接收处理类
 * @author liru
 */
@Slf4j
@Component
public class DmaKafkaDataConsumerBack {
    // 定义一个 Kafka 监听器，监听指定的主题（SpringBootKafkaConfig.TOPIC_TEST），并使用指定的消费者组（SpringBootKafkaConfig.GROUP_ID）
    @KafkaListener(topics = KafkaTopicConfig.DMA_COLLECTION_TOPIC_TEST,groupId = KafkaTopicConfig.DMA_COLLECTION_GROUP)
    public void dmaCollectionTopicConsumer(String messages, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        JSON entries = JSONUtil.parse(messages);

        System.out.println("DmaKafkaDataConsumerBack: "+entries);

        // 遍历接收到的消息列表
//        for (String message : messages) {
//            // 将接收到的消息字符串解析为 JSONObject 对象
//            final JSON entries = JSONUtil.parse(message);
//            // 打印消费信息，包括消费者组 ID、主题和消息中的 "data" 部分
//            System.out.println(KafkaTopicConfig.GROUP_ID + " 消费了： Topic:" + topic + ",Message:" + entries.toString());
//            //ack.acknowledge(); 可能是用于确认消息已被消费，当前被注释掉，可能需要根据具体情况启用
//        }
    }
}
